package com.realpage.propertyware.service;


import com.realpage.propertyware.entity.EndPoints;
import com.realpage.propertyware.entity.ExecutionSummary;
import com.realpage.propertyware.entity.Organization;
import com.realpage.propertyware.model.EnvironmentPojo;
import com.realpage.propertyware.model.ExecuteAgainResponse;
import com.realpage.propertyware.model.ExecutionSummaryPojo;
import com.realpage.propertyware.model.OrganizationPojo;
import com.realpage.propertyware.repository.EndPointsRepository;
import com.realpage.propertyware.repository.EnvironmentRepository;
import com.realpage.propertyware.repository.ExecutionSummaryRepository;
import com.realpage.propertyware.repository.OrganizationRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class APIService {

    @Autowired
    private EnvironmentRepository environmentRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ExecutionSummaryRepository summaryRepository;
    @Autowired
    private EndPointsRepository endPointsRepository;
    @Autowired
    private RestTemplate restTemplate;


    public List<EnvironmentPojo> getEnvironmentDetails() {

        return environmentRepository.findAll()
                .stream().map(e-> new EnvironmentPojo(e.getId(),e.getName()))
                .collect(Collectors.toList());
    }

    public List<OrganizationPojo> getOrganizationsForEnvironment(Integer envId) {

        return organizationRepository.findAllByEnvironmentId(envId)
                .stream().map(o-> new OrganizationPojo(o.getId(),o.getName()))
                .collect(Collectors.toList());
    }

    public List<ExecutionSummaryPojo> getExecutionSummaryForEnvAndOrg(Integer envId, Integer orgId) {

        List<ExecutionSummary> executionSummaries = summaryRepository.findAllByEnvironmentIdAndOrgId(envId, orgId);

        return executionSummaries.stream()
                .map(e -> {
                    ExecutionSummaryPojo pojo = new ExecutionSummaryPojo();
                    Optional<EndPoints> ep = endPointsRepository.findById(e.getEndpointId());
                    pojo.setId(e.getId());
                    pojo.setEndpoint(ep.get().getEndPoint());
                    pojo.setAction(ep.get().getAction());
                    pojo.setGroup(ep.get().getGroupEndPoints());
                    try {
                        pojo.setInput(new JSONObject(new String(e.getInput())));
                    } catch (NullPointerException ex) {
                        pojo.setInput(null);
                    }
                    try {
                        pojo.setOutput(new JSONArray(new String(e.getOutput())));
                    } catch(JSONException exc) {
                        pojo.setOutput(new JSONArray("["+new String(e.getOutput())+"]"));
                    } catch (NullPointerException ex) {
                        pojo.setOutput(null);
                    }
                    pojo.setLastTested(e.getLastTested());
                    if (Objects.nonNull(e.getEndTime()) && Objects.nonNull(e.getStartTime()))
                        pojo.setExecutionTime(e.getEndTime().getTime() - e.getStartTime().getTime());
                    pojo.setResult(e.getResult());
                    return pojo;
                }).collect(Collectors.toList());
    }

    public ExecuteAgainResponse executeAPIAgain(List<Integer> ids) {

        Integer id = ids.get(0);
        Optional<ExecutionSummary> es = summaryRepository.findById(id);
        Optional<EndPoints> ep = endPointsRepository.findById(es.get().getEndpointId());
        Optional<Organization> org = organizationRepository.findById(es.get().getOrgId());
        String url = Objects.isNull(ep.get().getUrlParameters())
                ? (ep.get().getEndpointUrl()) : (ep.get().getEndpointUrl() + "?" + ep.get().getUrlParameters());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("x-propertyware-system-id", String.valueOf(org.get().getSystemId()));
        headers.set("x-propertyware-client-id", org.get().getClientId());
        headers.set("x-propertyware-client-secret", org.get().getClientSecret());

        HttpEntity<String> req = null;
        HttpMethod method = null;

        if ("GET".equals(ep.get().getEndPoint())) {
            method = HttpMethod.GET;
            req = new HttpEntity<>(headers);
        } else if ("POST".equals(ep.get().getEndPoint())) {
            method = HttpMethod.POST;
            req = new HttpEntity<>(es.get().getInput(), headers);
        } else if ("PUT".equals(ep.get().getEndPoint())) {
            method = HttpMethod.PUT;
            req = new HttpEntity<>(es.get().getInput(), headers);
        }

        es.get().setStartTime(new Timestamp(System.currentTimeMillis()));
        ResponseEntity<byte[]> response = null;
        try {
            response = restTemplate.exchange(url, method, req, byte[].class);
            es.get().setEndTime(new Timestamp(System.currentTimeMillis()));
            es.get().setLastTested(new Date(System.currentTimeMillis()));
            es.get().setOutput(response.getBody());
            es.get().setStatusCode(response.getStatusCodeValue());
            es.get().setResult("PASS");
        } catch (HttpClientErrorException e) {

            es.get().setEndTime(new Timestamp(System.currentTimeMillis()));
            es.get().setLastTested(new Date(System.currentTimeMillis()));
            es.get().setOutput(e.getResponseBodyAsByteArray());
            es.get().setStatusCode(e.getRawStatusCode());
            es.get().setResult("FAIL");
        }

        summaryRepository.save(es.get());

        return new ExecuteAgainResponse("success");
    }

}

package com.realpage.propertyware.controller;


import com.realpage.propertyware.service.APIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pw/api")
public class APIController {

    @Autowired
    private APIService apiService;


    @GetMapping("/environments")
    public ResponseEntity<Object> getEnvironmentData() {

        return ResponseEntity.ok(apiService.getEnvironmentDetails());
    }


    @GetMapping("/environments/{id}/organizations")
    public ResponseEntity<Object> getOrganizationsForEnvironment(@PathVariable Integer id) {

        return ResponseEntity.ok(apiService.getOrganizationsForEnvironment(id));
    }

    @GetMapping("/environments/{envId}/organizations/{orgId}/executions")
    public ResponseEntity<Object> getExecutionSummary(@PathVariable Integer envId, @PathVariable Integer orgId) {

        return ResponseEntity.ok(apiService.getExecutionSummaryForEnvAndOrg(envId, orgId));
    }

    @PutMapping("/environments/{envId}/organizations/{orgId}/executions")
    public ResponseEntity<Object> executeAgain(@RequestBody List<Integer> ids) {

        return ResponseEntity.ok(apiService.executeAPIAgain(ids));
    }
}

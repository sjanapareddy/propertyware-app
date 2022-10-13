package com.realpage.propertyware.repository;

import com.realpage.propertyware.entity.Environment;
import com.realpage.propertyware.entity.ExecutionSummary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExecutionSummaryRepository extends CrudRepository<ExecutionSummary, Integer> {

    List<ExecutionSummary> findAllByEnvironmentIdAndOrgId(Integer envId, Integer orgId);
}

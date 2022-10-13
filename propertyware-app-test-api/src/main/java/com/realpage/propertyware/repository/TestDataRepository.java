package com.realpage.propertyware.repository;

import com.realpage.propertyware.entity.ExecutionSummary;
import org.springframework.data.repository.CrudRepository;

public interface TestDataRepository extends CrudRepository<ExecutionSummary, Integer> {
}

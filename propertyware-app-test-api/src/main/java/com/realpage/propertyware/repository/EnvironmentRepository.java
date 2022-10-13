package com.realpage.propertyware.repository;

import com.realpage.propertyware.entity.EndPoints;
import com.realpage.propertyware.entity.Environment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnvironmentRepository extends CrudRepository<Environment, Integer> {

    List<Environment> findAll();
}

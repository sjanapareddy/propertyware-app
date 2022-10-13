package com.realpage.propertyware.repository;

import com.realpage.propertyware.entity.EndPoints;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EndPointsRepository extends CrudRepository<EndPoints, Integer> {
}

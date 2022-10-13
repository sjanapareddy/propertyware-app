package com.realpage.propertyware.repository;

import com.realpage.propertyware.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    List<Organization> findAllByEnvironmentId(Integer environmentId);

}

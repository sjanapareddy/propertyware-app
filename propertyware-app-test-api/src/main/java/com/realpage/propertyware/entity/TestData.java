package com.realpage.propertyware.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "test_data")
public class TestData {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "environment_id")/*ForeignKey Relation*/
    private int environmentId;
    @Column(name = "org_id")
    private int organisationId;
    @Column(name = "execution_time")
    private int executionTime;
    private String input;/*blob*/
    @Column(name = "output")
    private String output;/*blob*/
    @Column(name = "endpoint_id")/*ForeignKey Relation*/
    private int endpointId;
    @Column(name = "status_code")
    private Integer statusCode;
}

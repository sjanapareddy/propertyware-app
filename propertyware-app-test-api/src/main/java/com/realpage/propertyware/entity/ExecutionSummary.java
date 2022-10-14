package com.realpage.propertyware.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "execution_summary")
@Getter
@Setter
@ToString
public class ExecutionSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;
    @Lob
    @Column(name = "input")
    private String input;
    @Lob
    @Column(name = "output")
    private byte[] output;
    @Column(name = "endpoint_Id")
    private Integer endpointId;
    @Column(name = "environment_Id")
    private Integer environmentId;
    @Column(name = "last_tested")
    private Timestamp lastTested;
    @Column(name = "status_code")
    private Integer statusCode;
    @Column(name = "org_id")
    private Integer orgId;
    @Column(name = "result")
    private String result;
}

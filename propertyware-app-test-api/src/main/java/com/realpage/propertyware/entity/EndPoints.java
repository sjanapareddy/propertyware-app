package com.realpage.propertyware.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "endpoints")
@Setter
@Getter
@ToString
public class EndPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "endpoint")
    private String endPoint;
    @Column(name = "action")
    private String action;
    @Column(name = "endpoint_url")
    private String endpointUrl;
    @Column(name = "group_endpoints")
    private String groupEndPoints;
    @Column(name = "url_parameters")
    private String urlParameters;
}

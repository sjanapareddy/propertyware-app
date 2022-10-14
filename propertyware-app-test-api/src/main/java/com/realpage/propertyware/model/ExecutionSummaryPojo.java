package com.realpage.propertyware.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.realpage.propertyware.config.JsonArraySerializer;
import com.realpage.propertyware.config.JsonObjectSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
public class ExecutionSummaryPojo {

    private Integer id;
    private String endpoint;
    private String action;
    private String group;
    @JsonSerialize(using = JsonObjectSerializer.class)
    private JSONObject input;
    @JsonSerialize(using = JsonArraySerializer.class)
    private JSONArray output;
    private Long executionTime;
    private Timestamp lastTested;
    private String result;
}

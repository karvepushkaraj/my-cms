package com.servicecenter.cms.model;

import lombok.Data;

@Data
public class ServiceCall {

    private String id;
    private Customer customer;
    private String description;
    private ServiceRequestStatus status;

}

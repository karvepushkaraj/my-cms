package com.servicecenter.cms.service;

import com.servicecenter.cms.model.ServiceCall;
import com.servicecenter.cms.model.Technician;

import java.util.List;

public interface TechnicianService {

    Technician findTechnician(String technicianId);

    List<Technician> findTechnician();

    void addNewTechnician(Technician technician, String username, String password);

    void removeTechnician(String technicianId);

    void updateTechnician(Technician technician);

    Technician allocateServiceCall(ServiceCall serviceCall);

    ServiceCall getServiceCall(String requestId);

    void closeServiceCall(String technicianId, String requestId);

    void deleteServiceCall(String technicianId, String requestId);
}

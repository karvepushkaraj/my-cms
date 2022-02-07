package com.servicecenter.cms.model;

import com.servicecenter.cms.audit.AuditMetaData;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("technicians")
@Data
public class Technician extends AuditMetaData {

    @Id
    private String technicianId;
    private String name;
    private String mobileNumber;
    private int noOfCallsClosed;
    private int totalCalls;
    private List<String> serviceCalls = new ArrayList<>();

    public void addServiceCall(String requestId) {
        serviceCalls.add(requestId);
        totalCalls++;
    }

    public void removeServiceCall(String requestId) {
        serviceCalls.remove(requestId);
        noOfCallsClosed++;
    }

    public void deleteServiceCall(String requestId) {
        serviceCalls.remove(requestId);
        totalCalls--;
    }
}

package com.servicecenter.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.servicecenter.cms.model.ServiceCall;
import com.servicecenter.cms.model.Technician;
import com.servicecenter.cms.repository.AppUserRepository;
import com.servicecenter.cms.service.TechnicianService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/service-center")
@AllArgsConstructor
public class TechnicianController {

    private TechnicianService technicianService;
    private AppUserRepository repo;

    @GetMapping("/technician")
    public Technician findTechnician(@RequestParam(value = "id", required = true) String technicianId) {
        return technicianService.findTechnician(technicianId);
    }

    @PostMapping("/allocate")
    public String allocateServiceCall(@RequestBody ServiceCall serviceCall) throws JsonProcessingException {
        Technician technician = technicianService.allocateServiceCall(serviceCall);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("technicianId", technician.getTechnicianId());
        node.put("name", technician.getName());
        node.put("mobileNumber", technician.getMobileNumber());
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }

    @GetMapping("/service-request")
    public ServiceCall getServiceCall(@RequestParam(value = "id", required = true) String requestId) {
        return technicianService.getServiceCall(requestId);
    }

    @PostMapping("/service-request")
    public void closeServiceCall(@RequestBody String input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(input);
        String technicianId = mapper.treeToValue(node.get("technicianId"), String.class);
        String requestId = mapper.treeToValue(node.get("requestId"), String.class);
        technicianService.closeServiceCall(technicianId, requestId);
    }

    @PostMapping("/technician")
    public void deleteServiceCall(@RequestBody String input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(input);
        String technicianId = mapper.treeToValue(node.get("technicianId"), String.class);
        String requestId = mapper.treeToValue(node.get("requestId"), String.class);
        technicianService.deleteServiceCall(technicianId,requestId);
    }
}

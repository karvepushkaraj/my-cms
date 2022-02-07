package com.servicecenter.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicecenter.cms.model.Technician;
import com.servicecenter.cms.service.TechnicianService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/service-center/admin")
@AllArgsConstructor
public class AdminController {

    private TechnicianService technicianService;

    @PostMapping
    public void addTechnician(@RequestBody String input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(input);
        Technician technician = mapper.treeToValue(node.get("technician"), Technician.class);
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        technicianService.addNewTechnician(technician, username, password);
    }

    @DeleteMapping("/{id}")
    public void removeTechnician(@PathVariable(value = "id", required = true) String technicianId) {
        technicianService.removeTechnician(technicianId);
    }

    @PutMapping
    public void updateTechnician(@RequestBody Technician technician) {
        technicianService.updateTechnician(technician);
    }

}

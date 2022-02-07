package com.servicecenter.cms.service;

import com.servicecenter.cms.model.Technician;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class RoundRobinAllocator implements ServiceCallAllocator {

    private TechnicianService technicianService;

    private int i = 0;

    public void setTechnicianService(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @Override
    public Technician allocate() {
        List<Technician> list = technicianService.findTechnician();
        i = i < list.size() ? i : 0;
        return list.get(i++);
    }
}

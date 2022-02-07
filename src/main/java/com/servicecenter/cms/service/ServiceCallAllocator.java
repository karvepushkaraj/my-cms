package com.servicecenter.cms.service;

import com.servicecenter.cms.model.Technician;

public interface ServiceCallAllocator {

    Technician allocate();

    void setTechnicianService(TechnicianService technicianService);
}

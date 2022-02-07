package com.servicecenter.cms.service;

import com.servicecenter.cms.model.ServiceCall;
import com.servicecenter.cms.model.Technician;
import com.servicecenter.cms.repository.TechnicianRepository;
import com.servicecenter.cms.util.ServiceLayerException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TechnicianServiceImpl implements TechnicianService {

    private TechnicianRepository repository;
    private ServiceCallAllocator serviceCallAllocator;
    private AppUserService appUserService;
    private WebClient webClient;

    @PostConstruct
    public void postConstruct() {
        serviceCallAllocator.setTechnicianService(this);
    }

    @Override
    public Technician findTechnician(String technicianId) {
        return repository.findById(technicianId)
                .orElseThrow(() -> new ServiceLayerException("Technician Not Found"));
    }

    @Override
    @Cacheable("technicians")
    public List<Technician> findTechnician() {
        return repository.findAll();
    }

    @Override
    @CacheEvict("technicians")
    public void addNewTechnician(Technician technician, String username, String password) {
        repository.insert(technician);
        appUserService.addNewUser(username, password, technician.getTechnicianId());
    }

    @Override
    @CacheEvict("technicians")
    public void removeTechnician(String technicianId) {
        repository.deleteById(technicianId);
        appUserService.deleteUser(technicianId);
    }

    @Override
    @CacheEvict("technicians")
    public void updateTechnician(Technician technician) {
        repository.save(technician);
    }

    @Override
    public Technician allocateServiceCall(ServiceCall serviceCall) {
        Technician technician = serviceCallAllocator.allocate();
        technician.addServiceCall(serviceCall.getId());
        repository.save(technician);
        return technician;
    }

    @Override
    public ServiceCall getServiceCall(String requestId) {
        return webClient.get()
                .uri(String.format("/?id=%s", requestId))
                .retrieve()
                .onStatus(p -> !p.is2xxSuccessful() ? true : false, r -> Mono.error(new ServiceLayerException("Unable to fetch data")))
                .bodyToMono(ServiceCall.class)
                .block();
    }

    @Override
    public void closeServiceCall(String technicianId, String requestId) {
        Technician technician = findTechnician(technicianId);
        technician.removeServiceCall(requestId);
        repository.save(technician);
        webClient.put()
                .uri(String.format("/%s", requestId))
                .retrieve()
                .onStatus(p -> !p.is2xxSuccessful() ? true : false, r -> Mono.error(new ServiceLayerException("Unable to close call")))
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void deleteServiceCall(String technicianId, String requestId) {
        Technician technician = findTechnician(technicianId);
        technician.deleteServiceCall(requestId);
        repository.save(technician);
    }


}

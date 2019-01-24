package de.thbingen.epro.project.data.service;

import de.thbingen.epro.project.data.repository.ServiceInstanceBindingRepository;
import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceInstanceBindingService {

    @Autowired
    private ServiceInstanceBindingRepository serviceInstanceBindingRepository;

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;


}

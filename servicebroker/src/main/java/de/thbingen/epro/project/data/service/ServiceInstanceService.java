package de.thbingen.epro.project.data.service;

import de.thbingen.epro.project.data.repository.ServiceInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceInstanceService {
    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

}

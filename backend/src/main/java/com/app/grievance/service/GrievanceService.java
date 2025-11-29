package com.app.grievance.service;

import com.app.grievance.model.Grievance;
import com.app.grievance.repository.GrievanceRepository;
import java.util.Date;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrievanceService {

    @Autowired
    private GrievanceRepository grievanceRepository;

    public Grievance getGrievanceById(Long id) {
        return grievanceRepository.findById(id).orElse(null);
    }

    
}

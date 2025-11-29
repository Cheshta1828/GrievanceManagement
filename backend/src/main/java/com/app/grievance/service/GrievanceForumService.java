package com.app.grievance.service;

import com.app.grievance.dto.GrievanceRequest;
import com.app.grievance.model.Comment;
import com.app.grievance.model.Grievance;
import com.app.grievance.repository.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class GrievanceForumService {

    @Autowired
    private GrievanceRepository grievanceRepository;

    // Fetch all grievances with optional filtering by status
    public List<Grievance> getAllGrievances(String status) {
        if (status != null) {
            return grievanceRepository.findByStatus(status);
        } else {
            return grievanceRepository.findAll();
        }
    }

    // Add comment
    public Grievance addComment(GrievanceRequest req) {

        Grievance g = grievanceRepository.findById(req.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Grievance not found with ID: " + req.getId()));

        // create comment object
        Comment c = new Comment();
        c.setUsername(req.getUsername());
        c.setComment(req.getComment());
        c.setGrievance(g);

        // attach comment
        g.getComments().add(c);

        // update status
        g.setStatus("Commented");

        return grievanceRepository.save(g);
    }

    // Search grievances
    public List<Grievance> searchGrievances(String query) {
        return grievanceRepository.searchByQuery(query);
    }

    // Get grievance by ID
    public Grievance getGrievanceById(Long id) {
        return grievanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // Sorted list
    public Page<Grievance> getAllGrievancesSorted(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return grievanceRepository.findAll(pageable);
    }

    // Filtering
    public List<Grievance> filterGrievances(String status, String createdBy, String assignedTo) {
        return grievanceRepository.filterGrievances(status, createdBy, assignedTo);
    }

    // Generate 5-digit ID
    private Long generateUnique5DigitId() {
        Random random = new Random();
        long id = 10000 + random.nextInt(90000);

        while (grievanceRepository.existsById(id)) {
            id = 10000 + random.nextInt(90000);
        }
        return id;
    }

    // Create grievance
    public Grievance createGrievance(Grievance grievance) {
//        grievance.setId(generateUnique5DigitId());
        grievance.setId(null);
        grievance.setCreatedAt(new Date());
        return grievanceRepository.save(grievance);
    }

    // Update grievance
    public Grievance updateGrievance(Long id, Grievance payload) {
        Grievance existing = grievanceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Grievance not found with ID: " + id));

        if (payload.getStatus() != null) {
            existing.setStatus(payload.getStatus());
        }
        if (payload.getDescription() != null) {
            existing.setDescription(payload.getDescription());
        }
        if (payload.getCategory() != null) {
            existing.setCategory(payload.getCategory());
        }
        if (payload.getAssignedTo() != null) {
            existing.setAssignedTo(payload.getAssignedTo());
        }

        return grievanceRepository.save(existing);
    }
}

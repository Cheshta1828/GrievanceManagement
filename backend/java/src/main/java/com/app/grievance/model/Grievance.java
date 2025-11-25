package com.app.grievance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "grievance")
@Getter
@Setter
public class Grievance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;   // OPEN, PENDING, RESOLVED
    private String category;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy="grievance", cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    // no-arg constructor (needed by JPA)
    public Grievance() {}

    // if you want a convenience constructor, drop the single "comment" param:
    public Grievance(String title,
                     String description,
                     String status,
                     String category,
                     String createdBy,
                     String assignedTo,
                     Date createdAt) {
        this.title       = title;
        this.description = description;
        this.status      = status;
        this.category    = category;
        this.createdBy   = createdBy;
        this.assignedTo  = assignedTo;
        this.createdAt   = createdAt;
    }
}
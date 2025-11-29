package com.app.grievance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "grievance")  // Ensure the table name matches the one in your MySQL database
public class Grievance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // Changed to Long for numeric ID

  private String title;
  private String description;
  private String status;       // OPEN, PENDING, RESOLVED
  private String category;     // Newly added field
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "assigned_to")
  private String assignedTo;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.util.Date createdAt;


  @OneToMany(mappedBy = "grievance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Comment> comments;


  // Default constructor
  public Grievance() {}

  // Constructor with fields (excluding id, which is auto-generated)

  public Grievance(String title, String description, String status, String category, String createdBy, String assignedTo, java.util.Date createdAt, List<Comment> comments) {

    this.title = title;
    this.description = description;
    this.status = status;
    this.category = category;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.assignedTo = assignedTo;
    this.comments = comments;
  }
}
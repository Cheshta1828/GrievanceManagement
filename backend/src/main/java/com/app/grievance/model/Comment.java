package com.app.grievance.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "username")
    private String username;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grievance_id", nullable = false)
    @JsonIgnore
    private Grievance grievance;

    // this is not a column in the DB
    @Transient
    @JsonProperty("grievanceId")
    private Long grievanceId;

    @JsonProperty("grievanceId")
    public void setGrievanceId(Long grievanceId) {
        this.grievanceId = grievanceId;
        // populate the actual relationship stub
        this.grievance = new Grievance();
        this.grievance.setId(grievanceId);
    }

    // optional: expose it in JSON if you like
    public Long getGrievanceId() {
        return this.grievance != null ? this.grievance.getId() : null;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

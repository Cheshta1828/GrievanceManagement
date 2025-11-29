package com.app.grievance.dto;

import jakarta.validation.constraints.NotBlank;

public class GrievanceRequest {

  private Long id;

  @NotBlank(message = "Username cannot be blank")
  private String username;

  @NotBlank(message = "Comment cannot be blank")
  private String comment;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}

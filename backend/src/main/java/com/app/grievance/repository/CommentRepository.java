package com.app.grievance.repository;

import com.app.grievance.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByGrievance_Id(Long grievanceId);
    List<Comment> findByGrievance_IdAndUsername(Long grievanceId, String username);
}

package com.app.grievance.service;

import com.app.grievance.model.Comment;
import com.app.grievance.model.Grievance;
import com.app.grievance.repository.CommentRepository;
import com.app.grievance.repository.GrievanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GrievanceRepository grievanceRepository;

    public Comment createComment(Comment comment, Long grievanceId) {
        Grievance grievance = grievanceRepository.findById(grievanceId)
                .orElseThrow(() -> new IllegalArgumentException("Grievance not found"));
        comment.setGrievance(grievance);
        // Only save the comment, do not add to grievance.getComments() or save grievance here
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByGrievanceId(Long grievanceId) {
        return commentRepository.findByGrievance_Id(grievanceId);
    }

    public List<Comment> getCommentsByGrievanceIdAndUsername(Long grievanceId, String username) {
        return commentRepository.findByGrievance_IdAndUsername(grievanceId, username);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            comment.setUsername(updatedComment.getUsername());
            comment.setComment(updatedComment.getComment());
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found with ID: " + id);
        }
        commentRepository.deleteById(id);
    }
}

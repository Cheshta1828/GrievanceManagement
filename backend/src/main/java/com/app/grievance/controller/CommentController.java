package com.app.grievance.controller;

import com.app.grievance.model.Comment;
import com.app.grievance.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        // grievanceId already wired into comment.getGrievance().getId()
        Comment saved = commentService.createComment(comment, comment.getGrievance().getId());
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@RequestParam String gid,
                                                     @RequestParam String username) {
        Long grievanceId = Long.parseLong(gid);
        return ResponseEntity.ok(
            commentService.getCommentsByGrievanceIdAndUsername(grievanceId, username)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        try {
            Comment updated = commentService.updateComment(id, updatedComment);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            // Return 404 if comment not found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}

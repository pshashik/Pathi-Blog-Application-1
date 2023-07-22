package com.pathi.blog.controller;

import com.pathi.blog.payload.CommentDto;
import com.pathi.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(name = "postId") Long postId){
        return ResponseEntity.ok(commentService.getAllComments(postId));
    }

    @GetMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getAllCommentById(@PathVariable(name = "postId") Long postId,
                                                              @PathVariable(name = "id") Long id){
        return ResponseEntity.ok(commentService.getCommentById(postId,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") Long postId,
                                                        @PathVariable(name = "id") Long id,
                                                    @Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(postId,id,commentDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("posts/{postId}/comments/{id}")
    public String deleteComment(@PathVariable(name = "postId") Long postId,
                                                        @PathVariable(name = "id") Long id){
        commentService.deleteComment(postId,id);
        return "Comment successfully deleted!";
    }
}

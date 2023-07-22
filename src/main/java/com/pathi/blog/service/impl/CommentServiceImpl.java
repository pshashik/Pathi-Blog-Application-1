package com.pathi.blog.service.impl;

import com.pathi.blog.entity.Comment;
import com.pathi.blog.entity.Post;
import com.pathi.blog.exception.BlogApiExceptionHandler;
import com.pathi.blog.exception.ResourceNotFoundException;
import com.pathi.blog.payload.CommentDto;
import com.pathi.blog.repository.CommentRepository;
import com.pathi.blog.repository.PostRepository;
import com.pathi.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return modelMapper.map(newComment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllComments(Long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream().map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long id) {
        return modelMapper.map(validatePostAndCommentId(postId, id), CommentDto.class);
    }

    @Override
    public CommentDto updateComment(Long postId, Long id, CommentDto commentDto) {
        Comment comment = validatePostAndCommentId(postId, id);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment newComment = commentRepository.save(comment);
        return modelMapper.map(newComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long postId, Long id) {
        Comment comment = validatePostAndCommentId(postId, id);
        commentRepository.delete(comment);
    }

    private Comment validatePostAndCommentId(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        if (!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiExceptionHandler(HttpStatus.BAD_REQUEST, "Comment does not belongs to post.");

        return comment;
    }

}

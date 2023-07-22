package com.pathi.blog.service;

import com.pathi.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    PostDto updatePost(Long id,PostDto postDto);

    void deletePost(Long id);

    List<PostDto> getPostsByCategoryId(Long categoryId);
}

package com.pathi.blog.service.impl;

import com.pathi.blog.entity.Category;
import com.pathi.blog.entity.Post;
import com.pathi.blog.exception.ResourceNotFoundException;
import com.pathi.blog.payload.PostDto;
import com.pathi.blog.repository.CategoryRepository;
import com.pathi.blog.repository.PostRepository;
import com.pathi.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = postRepository.save(modelMapper.map(postDto,Post.class));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepository.save(post);
        return modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
        List<Post> postList = postRepository.findByCategoryId(category.getId());
        return postList.stream().map(post->modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
    }
}

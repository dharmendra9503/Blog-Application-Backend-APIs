package com.example.blogapis.service.implementation;

import com.example.blogapis.exception.ResourceNotFoundException;
import com.example.blogapis.model.Category;
import com.example.blogapis.model.Post;
import com.example.blogapis.model.User;
import com.example.blogapis.payloads.PostDataTransfer;
import com.example.blogapis.payloads.PostResponse;
import com.example.blogapis.repository.CategoryRepository;
import com.example.blogapis.repository.PostRepository;
import com.example.blogapis.repository.UserRepository;
import com.example.blogapis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PostDataTransfer createPost(PostDataTransfer postDTO, Integer userId, Integer categoryId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        Post saved = postRepository.save(post);

        return modelMapper.map(saved, PostDataTransfer.class);
    }

    @Override
    public PostDataTransfer updatePost(PostDataTransfer postDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        post.setDate(new Date());

        Post updated = postRepository.save(post);
        return modelMapper.map(updated, PostDataTransfer.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));
        postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> pagePost  = postRepository.findAll(pageable);
        List<Post> posts = pagePost.getContent();

        List<PostDataTransfer> postDTO = posts.stream().map((post) -> modelMapper.map(post, PostDataTransfer.class))
                .toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTO);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDataTransfer getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));
        return modelMapper.map(post, PostDataTransfer.class);
    }

    @Override
    public List<PostDataTransfer> getAllPostByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<Post> posts = postRepository.findByCategory(category);

        return posts.stream().map((post) -> modelMapper.map(post, PostDataTransfer.class)).toList();
    }

    @Override
    public List<PostDataTransfer> getAllPostByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User", "User Id", userId));
        List<Post> posts = postRepository.findByUser(user);

        return posts.stream().map((post) -> modelMapper.map(post, PostDataTransfer.class)).toList();
    }

    @Override
    public List<PostDataTransfer> searchPost(String keyword) {
        return null;
    }

}

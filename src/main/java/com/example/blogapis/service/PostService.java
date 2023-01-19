package com.example.blogapis.service;

import com.example.blogapis.payloads.PostDataTransfer;
import com.example.blogapis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
    PostDataTransfer createPost(PostDataTransfer postDTO, Integer userId, Integer categoryId);

    //update
    PostDataTransfer updatePost(PostDataTransfer postDTO, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get all post
    PostResponse getAllPost(Integer pageNumber, Integer  pageSize, String sortBy, String sortDir);

    //get single post
    PostDataTransfer getPostById(Integer postId);

    //get all post by category
    List<PostDataTransfer> getAllPostByCategory(Integer categoryId);

    //get all post by user
    List<PostDataTransfer> getAllPostByUser(Integer userId);

    //search post
    List<PostDataTransfer> searchPost(String keyword);
}

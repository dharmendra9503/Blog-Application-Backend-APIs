package com.example.blogapis.service;

import com.example.blogapis.model.Post;
import com.example.blogapis.payloads.PostDataTransfer;

import java.util.List;

public interface PostService {

    //create
    PostDataTransfer createPost(PostDataTransfer postDTO, Integer userId, Integer categoryId);

    //update
    PostDataTransfer updatePost(PostDataTransfer postDTO, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get all post
    List<PostDataTransfer> getAllPost();

    //get single post
    PostDataTransfer getPostById(Integer postId);

    //get all post by category
    List<PostDataTransfer> getAllPostByCategory(Integer categoryId);

    //get all post by user
    List<PostDataTransfer> getAllPostByUser(Integer userId);

    //search post
    List<PostDataTransfer> searchPost(String keyword);
}

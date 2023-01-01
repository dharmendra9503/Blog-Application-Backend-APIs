package com.example.blogapis.controller;

import com.example.blogapis.payloads.ApiResponce;
import com.example.blogapis.payloads.PostDataTransfer;
import com.example.blogapis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")
public class PostController {

    @Autowired
    private PostService postService;

    //create
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDataTransfer> createPost(@RequestBody PostDataTransfer postDTO,
                                                       @PathVariable Integer userId,
                                                       @PathVariable Integer categoryId){
        PostDataTransfer created = postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //get by user
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDataTransfer>> getPostsByUser(@PathVariable Integer userId){
        return new ResponseEntity<>(postService.getAllPostByUser(userId), HttpStatus.OK);
    }

    //get by category
    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDataTransfer>> getPostsByCategory(@PathVariable Integer categoryId){
        return new ResponseEntity<>(postService.getAllPostByCategory(categoryId), HttpStatus.OK);
    }

    //get all post
    @GetMapping("/posts")
    public ResponseEntity<List<PostDataTransfer>> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
        ){
        return new ResponseEntity<>(postService.getAllPost(pageNumber, pageSize), HttpStatus.OK);
    }

    //get post by id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDataTransfer> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    //update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDataTransfer> updatePost(@RequestBody PostDataTransfer postDTO, @PathVariable Integer postId){

        return new ResponseEntity<>(postService.updatePost(postDTO, postId), HttpStatus.OK);

    }

    //delete post
    @DeleteMapping("/post/{postId}")
    public ApiResponce deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);

        return new ApiResponce("Post with id "+postId+" successfully deleted !!", true);
    }

}
package com.example.blogapis.controller;

import com.example.blogapis.config.AppConstants;
import com.example.blogapis.payloads.ApiResponce;
import com.example.blogapis.payloads.PostDataTransfer;
import com.example.blogapis.payloads.PostResponse;
import com.example.blogapis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
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
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)
            Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)
            Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.POST_SORT_BY, required = false)
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false)
            String sortDir
        ){
        return new ResponseEntity<>(postService.getAllPost(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    //get post by id
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDataTransfer> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }

    //update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDataTransfer> updatePost(@RequestBody PostDataTransfer postDTO,
                                                        @PathVariable Integer postId){

        return new ResponseEntity<>(postService.updatePost(postDTO, postId), HttpStatus.OK);

    }

    //delete post
    @DeleteMapping("/post/{postId}")
    public ApiResponce deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);

        return new ApiResponce("Post with id "+postId+" successfully deleted !!", true);
    }

}
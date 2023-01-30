package com.example.blogapis.controller;

import com.example.blogapis.config.AppConstants;
import com.example.blogapis.exception.ResourceNotFoundException;
import com.example.blogapis.model.Post;
import com.example.blogapis.payloads.ApiResponce;
import com.example.blogapis.payloads.ImageResponse;
import com.example.blogapis.payloads.PostDataTransfer;
import com.example.blogapis.payloads.PostResponse;
import com.example.blogapis.service.ImageService;
import com.example.blogapis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ModelMapper modelMapper;

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


    /*
        This is API is used to upload image for post. Only 1 image is allowed to upload for one post.
        This will store image into file storage and store that image path in database.
        Image path store in database and table name is "image_data".
        This table will store image name, image type, image path, image modified date with postId
        And for Post image name will also store in "post" table in database.
    */
    @PostMapping("/post/upload/image/{postId}")
    public ResponseEntity<?> uploadPostImage(
            @RequestParam("image") MultipartFile image, @PathVariable Integer postId){

        //If file is not selected.
        if(image.isEmpty()){
            return new ResponseEntity<>(new ImageResponse(null, "Please Select File !!"), HttpStatus.NOT_FOUND);
        }

        String imageName;

        PostDataTransfer postDTO = postService.getPostById(postId);

        if(postDTO == null){
            return new ResponseEntity<>(new ResourceNotFoundException("Post", "Post Id", postId),
                    HttpStatus.NOT_FOUND);
        }
        else {
            try {
                imageName = imageService.uploadImage(image, postId);

                postDTO.setImageName(imageName);
                PostDataTransfer updated = postService.updatePost(postDTO, postId);
                return new ResponseEntity<>(updated, HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(
                        e.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/post/{postId}/image/{imageName}")
    public ResponseEntity<?> download(@PathVariable String imageName, @PathVariable Integer postId) throws IOException {

        PostDataTransfer postDTO = postService.getPostById(postId);

        byte[] imageData = imageService.downloadImage(imageName, modelMapper.map(postDTO, Post.class));

        if(imageData != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                    .body(imageData);
        }
        else{
            return new ResponseEntity<>(new ImageResponse(imageName, "File doesn't Exist !!"),
                    HttpStatus.NOT_FOUND);
        }
    }
}
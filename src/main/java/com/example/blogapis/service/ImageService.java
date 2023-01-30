package com.example.blogapis.service;

import com.example.blogapis.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String uploadImage(MultipartFile image, Integer postId) throws IOException;

    byte[] downloadImage(String imageName, Post post) throws IOException;

}

package com.example.blogapis.repository;

import com.example.blogapis.model.ImageData;
import com.example.blogapis.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    ImageData findByPost(Post post);

    Optional<ImageData> findByNameAndPost(String imageName, Post post);

}
package com.example.blogapis.repository;

import com.example.blogapis.model.Category;
import com.example.blogapis.model.Post;
import com.example.blogapis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {


    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
}

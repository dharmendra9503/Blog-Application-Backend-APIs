package com.example.blogapis.payloads;

import com.example.blogapis.model.Category;
import com.example.blogapis.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDataTransfer {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date date;
    private Category category;
    private User user;

}

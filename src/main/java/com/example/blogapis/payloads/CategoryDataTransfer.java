package com.example.blogapis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter

public class CategoryDataTransfer {
    private String categoryId;

    @NotBlank
    private String categoryTitle;

    @NotBlank
    private String categoryDescription;
}

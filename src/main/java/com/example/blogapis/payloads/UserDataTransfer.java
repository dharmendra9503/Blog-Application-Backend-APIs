package com.example.blogapis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter

public class UserDataTransfer {
    private Integer id;

    @NotEmpty
    private String name;

    @Email(message = "Email Address Is Not Valid!!!")
    private String email;

    @NotEmpty
    @Size(min=5, max=13, message = "Password must be minimum of 5 characters and maximum of 13 characters!")
    private String password;

    @NotEmpty
    private String about;

}

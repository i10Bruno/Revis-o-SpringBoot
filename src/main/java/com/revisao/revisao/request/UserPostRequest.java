package com.revisao.revisao.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPostRequest {
    @NotBlank(message = "The field' FirstName is required")
    private String firstName;
    @NotBlank(message = "The field' LastName is required")
    private String lastName;
    @NotBlank(message = "E-mail is not valid")
    private String email;



}

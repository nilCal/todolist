package com.mvp.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Setter
@Getter
public class UserDTO {

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @Email
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    private String email;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}

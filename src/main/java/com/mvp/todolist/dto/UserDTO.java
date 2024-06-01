package com.mvp.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
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

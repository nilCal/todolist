package com.mvp.todolist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class TaskDTO {

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @Min(value = 1)
    private Long userCode;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "Pending|Done", message = "Status must be 'Pending' ou 'Done'!")
    private String status;

}

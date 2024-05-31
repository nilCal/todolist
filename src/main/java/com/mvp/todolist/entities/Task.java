package com.mvp.todolist.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "creationdate")
    private LocalDateTime creationDate;

    @Column(name = "updatedate")
    private LocalDateTime updateDate;

    @Min(value = 1)
    @Column(name = "usercode")
    private Long userCode;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "Pending|Done", message = "Status must be 'Pending' ou 'Done'!")
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

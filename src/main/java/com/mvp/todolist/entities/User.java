package com.mvp.todolist.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;

    @Email
    @Size(min = 1, max = 255)
    @Column(unique = true, name = "email")
    private String email;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;
    
    @OneToMany(mappedBy="user")
    private Set<Task> task;
}


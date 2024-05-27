package com.mvp.todolist.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

	@Column(name = "usercode")
	private Long userCode;
	
	@NotEmpty
    @NotBlank
    @Pattern(regexp = "Pending|Done", message = "Status must be 'Pending' ou 'Done'!")
	@Column(name = "status")
	private String status;
	
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;


}

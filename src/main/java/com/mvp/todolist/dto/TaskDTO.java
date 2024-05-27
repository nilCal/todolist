package com.mvp.todolist.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TaskDTO {

	@NotEmpty
	@NotBlank
	@Size(min = 1, max = 255)
	private String title;

	@NotEmpty
	@NotBlank
	@Size(min = 1, max = 255)
	private String description;

	private Long userCode;

	@NotEmpty
	@NotBlank
	@Pattern(regexp = "Pending|Done", message = "Status must be 'Pending' ou 'Done'!")
	private String status;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

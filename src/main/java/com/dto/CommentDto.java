package com.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CommentDTO {

	private Long id;
	
	@NotBlank(message = "Comment cannot be empty or blank")
	@Size(min = 3, max = 200, message = "Comment's size must be between 3 and 200")
	private String comment;
	
	@NotNull(message = "Blog ID cannot be null")
	@Positive(message = "Blog ID must be positive")
	private Long blogId;

	public CommentDTO(Long id, String comment, Long blogId) {
		super();
		this.id = id;
		this.comment = comment;
		this.blogId = blogId;
	}

	public CommentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

}

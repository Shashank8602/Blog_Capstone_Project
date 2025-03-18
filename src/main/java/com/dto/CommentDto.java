package com.dto;

public class CommentDTO {

	// validations to be added

	private Long id;
	private String comment;
	private Long blog_id;

	public CommentDTO(Long id, String comment, Long blog_id) {
		super();
		this.id = id;
		this.comment = comment;
		this.blog_id = blog_id;
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

	public Long getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(Long blog_id) {
		this.blog_id = blog_id;
	}

}

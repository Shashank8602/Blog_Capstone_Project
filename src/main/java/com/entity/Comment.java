package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity // @Entity is a JPA annotation that marks a class as a database entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comment;

	@ManyToOne //defines a many-to-one relationship
	@JoinColumn(name = "blog_id") //specifies the foreign key column in the child entity's table that links it to the parent entity.
	private Blog blog;

	public Comment(Long id, String comment, Blog blog) {
		super();
		this.id = id;
		this.comment = comment;
		this.blog = blog;
	}

	public Comment() {
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

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

}

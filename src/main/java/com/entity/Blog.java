package com.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity // @Entity is a JPA annotation that marks a class as a database entity
public class Blog {

	@Id // @Id marks the field as the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue(strategy = GenerationType.IDENTITY) auto-generates unique values for it using the database's identity column.
	private Long id;
	private String title;
	private String content;

	@OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true) // establishes a one-to-many relationship
	// mappedBy = "blog" → Refers to the field in the child entity that owns the relationship.
	// cascade = CascadeType.ALL → Applies all cascading operations (persist, merge, remove, etc.) to related entities.
	//orphanRemoval = true → Automatically deletes child entities if they are removed from the parent's collection.
	private List<Comment> comments;

	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Blog(Long id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}

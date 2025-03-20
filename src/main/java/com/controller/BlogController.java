package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BlogDTO;
import com.dto.CommentDTO;
import com.service.BlogService;
import com.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blog Controller", description = "APIs for managing blogs and comments")
public class BlogController {

	private final BlogService blogService;

	private final CommentService commentService;

	public BlogController(BlogService blogService, CommentService commentService) {
		this.blogService = blogService;
		this.commentService = commentService;
	}
	
	//BLOGS

	@PostMapping
	@Operation(summary = "Create New Blog", description = "Create a new blog")
	public ResponseEntity<BlogDTO> createNewBlog(@Valid @RequestBody BlogDTO blogDto) {
		BlogDTO blog = blogService.addBlog(blogDto);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Blog by ID", description = "Fetch a specific blog using its ID")
	public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
		BlogDTO blog = blogService.getBlogById(id);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.FOUND);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Blog", description = "Update an existing blog by its ID")
	public ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id,@Valid @RequestBody BlogDTO blogDto) {
		BlogDTO blog = blogService.modifyBlog(id, blogDto);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Blog", description = "Delete a blog using its ID")
	public ResponseEntity<Boolean> deleteBlog(@PathVariable Long id) {
		boolean deleteStatus = blogService.deleteBlog(id);
		return new ResponseEntity<Boolean>(deleteStatus, HttpStatus.OK);
	}
	
	//COMMENTS

	@PostMapping("/comment")
	@Operation(summary = "Add Comment to Blog", description = "Add a comment to a blog using blog's ID")
	public ResponseEntity<CommentDTO> createNewComment(@Valid @RequestBody CommentDTO commentDto) {
		CommentDTO comment = commentService.addComment(commentDto);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/comment")
	@Operation(summary = "Get Comments of a Blog", description = "Get comments of a blog using blog's ID")
	public ResponseEntity<List<CommentDTO>> getCommentsForBlogId(@PathVariable Long id){
		List<CommentDTO> commentsByBlogId = commentService.getCommentByBlogId(id);
		return new ResponseEntity<List<CommentDTO>>(commentsByBlogId, HttpStatus.FOUND);
	}

}

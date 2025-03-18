package com.controller;

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

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

	private final BlogService blogService;

	private final CommentService commentService;

	public BlogController(BlogService blogService, CommentService commentService) {
		this.blogService = blogService;
		this.commentService = commentService;
	}

	@PostMapping
	public ResponseEntity<BlogDTO> createNewBlog(@RequestBody BlogDTO blogDto) {
		BlogDTO blog = blogService.addBlog(blogDto);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
		BlogDTO blog = blogService.getBlogById(id);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id, @RequestBody BlogDTO blogDto) {
		BlogDTO blog = blogService.modifyBlog(id, blogDto);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteBlog(@PathVariable Long id) {
		boolean deleteStatus = blogService.deleteBlog(id);
		return new ResponseEntity<Boolean>(deleteStatus, HttpStatus.OK);
	}

	@PostMapping("/comment")
	public ResponseEntity<CommentDTO> createNewComment(@RequestBody CommentDTO commentDto) {
		CommentDTO comment = commentService.addComment(commentDto);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.CREATED);
	}

}

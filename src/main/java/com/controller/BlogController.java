package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/blogs")
@Validated
@Tag(name = "Blog Controller", description = "APIs for managing blogs and comments")
public class BlogController {

	private final BlogService blogService;

	private final CommentService commentService;

	public BlogController(BlogService blogService, CommentService commentService) {
		this.blogService = blogService;
		this.commentService = commentService;
	}

	// BLOGS

	//CREATE BLOG
	@PostMapping
	@Tag(name = "Create New Blog")
	@Operation(summary = "Create New Blog", description = "Create a new blog")
	public ResponseEntity<BlogDTO> createNewBlog(@Valid @RequestBody BlogDTO blogDto) {
		BlogDTO blog = blogService.addBlog(blogDto);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.CREATED);
	}

	//GET BLOG BY ID
	@GetMapping("/{id}")
	@Tag(name = "Get Blog by ID")
	@Operation(summary = "Get Blog by ID", description = "Fetch a specific blog using its ID")
	public ResponseEntity<BlogDTO> getBlogById(@PathVariable("id") @Positive(message = "Blog ID must be positive") Long blogId) {
		BlogDTO blog = blogService.getBlogById(blogId);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.FOUND);
	}

	//GET ALL BLOGS
	@GetMapping
	@Tag(name = "Get All Blogs")
	@Operation(summary = "Get All Blogs", description = "Fetch all blogs")
	public ResponseEntity<List<BlogDTO>> getAllBlogs() {
		List<BlogDTO> allBlogs = blogService.getAllBlogs();
		return new ResponseEntity<List<BlogDTO>>(allBlogs, HttpStatus.FOUND);
	}

	
	//UPDATE BLOG
	@PutMapping("/{id}")
	@Tag(name = "Update Blog")
	@Operation(summary = "Update Blog", description = "Update an existing blog by its ID")
	public ResponseEntity<BlogDTO> updateBlog(@PathVariable("id") @Positive(message = "Blog ID must be positive") Long blogId, @Valid @RequestBody BlogDTO blogDto) {
		BlogDTO blog = blogService.modifyBlog(blogId, blogDto);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.OK);
	}

	//DELETE BLOG
	@DeleteMapping("/{id}")
	@Tag(name = "Delete Blog")
	@Operation(summary = "Delete Blog", description = "Delete a blog using its ID")
	public ResponseEntity<String> deleteBlog(@PathVariable("id") @Positive(message = "Blog ID must be positive") Long blogId) {
		boolean deleteStatus = blogService.deleteBlog(blogId);
		if(deleteStatus) {
			return new ResponseEntity<String>("Blog deleted with ID: "+blogId, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Blog not deleted with ID: "+blogId, HttpStatus.OK);
	}

	// COMMENTS

	//CREATE A COMMENT
	@PostMapping("/comment")
	@Tag(name = "Add Comment to Blog")
	@Operation(summary = "Add Comment to Blog", description = "Add a comment to a blog using blog's ID")
	public ResponseEntity<CommentDTO> createNewComment(@Valid @RequestBody CommentDTO commentDto) {
		CommentDTO comment = commentService.addComment(commentDto);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.CREATED);
	}
	
	//GET ALL COMMENTS FOR BLOG
	@GetMapping("/{id}/comment")
	@Tag(name = "Get All Comments of a Blog")
	@Operation(summary = "Get Comments of a Blog", description = "Get comments of a blog using blog's ID")
	public ResponseEntity<List<CommentDTO>> getCommentsForBlogId(@PathVariable("id") @Positive(message = "Blog ID must be positive") Long blogId) {
		List<CommentDTO> commentsByBlogId = commentService.getCommentByBlogId(blogId);
		return new ResponseEntity<List<CommentDTO>>(commentsByBlogId, HttpStatus.FOUND);
	}

	//GET A COMMENT FOR A BLOG
	@GetMapping("/{blogId}/comment/{commentId}")
	@Tag(name = "Get Comment of a Blog")
	@Operation(summary = "Get Comment of a Blog", description = "Get a comment of a blog using comment's ID")
	public ResponseEntity<CommentDTO> getCommentOfBlog(@PathVariable @Positive(message = "Comment ID must be positive") Long commentId, @PathVariable @Positive(message = "Blog ID must be positive") Long blogId) {
		CommentDTO comment = commentService.getCommentByIdForBlogById(commentId, blogId);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.FOUND);
	}

	//DELETE A COMMENT FOR A BLOG
	@DeleteMapping("/{blogId}/comment/{commentId}")
	@Tag(name = "Delete Comment of a Blog")
	@Operation(summary = "Delete Comment of a Blog", description = "Delete a comment of a blog using comment's ID")
	public ResponseEntity<String> deleteCommentOfBlog(@PathVariable @Positive(message = "Comment ID must be positive") Long commentId, @PathVariable @Positive(message = "Blog ID must be positive") Long blogId) {
		Boolean deleteValue = commentService.deleteCommentByIdForBlogById(commentId, blogId);
		if (deleteValue) {
			return new ResponseEntity<String>("Comment deleted with ID: " + commentId + " of blog with ID: " + blogId,
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Comment not deleted with ID: " + commentId + " of blog with ID: " + blogId,
				HttpStatus.BAD_REQUEST);
	}

}

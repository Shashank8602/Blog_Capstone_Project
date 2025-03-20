package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dto.CommentDTO;
import com.entity.Blog;
import com.entity.Comment;
import com.exception.NoCommentExistException;
import com.exception.ResourceNotFoundException;
import com.repository.BlogRepository;
import com.repository.CommentRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;

	private final BlogRepository blogRepository;

	private final ModelMapper modelMapper;

	public CommentService(CommentRepository commentRepository, BlogRepository blogRepository, ModelMapper modelMapper) {
		this.commentRepository = commentRepository;
		this.blogRepository = blogRepository;
		this.modelMapper = modelMapper;
	}

	// BUSINESS CODE

	// ADD COMMENT
	public CommentDTO addComment(CommentDTO commentDto) {
		Blog blog = blogRepository.findById(commentDto.getBlogId())
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + commentDto.getBlogId()));
		Comment comment = getEntity(commentDto);
		comment.setBlog(blog);

		Comment savedComment = commentRepository.save(comment);
		CommentDTO savedDto = getDto(savedComment);
		savedDto.setBlogId(commentDto.getBlogId());

		return savedDto;
	}

	// GET COMMENTS FOR BLOG ID
	public List<CommentDTO> getCommentByBlogId(Long id) {
		Blog blog = blogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));
		List<Comment> commentForBlog = commentRepository.findByBlogId(blog.getId());

		if (commentForBlog.isEmpty()) {
			throw new NoCommentExistException("No comment(s) exists for blog with ID: " + id);
		}

		List<CommentDTO> commentDtoForBlog = commentForBlog.stream().map(this::getDto).collect(Collectors.toList());

		return commentDtoForBlog;
	}

	// GET SPECIFIC COMMENT BY ID FOR BLOG WITH ID
	public CommentDTO getCommentByIdForBlogById(Long commentId, Long blogId) {
		blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + blogId));

		List<Comment> comments = commentRepository.findByBlogId(blogId);

		Comment foundComment = comments.stream().filter(c -> c.getId().equals(commentId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));

		return getDto(foundComment);
	}

	// DELETE COMMENT BY ITS ID
	public Boolean deleteCommentByIdForBlogById(Long commentId, Long blogId) {
		blogRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + blogId));

		List<Comment> comments = commentRepository.findByBlogId(blogId);

		Comment foundComment = comments.stream().filter(c -> c.getId().equals(commentId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));

		commentRepository.delete(foundComment);
		return true;

	}

	// CONVERSION METHODS

	private Comment getEntity(CommentDTO dto) {
		return modelMapper.map(dto, Comment.class);
	}

	private CommentDTO getDto(Comment entity) {
		return modelMapper.map(entity, CommentDTO.class);
	}

}

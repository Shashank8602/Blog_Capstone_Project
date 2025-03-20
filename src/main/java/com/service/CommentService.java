package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dto.CommentDTO;
import com.entity.Blog;
import com.entity.Comment;
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
		List<CommentDTO> commentDtoForBlog = commentForBlog.stream().map(this::getDto).collect(Collectors.toList());

		return commentDtoForBlog;
	}

	// CONVERSION METHODS

	private Comment getEntity(CommentDTO dto) {
		return modelMapper.map(dto, Comment.class);
	}

	private CommentDTO getDto(Comment entity) {
		return modelMapper.map(entity, CommentDTO.class);
	}

}

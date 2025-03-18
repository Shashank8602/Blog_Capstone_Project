package com.service;

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

	private final CommentRepository cr;

	private final BlogRepository br;

	private final ModelMapper modelMapper;

	public CommentService(CommentRepository cr, BlogRepository br, ModelMapper modelMapper) {
		this.cr = cr;
		this.br = br;
		this.modelMapper = modelMapper;
	}

	// BUSINESS CODE
	public CommentDTO addComment(CommentDTO commentDto) {
		Blog blog = br.findById(commentDto.getBlog_id())
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + commentDto.getBlog_id()));
		Comment comment = getEntity(commentDto);
		comment.setBlog(blog);

		Comment savedComment = cr.save(comment);
		CommentDTO savedDto = getDto(savedComment);
		savedDto.setBlog_id(commentDto.getBlog_id());

		return savedDto;
	}

	// CONVERSION METHODS

	private Comment getEntity(CommentDTO dto) {
		return modelMapper.map(dto, Comment.class);
	}

	private CommentDTO getDto(Comment entity) {
		return modelMapper.map(entity, CommentDTO.class);
	}

}

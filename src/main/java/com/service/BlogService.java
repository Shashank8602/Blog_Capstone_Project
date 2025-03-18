package com.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dto.BlogDTO;
import com.entity.Blog;
import com.exception.ResourceNotFoundException;
import com.repository.BlogRepository;

@Service
public class BlogService {

	private final BlogRepository br;

	private final ModelMapper modelMapper;

	public BlogService(BlogRepository br, ModelMapper modelMapper) {
		this.br = br;
		this.modelMapper = modelMapper;
	}

	// BUSINESS CODES
	// ADD
	public BlogDTO addBlog(BlogDTO blogDto) {
		Blog savedBlog = br.save(getEntity(blogDto));
		return getDto(savedBlog);
	}

	// GET BY ID
	public BlogDTO getBlogById(Long id) {
		Blog blog = br.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));
		return getDto(blog);
	}

	// UPDATE
	public BlogDTO modifyBlog(Long id, BlogDTO blogDto) {
		// fetch
		Blog blog = br.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));

		// modify
		blog.setTitle(blogDto.getTitle());
		blog.setContent(blogDto.getContent());

		// save and return
		BlogDTO dto = getDto(br.save(blog));
		return dto;
	}

	//DELETE
	public boolean deleteBlog(Long id) {
		Blog blog = br.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));
		br.delete(blog);
		return true;
	}

	// MODELMAPPER CONVERSION METHODS

	private Blog getEntity(BlogDTO dto) {
		return modelMapper.map(dto, Blog.class);
	}

	private BlogDTO getDto(Blog entity) {
		return modelMapper.map(entity, BlogDTO.class);
	}

}

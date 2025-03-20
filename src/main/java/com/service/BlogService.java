package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dto.BlogDTO;
import com.entity.Blog;
import com.exception.ResourceNotFoundException;
import com.repository.BlogRepository;

@Service
public class BlogService {

	private final BlogRepository blogRepository;

	private final ModelMapper modelMapper;

	public BlogService(BlogRepository blogRepository, ModelMapper modelMapper) {
		this.blogRepository = blogRepository;
		this.modelMapper = modelMapper;
	}

	// BUSINESS CODES
	// ADD
	public BlogDTO addBlog(BlogDTO blogDto) {
		Blog savedBlog = blogRepository.save(getEntity(blogDto));
		return getDto(savedBlog);
	}

	// GET BY ID
	public BlogDTO getBlogById(Long id) {
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));
		return getDto(blog);
	}
	
	//GET ALL BLOGS
	public List<BlogDTO> getAllBlogs(){
		List<Blog> allBlogs = blogRepository.findAll();
		List<BlogDTO> collectBlogsDtos = allBlogs.stream()
	            .map(this::getDto)  // Using getDto() method
	            .collect(Collectors.toList());
		return collectBlogsDtos;
	}

	// UPDATE
	public BlogDTO modifyBlog(Long id, BlogDTO blogDto) {
		// fetch
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));

		// modify
		blog.setTitle(blogDto.getTitle());
		blog.setContent(blogDto.getContent());

		// save and return
		BlogDTO dto = getDto(blogRepository.save(blog));
		return dto;
	}

	//DELETE
	public boolean deleteBlog(Long id) {
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));
		blogRepository.delete(blog);
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

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
	/*
	 * Saves a new blog in the database. 
	 * Converts BlogDTO to Blog entity, saves it,
	 * and returns the corresponding BlogDTO.
	 */
	public BlogDTO addBlog(BlogDTO blogDto) {
		Blog savedBlog = blogRepository.save(getEntity(blogDto));
		return getDto(savedBlog);
	}

	// GET BY ID
	/*
	 * Retrieves a blog by its ID. 
	 * Throws ResourceNotFoundException if the blog does not exist.
	 */
	public BlogDTO getBlogById(Long id) {
		Blog blog = blogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));
		return getDto(blog);
	}

	// GET ALL BLOGS
	/*
	 * Retrieves all blogs from the database. 
	 * Converts each Blog entity to BlogDTO and returns a list of DTOs.
	 */
	public List<BlogDTO> getAllBlogs() {
		List<Blog> allBlogs = blogRepository.findAll();
		List<BlogDTO> collectBlogsDtos = allBlogs.stream().map(this::getDto) // Using getDto() method
				.collect(Collectors.toList());
		return collectBlogsDtos;
	}

	// UPDATE
	/*
	 * Updates an existing blog by its ID. 
	 * Throws ResourceNotFoundException if the blog does not exist.
	 */
	public BlogDTO modifyBlog(Long id, BlogDTO blogDto) {
		// Fetch blog from database
		Blog blog = blogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));

		// Modify blog details
		blog.setTitle(blogDto.getTitle());
		blog.setContent(blogDto.getContent());

		// Save updated blog and return as DTO
		BlogDTO dto = getDto(blogRepository.save(blog));
		return dto;
	}

	// DELETE
	/*
	 * Deletes a blog by its ID. 
	 * Throws ResourceNotFoundException if the blog does not exist.
	 */
	public boolean deleteBlog(Long id) {
		Blog blog = blogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Blog not found with ID: " + id));
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

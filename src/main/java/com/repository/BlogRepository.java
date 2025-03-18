package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}

package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Blog;

@Repository // marks this interface as a Spring Data repository
public interface BlogRepository extends JpaRepository<Blog, Long> // provides CRUD operations for the Blog entity, with Long as the primary key type
{

}

package com.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
Configures a ModelMapper bean.

Creates and returns a ModelMapper instance.

Used for mapping entities to DTOs and vice versa.
*/
@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}


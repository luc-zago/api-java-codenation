package com.codenation.config;

import com.codenation.dtos.UserDTO;
import com.codenation.models.User;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @AllArgsConstructor
    private class Fullname {
        private String name;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

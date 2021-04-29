package com.codenation.config;

import com.codenation.dtos.CreateEventDTO;
import com.codenation.models.Event;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper model = new ModelMapper();

        model.createTypeMap(Event.class, CreateEventDTO.class)
                .addMapping(src -> src.getLevel().getDescription(),
                        CreateEventDTO::setLevel);

        return model;
    }
}

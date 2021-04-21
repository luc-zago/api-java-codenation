package com.codenation.config;

import com.codenation.dtos.CreateEventDTO;
import com.codenation.dtos.EventDTO;
import com.codenation.dtos.EventDTOWithLog;
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
                .<String>addMapping(src -> src.getUser().getEmail(),
                        (desc, value) -> desc.setUser(value));

        model.createTypeMap(Event.class, EventDTO.class)
                .<String>addMapping(src -> src.getLevel().getDescription(),
                        (desc, value) -> desc.setLevel(value));

        return model;
    }
}

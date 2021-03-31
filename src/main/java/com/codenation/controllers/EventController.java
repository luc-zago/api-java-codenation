package com.codenation.controllers;

import com.codenation.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    final private EventService eventService;
}

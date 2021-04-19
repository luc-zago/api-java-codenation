package com.codenation.services;

import com.codenation.models.Event;

import java.util.List;

public interface EventService extends ServiceInterface<Event> {

    public List<Event> getAll();

}

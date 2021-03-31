package com.codenation.services;

public interface ServiceInterface<T> {
    T save(T object);
}

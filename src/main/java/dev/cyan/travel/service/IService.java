package dev.cyan.travel.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    List<T> getAll();
    Optional<T> getById(String id);
    T createOrUpdate(T t);
    void delete(String id);
}

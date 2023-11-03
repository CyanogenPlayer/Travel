package dev.cyan.travel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IController<T> {
    @GetMapping
    ResponseEntity<List<T>> getAll();
    @GetMapping("/{id}")
    ResponseEntity<T> getById(@PathVariable String id);
    @PostMapping
    ResponseEntity<T> create(@RequestBody T t);
    @PutMapping("/{id}")
    ResponseEntity<T> update(@PathVariable String id, @RequestBody T t);
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id);
}

package com.davezone.billtracker.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BaseController <T, ID>{
    ResponseEntity<List<T>> getAll();
    ResponseEntity<?> getById(@PathVariable("id") ID id);
    ResponseEntity<?> create (@RequestBody T entity);
    ResponseEntity<String> delete (@PathVariable("id") ID id);
    ResponseEntity<?> update (@PathVariable("id") ID id, @RequestBody T entity);
}

package com.davezone.billtracker.base.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BaseService <T, ID>{
    ResponseEntity<List<T>> getAll();
    ResponseEntity<?> getById(ID id);
    ResponseEntity<?> create (T entity);
    ResponseEntity<String> delete (ID id);
    ResponseEntity<?> update (ID id, T entity);
    boolean existById (ID id);
}

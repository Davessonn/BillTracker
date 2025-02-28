package com.davezone.billtracker.base.service;

import org.springframework.http.ResponseEntity;


import java.util.List;

public interface BaseService <T, ID>{
    ResponseEntity<List<T>> getAll();
    ResponseEntity<?> getById(ID id);
    ResponseEntity<?> create (T entity);
    ResponseEntity<String> delete (ID id);
    ResponseEntity<?> update (ID id, T entity);
}

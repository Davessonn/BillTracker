package com.davezone.billtracker.rent.controller;

import com.davezone.billtracker.base.controller.BaseController;
import com.davezone.billtracker.rent.model.Rent;
import com.davezone.billtracker.rent.service.RentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rent")
public class RentController implements BaseController<Rent, Long> {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @Override
    public ResponseEntity<List<Rent>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> getById(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<?> create(Rent entity) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Long aLong, Rent entity) {
        return null;
    }
}

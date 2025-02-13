package com.davezone.billtracker.rent.controller;

import com.davezone.billtracker.base.controller.BaseController;
import com.davezone.billtracker.rent.model.Rent;
import com.davezone.billtracker.rent.service.RentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent")
public class RentController implements BaseController<Rent, Long> {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<List<Rent>> getAll() {
        return rentService.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return rentService.getById(id);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<?> create(@RequestBody Rent entity) {
        return rentService.create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return rentService.delete(id);
    }

    @PutMapping("/edit/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody Rent entity) {
        return rentService.update(id, entity);
    }
}

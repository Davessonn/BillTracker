package com.davezone.billtracker.heating.controller;

import com.davezone.billtracker.base.controller.BaseController;
import com.davezone.billtracker.heating.model.Heating;
import com.davezone.billtracker.heating.service.HeatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heating")
public class HeatingController implements BaseController<Heating, Long> {

    private final HeatingService heatingService;

    public HeatingController(HeatingService heatingService) {
        this.heatingService = heatingService;
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<Heating>> getAll() {
        return heatingService.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return heatingService.getById(id);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Heating newheating) {
        return heatingService.create(newheating);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return heatingService.delete(id);
    }

    @Override
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody Heating heating) {
        return heatingService.update(id, heating);
    }
}

package com.davezone.billtracker.localtax.controller;

import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.service.LocalTaxService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/localTax")
public class LocalTaxController {

    private final LocalTaxService localTaxService;

    @Autowired
    public LocalTaxController(LocalTaxService localTaxService) {
        this.localTaxService = localTaxService;
    }

    //Get method for querry all local taxes
    @GetMapping(value = "/all")
    public ResponseEntity<List<LocalTax>> getAllLocalTaxes() {
        List<LocalTax> localTaxes = localTaxService.getAllLocalTaxes();
        if (localTaxes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(localTaxes);
    }

    //Get method for search local tax by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<LocalTax> getLocalTaxById (@PathVariable("id") long id) {
        Optional<LocalTax> searchedLocalTax= localTaxService.getLocalTaxById(id);
        return ResponseEntity.of(searchedLocalTax);
    }

    //Post method for create new local tax
    @PostMapping(value = "/create")
    public ResponseEntity<LocalTax> createLocalTax (@RequestBody LocalTax newLocalTax) {
        if (newLocalTax == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalTax savedLocalTax = localTaxService.saveLocalTax(newLocalTax);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLocalTax);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

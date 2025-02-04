package com.davezone.billtracker.localtax.controller;

import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.service.LocalTaxService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<LocalTax> getLocalTaxById (@PathVariable("id") long id) {
        Optional<LocalTax> searchedLocalTax= localTaxService.getLocalTaxById(id);
        return ResponseEntity.of(searchedLocalTax);
    }

    //Post method for create new local tax
    @PostMapping( "/create")
    public ResponseEntity<?> createLocalTax(@RequestBody LocalTax newLocalTax) {
        try {
            LocalTax createdLocalTax = localTaxService.saveLocalTax(newLocalTax);
            if (createdLocalTax == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLocalTax);
            //return ResponseEntity.ok(createdLocalTax);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt az adatok mentése során!");
        }
    }

    //Delete method for delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLocalTax(@PathVariable("id") long id) {
        if (!localTaxService.existById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó");
        }
        localTaxService.deleteLocalTaxById(id);
        return ResponseEntity.ok("Sikeresen törölve: " + id);
    }
}

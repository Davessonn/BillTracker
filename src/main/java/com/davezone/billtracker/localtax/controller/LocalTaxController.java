package com.davezone.billtracker.localtax.controller;

import com.davezone.billtracker.base.controller.BaseController;
import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.service.LocalTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/localTax")
public class LocalTaxController implements BaseController<LocalTax, Long> {

    private final LocalTaxService localTaxService;

    @Autowired
    public LocalTaxController(LocalTaxService localTaxService) {
        this.localTaxService = localTaxService;
    }

    //Get method for querry all local taxes
    //TODO Pagination?? (Pageable)
    @GetMapping(value = "/all")
    @Override
    public ResponseEntity<List<LocalTax>> getAll() {
        return localTaxService.getAll();
    }

    //Get method for search local tax by id
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return localTaxService.getById(id);
    }

    //Post method for create new local tax
    //TODO @Valid in parameters for ensure the user input validation
    @PostMapping( "/create")
    @Override
    public ResponseEntity<?> create(@RequestBody LocalTax newLocalTax) {
        return localTaxService.create(newLocalTax);
    }

    //Delete method for delete by id
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        return localTaxService.delete(id);
    }

    //TODO letesztelni!
    @PutMapping("/edit/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody LocalTax editedLocalTax) {
        return localTaxService.update(id, editedLocalTax);
    }
}

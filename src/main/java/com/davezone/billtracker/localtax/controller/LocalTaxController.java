package com.davezone.billtracker.localtax.controller;

import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.service.LocalTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping(value = "/api/localTax")
public class LocalTaxController {

    private final LocalTaxService localTaxService;

    @Autowired
    public LocalTaxController(LocalTaxService localTaxService) {
        this.localTaxService = localTaxService;
    }

    @GetMapping(value = "/all")
    public List<LocalTax> getAllLocalTaxes() {
        List<LocalTax> localTaxes;
        localTaxes = localTaxService.getAllLocalTaxes();
        return localTaxes;
    }
}

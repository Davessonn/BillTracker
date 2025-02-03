package com.davezone.billtracker.localtax.service;

import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.repository.LocalTaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalTaxService {

    private final LocalTaxRepository localTaxRepository;

    @Autowired
    public LocalTaxService(LocalTaxRepository localTaxRepository) {
        this.localTaxRepository = localTaxRepository;
    }

    //Get all local taxes
    public List<LocalTax> getAllLocalTaxes() {
        return localTaxRepository.findAll();
    }

    //Get local tax by id
    public Optional<LocalTax> getLocalTaxById(long id) {
        return localTaxRepository.findById(id);
    }

    //Save new local tax
    public LocalTax saveLocalTax(LocalTax localTax) {
        return localTaxRepository.save(localTax);
    }

    //Update local tax
    public LocalTax updateLocalTax(LocalTax localTax) {
        return localTaxRepository.save(localTax);
    }

    //Delete local tax by id
    public void deleteLocalTax(long id) {
        localTaxRepository.deleteById(id);
    }


}

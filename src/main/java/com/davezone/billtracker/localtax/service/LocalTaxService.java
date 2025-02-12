package com.davezone.billtracker.localtax.service;

import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.repository.LocalTaxRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LocalTaxService {

    private final LocalTaxRepository localTaxRepository;


    @Autowired
    public LocalTaxService(LocalTaxRepository localTaxRepository) {
        this.localTaxRepository = localTaxRepository;
    }

    //Get all local taxes
    public ResponseEntity<List<LocalTax>> getAllLocalTaxes() {
        log.debug("Ez egy teszt");
        List<LocalTax> localTaxes = localTaxRepository.findAll();
        if (localTaxes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(localTaxes);
    }

    //Get local tax by id
    public ResponseEntity<?> getLocalTaxById(long id) {
        Optional<LocalTax> searchedLocalTax= localTaxRepository.findById(id);
        if (!existById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó!");
        }
        return ResponseEntity.of(searchedLocalTax);
    }

    //Save new local tax
    @Transactional
    public ResponseEntity<?> createLocalTax(LocalTax newLocalTax) {
        try {
            LocalTax createdLocalTax = localTaxRepository.save(newLocalTax);
            if (createdLocalTax == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLocalTax);
            //return ResponseEntity.ok(createdLocalTax);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt az adatok mentése során!");
        }
    }

    //Update local tax
    @Transactional
    public ResponseEntity<?> updateLocalTax(Long id, LocalTax localTax) {
        if (!existById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó");
        }
        localTaxRepository.save(localTax);
        return ResponseEntity.ok("A módosítás sikeres volt!");
    }


    //Delete local tax by id
    @Transactional
    public ResponseEntity<String> deleteLocalTaxById(long id) {
        try {
            if (!existById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó");
            }
            localTaxRepository.deleteById(id);
            return ResponseEntity.ok("Sikeresen törölve: " + id);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nem törölhető, mert más adatok hivatkoznak rá!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt a törlés során!");
        }
    }

    public boolean existById(long id) {
        return localTaxRepository.existsById(id);
    }
}

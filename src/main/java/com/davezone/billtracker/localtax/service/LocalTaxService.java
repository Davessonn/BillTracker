package com.davezone.billtracker.localtax.service;

import com.davezone.billtracker.base.service.BaseService;
import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.repository.LocalTaxRepository;
import com.davezone.billtracker.rent.model.Rent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LocalTaxService implements BaseService<LocalTax, Long> {

    private final LocalTaxRepository localTaxRepository;


    @Autowired
    public LocalTaxService(LocalTaxRepository localTaxRepository) {
        this.localTaxRepository = localTaxRepository;
    }


    @Override
    public ResponseEntity<List<LocalTax>> getAll() {
        List<LocalTax> localTaxes = localTaxRepository.findAll();
        if (localTaxes.isEmpty()) {
            log.debug("Kommunális adók táblázat üres!");
            return ResponseEntity.noContent().build();
        }
        log.debug("Kommunális adók lekérdezése sikeres volt!");
        return ResponseEntity.ok(localTaxes);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<LocalTax> searchedLocalTax= localTaxRepository.findById(id);
        if (!localTaxRepository.existsById(id)) {
            log.debug(String.format("Nem található ezzel az ID-val kommunális adó: " + id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó!");
        }
        log.debug(String.format("Kommunális adó lekérdezése ID alapján sikeres volt, " + id));
        return ResponseEntity.of(searchedLocalTax);
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(LocalTax newLocalTax) {
        try {
            LocalTax createdLocalTax = localTaxRepository.save(newLocalTax);
            if (createdLocalTax == null) {
                return ResponseEntity.noContent().build();
            }
            log.debug("Kommunális adó sikeresen létrehozva:\n" + createdLocalTax);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLocalTax);
        } catch (Exception e) {
            log.debug("Hiba a kommunális adó létrehozásánál" +
                    "\n Hiba: " + e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt az adatok mentése során!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> delete(Long id) {
        try {
            if (!localTaxRepository.existsById(id)) {
                log.debug(String.format("Nem található ezzel az ID-val kommunális adó: " + id));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó");
            }
            localTaxRepository.deleteById(id);
            log.debug("Törlés sikeres volt, ID: " + id);
            return ResponseEntity.ok("Sikeresen törölve: " + id);
        } catch (DataIntegrityViolationException e) {
            log.debug("Nem törölhető, mert más adatok hivatkoznak rá!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nem törölhető, mert más adatok hivatkoznak rá!");
        } catch (Exception e) {
            log.debug("Hiba történt a törlés során!, ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt a törlés során!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long id, LocalTax localTax) {
        Optional<LocalTax> existingLocalTax = localTaxRepository.findById(id);
        if (existingLocalTax.isEmpty()) {
            log.debug("Nem található ezzel az ID-val kommunális adó: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó!");
        }
        // Meglévő entitás frissítése
        LocalTax updatedLocalTax = existingLocalTax.get();
        updatedLocalTax.setReferenceNumber(localTax.getReferenceNumber());
        updatedLocalTax.setDate(localTax.getDate());
        updatedLocalTax.setAmount(localTax.getAmount());
        updatedLocalTax.setDueDate(localTax.getDueDate());
        updatedLocalTax.setPayed(localTax.isPayed());

        localTaxRepository.save(updatedLocalTax);
        log.debug("A módosítás sikeres volt, kommunális adó: \n" + localTax);
        return ResponseEntity.ok("A módosítás sikeres volt!");
    }
}

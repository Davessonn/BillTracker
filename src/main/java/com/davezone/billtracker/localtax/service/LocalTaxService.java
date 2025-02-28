package com.davezone.billtracker.localtax.service;

import com.davezone.billtracker.base.service.BaseService;
import com.davezone.billtracker.localtax.model.LocalTax;
import com.davezone.billtracker.localtax.repository.LocalTaxRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
            log.info("Kommunális adók táblázat üres!");
            return ResponseEntity.noContent().build();
        }
        log.info("Kommunális adók lekérdezése sikeres volt!");
        return ResponseEntity.ok(localTaxes);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<LocalTax> searchedLocalTax= localTaxRepository.findById(id);
        if (!localTaxRepository.existsById(id)) {
            log.error(String.format("Nem található ezzel az ID-val kommunális adó: " + id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó!");
        }
        log.info(String.format("Kommunális adó lekérdezése ID alapján sikeres volt, " + id));
        return ResponseEntity.of(searchedLocalTax);
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(LocalTax newLocalTax) {
        try {
            if (newLocalTax == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A kommunális adó adat nem lehet üres!");
            }
            LocalTax createdLocalTax = localTaxRepository.save(newLocalTax);
            log.info("kommunális adó sikeresen létrehozva: {}", createdLocalTax);
            return ResponseEntity.status(HttpStatus.CREATED).body("kommunális adó sikeresen létrehozva ");

        } catch (DataIntegrityViolationException e) {
            log.error("Adat integritási hiba a lakbér létrehozásakor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A kommunális adó nem hozható létre, mert ütközik meglévő adatokkal!");
        } catch (Exception e) {
            log.error("Hiba a kommunális adó létrehozásánál: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Hiba történt az adatok mentése során!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> delete(Long id) {
        try {
            if (!localTaxRepository.existsById(id)) {
                log.error(String.format("Nem található ezzel az ID-val kommunális adó: " + id));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó");
            }
            localTaxRepository.deleteById(id);
            log.info("Törlés sikeres volt, ID: " + id);
            return ResponseEntity.ok("Sikeresen törölve: " + id);
        } catch (DataIntegrityViolationException e) {
            log.error("Nem törölhető, mert más adatok hivatkoznak rá!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nem törölhető, mert más adatok hivatkoznak rá!");
        } catch (Exception e) {
            log.error("Hiba történt a törlés során!, ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt a törlés során!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long id, LocalTax localTax) {
        try
        {
            if (localTax == null) {
                log.error("A bemeneti kommunális adó objektum null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A kommunális adó adat nem lehet üres!");
            }

            Optional<LocalTax> existingLocalTax = localTaxRepository.findById(id);

            if (existingLocalTax.isEmpty()) {
                log.error("Nem található ezzel az ID-val kommunális adó: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen kommunális adó!");
            }
            // Meglévő entitás frissítése
            LocalTax updatedLocalTax = existingLocalTax.get();
            BeanUtils.copyProperties(localTax, updatedLocalTax, "id", "createdAt");
            localTaxRepository.save(updatedLocalTax);
            log.info("A módosítás sikeres volt, kommunális adó: {}", localTax);
            return ResponseEntity.ok("A módosítás sikeres volt!");
        } catch (DataIntegrityViolationException e) {
            log.error("Nem módosítható, mert más adatok hivatkoznak rá! hiba: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nem szerkeszthető, mert más adatok hivatkoznak rá!");
        } catch (Exception e) {
            log.error("Hiba történt a szerkesztés során!, ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt a szerkesztés során!");
        }
    }
}

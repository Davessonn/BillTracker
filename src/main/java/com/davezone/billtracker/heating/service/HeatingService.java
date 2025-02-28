package com.davezone.billtracker.heating.service;

import com.davezone.billtracker.base.service.BaseService;
import com.davezone.billtracker.heating.repository.HeatingRepository;
import com.davezone.billtracker.heating.model.Heating;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HeatingService implements BaseService<Heating, Long> {

    private final HeatingRepository heatingRepository;

    public HeatingService(HeatingRepository heatingRepository) {
        this.heatingRepository = heatingRepository;
    }

    @Override
    public ResponseEntity<List<Heating>> getAll() {
        List<Heating> heatingList = heatingRepository.findAll();
        if (heatingList.isEmpty()) {
            log.info("A fűtés táblázat üres!");
            return ResponseEntity.noContent().build();
        }
        log.info("A fűtés táblázat lekérdezése sikeresen megtörtént!");
        return ResponseEntity.ok(heatingList);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Heating> searchedHeating = heatingRepository.findById(id);
        if (!heatingRepository.existsById(id)) {
            log.error("Nem található ilyen ID-val fűtés számla, ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen ID-val fűtés számla");
        }
        log.info("Fűtés számla lekérdezése ID alapján sikeres volt, " + id);
        return ResponseEntity.status(HttpStatus.OK).body(searchedHeating);
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(Heating newHeating) {
        try {
            if (newHeating == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A fűtés számla adat nem lehet üres!");
            }
            Heating createdHeating = heatingRepository.save(newHeating);
            log.info("kommunális adó sikeresen létrehozva: {}", createdHeating);
            return ResponseEntity.status(HttpStatus.CREATED).body("fűtés számla sikeresen létrehozva ");

        } catch (DataIntegrityViolationException e) {
            log.error("Adat integritási hiba a fűtés számla létrehozásakor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A kommunális adó nem hozható létre, mert ütközik meglévő adatokkal!");
        } catch (Exception e) {
            log.error("Hiba a fűtés számla létrehozásánál: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Hiba történt az adatok mentése során!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> delete(Long id) {
        try {
            if (!heatingRepository.existsById(id)) {
                log.error("Nem található ezzel az ID-val fűtés számla: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen fűtés számla!");
            }
            heatingRepository.deleteById(id);
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
    public ResponseEntity<?> update(Long id, Heating heating) {
        try
        {
            if (heating == null) {
                log.error("A bemeneti fűtés számla objektum null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A fűtés számla adat nem lehet üres!");
            }

            Optional<Heating> existingHeating = heatingRepository.findById(id);

            if (existingHeating.isEmpty()) {
                log.error("Nem található ezzel az ID-val fűtés számla: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen fűtés számla!");
            }
            // Meglévő entitás frissítése
            Heating updatedHeating = existingHeating.get();
            BeanUtils.copyProperties(heating, updatedHeating, "id", "createdAt");
            heatingRepository.save(updatedHeating);
            log.info("A módosítás sikeres volt, fűtés számla: {}", heating);
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

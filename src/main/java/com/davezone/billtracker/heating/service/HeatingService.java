package com.davezone.billtracker.heating.service;

import com.davezone.billtracker.base.service.BaseService;
import com.davezone.billtracker.heating.model.Heating;
import com.davezone.billtracker.heating.repository.HeatingRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
            log.debug("A fűtés táblázat üres!");
            return ResponseEntity.noContent().build();
        }
        log.debug("A fűtés táblázat lekérdezése sikeresen megtörtént!");
        return ResponseEntity.ok(heatingList);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Heating> searchedHeating = heatingRepository.findById(id);
        if (!heatingRepository.existsById(id)) {
            log.debug("Nem található ilyen ID-val fűtés számla, ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen ID-val fűtés számla");
        }
        log.debug("Lakbér lekérdezése ID alapján sikeres volt, " + id);
        return ResponseEntity.status(HttpStatus.OK).body(searchedHeating);
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(Heating newHeating) {
        try {
            if (newHeating == null) {
                return ResponseEntity.noContent().build();
            }
            heatingRepository.save(newHeating);
            log.debug("Fűtés számla sikeresen létrehozva:\n" + newHeating);
            return ResponseEntity.status(HttpStatus.CREATED).body(newHeating);
        } catch (Exception e) {
            log.debug("Hiba a fűtés számla létrehozásánál" +
                    "\n Hiba: " + e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt az adatok mentése során!");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> delete(Long id) {
        try {
            if (!heatingRepository.existsById(id)) {
                log.debug("Nem található ezzel az ID-val fűtés számla: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen fűtés számla!");
            }
            heatingRepository.deleteById(id);
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
    //TODO Create update method
    public ResponseEntity<?> update(Long id, Heating updatableHeating) {
        return null;
    }
}

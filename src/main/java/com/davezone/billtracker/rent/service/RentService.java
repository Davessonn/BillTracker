package com.davezone.billtracker.rent.service;

import com.davezone.billtracker.base.service.BaseService;
import com.davezone.billtracker.rent.model.Rent;
import com.davezone.billtracker.rent.repository.RentRepository;
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

@Slf4j
@Service
public class RentService implements BaseService<Rent, Long> {

    private final RentRepository rentRepository;

    @Autowired
    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }


    @Override
    public ResponseEntity<List<Rent>> getAll() {
        List<Rent> rentList = rentRepository.findAll();
        if (rentList.isEmpty()) {
            log.info("A lakbér táblázat üres!");
            return ResponseEntity.ok(rentList);
        }
        log.info("A lakbér táblázat lekérdezése sikeresen megtörtént! Méret: {}", rentList.size());
        return ResponseEntity.ok(rentList);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Rent> searchedRent = rentRepository.findById(id);
        if (searchedRent.isEmpty()) {
            log.error("Nem található ezzel az ID-val lakbér: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen lakbér!");
        }
        log.info("Lakbér lekérdezése ID alapján sikeres volt, {}", id);
        return ResponseEntity.of(searchedRent);
    }

    @Transactional
    @Override
    public ResponseEntity<String> create(Rent newRent) {
        try {
            if (newRent == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A lakbér adat nem lehet üres!");
            }
            Rent createdRent = rentRepository.save(newRent);
            log.info("Lakbér sikeresen létrehozva: {}", createdRent);
            return ResponseEntity.status(HttpStatus.CREATED).body("Lakbér sikeresen létrehozva ");

        } catch (DataIntegrityViolationException e) {
            log.error("Adat integritási hiba a lakbér létrehozásakor: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("A lakbér nem hozható létre, mert ütközik meglévő adatokkal!");
        } catch (Exception e) {
            log.error("Hiba a lakbér létrehozásánál: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Hiba történt az adatok mentése során!");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> delete(Long id) {
        try {
            if (!rentRepository.existsById(id)) {
                log.error("Nem található ezzel az ID-val lakbér: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen lakbér!");
            }
            rentRepository.deleteById(id);
            log.info("Törlés sikeres volt, ID: {}", id);
            return ResponseEntity.ok("Sikeresen törölve: " + id);

        } catch (DataIntegrityViolationException e) {
            log.error("Nem törölhető, mert más adatok hivatkoznak rá!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Nem törölhető, mert más adatok hivatkoznak rá!");
        } catch (Exception e) {
            log.error("Hiba történt a törlés során!, ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt a törlés során!");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> update(Long id, Rent rent) {
        try
        {
            if (rent == null) {
                log.error("A bemeneti lakbér objektum null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A lakbér adat nem lehet üres!");
            }

            Optional<Rent> existingRent = rentRepository.findById(id);

            if (existingRent.isEmpty()) {
                log.error("Nem található ezzel az ID-val lakbér: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen lakbér!");
            }
            // Meglévő entitás frissítése
            Rent updatedRent = existingRent.get();
            BeanUtils.copyProperties(rent, updatedRent, "id", "createdAt");
            rentRepository.save(updatedRent);
            log.info("A módosítás sikeres volt, lakbér: {}", rent);
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

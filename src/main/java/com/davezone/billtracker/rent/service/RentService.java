package com.davezone.billtracker.rent.service;

import com.davezone.billtracker.base.service.BaseService;
import com.davezone.billtracker.rent.model.Rent;
import com.davezone.billtracker.rent.repository.RentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
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
    private final ClientHttpRequestFactorySettings clientHttpRequestFactorySettings;

    @Autowired
    public RentService(RentRepository rentRepository, ClientHttpRequestFactorySettings clientHttpRequestFactorySettings) {
        this.rentRepository = rentRepository;
        this.clientHttpRequestFactorySettings = clientHttpRequestFactorySettings;
    }


    @Override
    public ResponseEntity<List<Rent>> getAll() {
        List<Rent> rentList = rentRepository.findAll();
        if (rentList.isEmpty()) {
            log.debug("A lakbér táblázat üres!");
            return ResponseEntity.noContent().build();
        }
        log.debug("A lakbér táblázat lekérdezése sikeresen megtörtént!");
        return ResponseEntity.ok(rentList);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Rent> searchedRent = rentRepository.findById(id);
        if (!rentRepository.existsById(id)) {
            log.debug("Nem található ezzel az ID-val lakbér: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen lakbér!");
        }
        log.debug("Lakbér lekérdezése ID alapján sikeres volt, " + id);
        return ResponseEntity.of(searchedRent);
    }

    @Transactional
    @Override
    public ResponseEntity<?> create(Rent newRent) {
        try {
            if (newRent == null) {
                return ResponseEntity.noContent().build();
            }
            Rent createdRent = rentRepository.save(newRent);
            log.debug("Lakbér sikeresen létrehozva:\n" + createdRent);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRent);

        } catch (Exception e) {
            log.debug("Hiba a lakbér létrehozásánál" +
                    "\n Hiba: " + e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hiba történt az adatok mentése során!");
        }
    }

    @Transactional
    @Override
    public ResponseEntity<String> delete(Long id) {
        try {
            if (!rentRepository.existsById(id)) {
                log.debug("Nem található ezzel az ID-val lakbér: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen lakbér!");
            }
            rentRepository.deleteById(id);
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

    @Transactional
    @Override
    public ResponseEntity<?> update(Long id, Rent rent) {
        if (!rentRepository.existsById(id)) {
            log.debug("Nem található ezzel az ID-val lakbér: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nem található ilyen lakbér!");
        }
        rentRepository.save(rent);
        log.debug("A módosítás sikeres volt, lakbér: \n" + rent);
        return ResponseEntity.ok("A módosítás sikeres volt!");
    }
}

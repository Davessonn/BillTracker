package com.davezone.billtracker.rent.service;

import com.davezone.billtracker.rent.model.Rent;
import com.davezone.billtracker.rent.repository.RentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentService {

    public final RentRepository rentRepository;

    @Autowired
    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    //findAll
    public List<Rent> getAllRents () {
        return rentRepository.findAll();
    }

    //findById
    public Optional<Rent> getRentById (Long id) {
        return rentRepository.findById(id);
    }

    //delete
    @Transactional
    public void deleteRentById (Long id) {
        if (rentRepository.existsById(id)) {
            rentRepository.deleteById(id);
        }
    }

    //create



    //update
    @Transactional
    public Rent updateRent (Rent updatedRent) {
        return rentRepository.save(updatedRent);
    }


}

package com.davezone.billtracker.rent.service;

import com.davezone.billtracker.rent.model.Rent;
import com.davezone.billtracker.rent.repository.RentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Rent getRentById (Long id) {
        return rentRepository.getById(id);
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


}

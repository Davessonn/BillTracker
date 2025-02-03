package com.davezone.billtracker.localtax.repository;

import com.davezone.billtracker.localtax.model.LocalTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalTaxRepository extends JpaRepository<LocalTax, Long> {


}

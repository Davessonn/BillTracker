package com.davezone.billtracker.heating.repository;

import com.davezone.billtracker.heating.model.Heating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeatingRepository extends JpaRepository<Heating, Long> {
}

package com.davezone.billtracker.rent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "rent_bills")
public class Rent {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;


}

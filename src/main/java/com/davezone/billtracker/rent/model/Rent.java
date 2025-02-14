package com.davezone.billtracker.rent.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "rent_bills")
public class Rent {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;
    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "amount", nullable = false)
    private int amount;
    @Column(name = "payed", nullable = false)
    private boolean isPayed;

    public void setAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Rent id=" + id
                + ", invoice number=" + invoiceNumber
                + ", invoice date=" + invoiceDate
                + ", due date=" + dueDate
                + ", start date=" + startDate
                + ", end date=" + endDate
                + ", amount=" + amount
                + ", payed=" + isPayed + "}";
    }



}

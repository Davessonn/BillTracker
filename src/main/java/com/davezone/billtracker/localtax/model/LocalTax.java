package com.davezone.billtracker.localtax.model;


import com.davezone.billtracker.category.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "local_tax")
public class LocalTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "reference_number", nullable = false)
    private int referenceNumber;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "amount", nullable = false)
    private int amount;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Column(name = "payed", nullable = false)
    private boolean payed;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    public void setAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "LocalTax id=" + id
                + ", referenceNumber=" + referenceNumber
                + ", date=" + date
                + ", amount=" + amount
                + ", dueDate=" + dueDate
                + ", payed=" + payed
                + ", category name=" + category.getName() + "}";
    }

}

package com.davezone.billtracker.rent.model;

import com.davezone.billtracker.category.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rent_bills")
public class Rent {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "reference_number", nullable = false)
    private String referenceNumber;
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

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public void setAmount(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Rent id=" + id
                + ", reference number=" + referenceNumber
                + ", invoice date=" + invoiceDate
                + ", due date=" + dueDate
                + ", start date=" + startDate
                + ", end date=" + endDate
                + ", amount=" + amount
                + ", payed=" + isPayed
                + ", category name=" + category.getName() + "}";
    }



}

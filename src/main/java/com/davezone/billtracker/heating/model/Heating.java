package com.davezone.billtracker.heating.model;

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
@Table(name = "heating_bills")
public class Heating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "reference_number", nullable = false)
    private String referenceNumber;
    @Column (name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;
    @Column (name = "due_date", nullable = false)
    private LocalDate dueDate;
    @Column (name = "heating_base_fee", nullable = false)
    private int heatingBaseFee;
    @Column (name = "heating_start_date", nullable = false)
    private LocalDate heatingStartDate;
    @Column (name = "heating_end_date", nullable = false)
    private LocalDate heatingEndDate;
    @Column (name = "water_heating_fee", nullable = false)
    private int waterHeatingFee;
    @Column (name = "previous_meter_reading", nullable = false)
    private int previousMeterReading;
    @Column (name = "current_meter_reading", nullable = false)
    private int currentMeterReading;
    @Column (name = "water_heating_start_date", nullable = false)
    private LocalDate waterHeatingStartDate;
    @Column (name = "water_heating_end_date", nullable = false)
    private LocalDate waterHeatingEndDate;
    @Column (name = "total_cost", nullable = false)
    private int totalCost;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @Override
    public String toString() {
        return "Heating{" +
                "id=" + id +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", heatingBaseFee=" + heatingBaseFee +
                ", heatingStartDate=" + heatingStartDate +
                ", heatingEndDate=" + heatingEndDate +
                ", waterHeatingFee=" + waterHeatingFee +
                ", previousMeterReading=" + previousMeterReading +
                ", currentMeterReading=" + currentMeterReading +
                ", waterHeatingStartDate=" + waterHeatingStartDate +
                ", waterHeatingEndDate=" + waterHeatingEndDate +
                ", totalCost=" + totalCost +
                ", category=" + category.getName() +
                '}';
    }
}

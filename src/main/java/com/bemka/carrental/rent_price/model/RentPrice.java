package com.bemka.carrental.rent_price.model;

import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.common.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.UUID;

@Table(name = "rent_prices")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RentPrice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @PrePersist
    private void onPrePersist() {
        if (externalId == null) {
            externalId = UUID.randomUUID().toString();
        }
    }
}

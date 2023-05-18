package com.bemka.carrental.car.model;

import com.bemka.carrental.common.Auditable;
import com.bemka.carrental.rent_price.model.RentPrice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Table(name = "cars")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;
    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private CarBrand brand;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<RentPrice> rentPrices;

    @PrePersist
    private void onPrePersist() {
        if (externalId == null) {
            externalId = UUID.randomUUID().toString();
        }
        if (isActive == null) {
            isActive = true;
        }
    }
}

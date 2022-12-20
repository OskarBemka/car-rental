package com.bemka.carrental.car.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "cars")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "external_id", nullable = false, unique = true)
    private String externalId;
    @Column(name = "brand", nullable = false)
    private CarBrand brand;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}

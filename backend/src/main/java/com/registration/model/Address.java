package com.registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address", schema = "public")
public class Address {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long id;

    @Column(name = "zip_code", nullable = false, length = 100)
    @NotEmpty(message = "zip code")
    private String zipCode;

    @Column(name = "public_place", nullable = false, length = 100)
    @NotEmpty(message = "public place")
    private String publicPlace;

    @Column(name = "neighborhood", nullable = false, length = 100)
    @NotEmpty(message = "neighborhood")
    private String neighborhood;

    @Column(name = "city", nullable = false, length = 100)
    @NotEmpty(message = "city")
    private String city;

    @Column(name = "fu", nullable = false, length = 100)
    @NotEmpty(message = "fu")
    private String fu;

    @Column(name = "complement")
    private String complement;
}

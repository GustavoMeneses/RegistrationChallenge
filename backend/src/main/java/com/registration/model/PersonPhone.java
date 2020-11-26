package com.registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_person_phone", schema = "public")
public class PersonPhone {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_phone_seq")
    @SequenceGenerator(name = "person_phone_seq", sequenceName = "person_phone_seq", allocationSize = 1)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_person")
    private Person person;

    @ManyToOne()
    @JoinColumn(name = "id_phone")
    private Phone phone;
}

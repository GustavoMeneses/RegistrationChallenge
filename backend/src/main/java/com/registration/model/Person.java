package com.registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_person", schema = "public")
public class Person {


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NotEmpty(message = "person name")
    private String name;

    @Column(name = "social_security_number", nullable = false, length = 100)
    @NotEmpty(message = "social security number")
    private String socialSecurityNumber;

    @ManyToOne()
    @JoinColumn(name = "id_address")
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_person_email",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_email")
    )
    Set<Email> emails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_person_phone",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_phone")
    )
    Set<Phone> phones;
}

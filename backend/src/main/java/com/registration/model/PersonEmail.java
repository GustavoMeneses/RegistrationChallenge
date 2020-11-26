package com.registration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_person_email", schema = "public")
public class PersonEmail {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_email_seq")
    @SequenceGenerator(name = "person_email_seq", sequenceName = "person_email_seq", allocationSize = 1)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_person")
    private Person person;

    @ManyToOne()
    @JoinColumn(name = "id_email")
    private Email email;
}

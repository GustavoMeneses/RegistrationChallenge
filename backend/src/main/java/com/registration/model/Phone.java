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
@Table(name = "tb_phone", schema = "public")
public class Phone {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    @SequenceGenerator(name = "phone_seq", sequenceName = "phone_seq", allocationSize = 1)
    private Long id;

    @Column(name = "phone", nullable = false, length = 100)
    @NotEmpty(message = "phone number")
    private String phone;

    @Column(name = "phone_type", nullable = false, length = 100)
    private Long phoneType;
}

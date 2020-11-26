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
@Table(name = "tb_email", schema = "public")
public class Email {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")
    @SequenceGenerator(name = "email_seq", sequenceName = "email_seq", allocationSize = 1)
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    @NotEmpty(message = "e-mail")
    private String email;
}

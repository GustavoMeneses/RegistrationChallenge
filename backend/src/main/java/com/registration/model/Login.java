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
@Table(name = "tb_user", schema = "public")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", nullable = false, length = 100)
    @NotEmpty(message = "login do usuário")
    private String login;

    @Column(name = "password", nullable = false, length = 7)
    @NotEmpty(message = "Senha do usuário")
    private String password;

    @ManyToOne()
    @JoinColumn(name = "id_profile")
    private Profile profile;
}

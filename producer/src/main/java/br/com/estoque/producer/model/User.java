package br.com.estoque.producer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user")
public class User {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotNull
    private String name;
    @Email
    private String email;
    @NotNull
    private String cpf;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String profile;
    @NotNull
    private int council;
    @NotNull
    private String federativeUnit;

    public User() {
    }

    public User(Long userId, String name, String email, String cpf, String phoneNumber, String profile, int council, String federativeUnit) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.profile = profile;
        this.council = council;
        this.federativeUnit = federativeUnit;
    }
}
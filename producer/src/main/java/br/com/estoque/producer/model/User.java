package br.com.estoque.producer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user")
public class User {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @Email
    private String email;
    @NotNull
    private String cpf;
    @NotNull
    private String telephone;
    @NotNull
    private String profile;
    @NotNull
    private int numConselho;
    @NotNull
    private String uf;

    public User() {
    }

    public User(Long id, String name, String email, String cpf, String telephone, String profile, int numConselho, String uf) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.telephone = telephone;
        this.profile = profile;
        this.numConselho = numConselho;
        this.uf = uf;
    }
}
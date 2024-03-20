package br.com.estoque.producer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UserTeste {

    private String userId;
    private String name;
    private String email;
    private String cpf;
    private String phoneNumber;
    private String profile;
    private int council;
    private String federativeUnit;

    public UserTeste() {
    }

    public UserTeste(String userId, String name, String email, String cpf, String phoneNumber, String profile, int council, String federativeUnit) {
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
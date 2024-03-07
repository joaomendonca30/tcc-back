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
@Table(name = "paciente")
public class Paciente {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    @NotNull
    private String name;
    @Email
    private String email;
    @NotNull
    private String cpf;
    @NotNull
    private String phoneNumber;
    private Date dateOfBirth;
    private String healthInsurance;
    private String insurancePlanNumber;
    private String specialNotes;

    public Paciente() {
    }

    public Paciente(Long patientId, String name, String email, String cpf, String phoneNumber, Date dateOfBirth, String healthInsurance, String insurancePlanNumber, String specialNotes) {
        this.patientId = patientId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.healthInsurance = healthInsurance;
        this.insurancePlanNumber = insurancePlanNumber;
        this.specialNotes = specialNotes;
    }
}
package br.com.estoque.consumer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "agenda")
public class Agenda {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sheduleId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Paciente patientId;

    @NotNull
    private String start;
    @NotNull
    private String end;
    @NotNull
    private String title;
    @NotNull
    private String scheduleType;



    public Agenda() {
    }

    public Agenda(Long sheduleId, User userId, Paciente patientId, String start, String end, String title, String scheduleType) {
        this.sheduleId = sheduleId;
        this.userId = userId;
        this.patientId = patientId;
        this.start = start;
        this.end = end;
        this.title = title;
        this.scheduleType = scheduleType;
    }

}
package br.com.estoque.producer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


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
    private Paciente pacienteId;
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

    public Agenda(Long sheduleId, User userId, Paciente pacienteId, String start, String end, String title, String scheduleType) {
        this.sheduleId = sheduleId;
        this.userId = userId;
        this.start = start;
        this.end = end;
        this.title = title;
        this.scheduleType = scheduleType;
        this.pacienteId = pacienteId;
    }
}
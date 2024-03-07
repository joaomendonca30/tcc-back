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
@Table(name = "agenda")
public class Agenda {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sheduleId;


    @ManyToOne // Muitas agendas podem pertencer a um usuário
    @JoinColumn(name = "user_id") // Nome da coluna na tabela agenda que referencia o usuário
    private User user; // Referência para o usuário
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

    public Agenda(Long sheduleId, User user, String start, String end, String title, String scheduleType) {
        this.sheduleId = sheduleId;
        this.user = user;
        this.start = start;
        this.end = end;
        this.title = title;
        this.scheduleType = scheduleType;
    }
}
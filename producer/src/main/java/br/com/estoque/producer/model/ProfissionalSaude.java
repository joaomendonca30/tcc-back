package br.com.estoque.producer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProfissionalSaude {

    private Long sheduleId;

    private String start;
    private String end;

    private String title;

    private String scheduleType;

    public ProfissionalSaude() {
    }

    public ProfissionalSaude(Long sheduleId,String start, String end, String title, String scheduleType) {
        this.sheduleId = sheduleId;
        this.start = start;
        this.end = end;
        this.title = title;
        this.scheduleType = scheduleType;
    }
}
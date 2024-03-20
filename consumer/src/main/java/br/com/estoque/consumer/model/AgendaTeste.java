package br.com.estoque.consumer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class AgendaTeste {

    private Long userId;

    private Long patientId;

    private String start;
    private String end;
    private String title;
    private String scheduleType;



    public AgendaTeste() {
    }

    public AgendaTeste( Long userId, Long patientId, String start, String end, String title, String scheduleType) {
        this.userId = userId;
        this.patientId = patientId;
        this.start = start;
        this.end = end;
        this.title = title;
        this.scheduleType = scheduleType;
    }

}
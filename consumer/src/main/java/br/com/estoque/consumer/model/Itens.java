package br.com.estoque.consumer.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "estoque")
public class Itens {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int quantity;

    private String producer;

    private String type;

    private LocalDate startDate;

    private LocalDate endDate;

    public Itens() {
    }

    public Itens(Long id, String name, int quantity, String producer, String type, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.producer = producer;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
package br.com.estoque.producer.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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

    private Date startDate;

    private Date endDate;

    public Itens() {
    }

    public Itens(Long id, String name, int quantity, String producer, String type, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.producer = producer;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
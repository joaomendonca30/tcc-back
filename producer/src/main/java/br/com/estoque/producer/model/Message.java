package br.com.estoque.producer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private Long id;
    private String method;
    private String message;
}

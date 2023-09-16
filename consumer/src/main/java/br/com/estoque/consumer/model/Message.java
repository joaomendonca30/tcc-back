package br.com.estoque.consumer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private Long id;
    private String method;
    private String message;
}

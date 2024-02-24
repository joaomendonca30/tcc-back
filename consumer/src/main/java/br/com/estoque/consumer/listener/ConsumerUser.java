package br.com.estoque.consumer.listener;

import br.com.estoque.consumer.controller.ItensController;
import br.com.estoque.consumer.controller.UserController;
import br.com.estoque.consumer.model.Itens;
import br.com.estoque.consumer.model.Message;
import br.com.estoque.consumer.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class ConsumerUser {
    @Autowired
    private UserController controller;
    private final Logger logger = LoggerFactory.getLogger(ConsumerUser.class);

    @KafkaListener(topics = "${topic.user-uclinic}", groupId = "group_id")
    public void consume(String kafkaMessage) throws IOException, ParseException {
        Message mensagemRecebida = parseJsonToMensagem(kafkaMessage);
        Long id = mensagemRecebida.getId();
        String method = mensagemRecebida.getMethod();
        String message = mensagemRecebida.getMessage();

        if(method.equals("POST")){
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());
            User user = jsonMapper.readValue(message, User.class);

            logger.info("User consumido: {}", message);

            controller.insert(user);
        }
        else if(method.equals("PUT")){
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(message, User.class);
            logger.info("User consumido: {}", message);
            controller.update(id, user);
        }
        else {
            logger.info("User consumido: {}", id);
            controller.delete(id);
        }

    }
    private Message parseJsonToMensagem(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Message.class);
    }

}
package br.com.estoque.consumer.listener;

import br.com.estoque.consumer.controller.PacienteController;
import br.com.estoque.consumer.model.Message;
import br.com.estoque.consumer.model.Paciente;
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
public class ConsumerPaciente {
    @Autowired
    private PacienteController controller;
    private final Logger logger = LoggerFactory.getLogger(ConsumerPaciente.class);

    @KafkaListener(topics = "${topic.paciente-uclinic}", groupId = "group_id")
    public void consume(String kafkaMessage) throws IOException, ParseException {
        Message mensagemRecebida = parseJsonToMensagem(kafkaMessage);
        Long id = mensagemRecebida.getId();
        String method = mensagemRecebida.getMethod();
        String message = mensagemRecebida.getMessage();

        if(method.equals("POST")){
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());
            Paciente paciente = jsonMapper.readValue(message, Paciente.class);

            logger.info("Paciente consumido: {}", message);

            controller.insert(paciente);
        }
        else if(method.equals("PUT")){
            ObjectMapper objectMapper = new ObjectMapper();
            Paciente paciente = objectMapper.readValue(message, Paciente.class);
            logger.info("Paciente consumido: {}", message);
            controller.update(id, paciente);
        }
        else {
            logger.info("Paciente consumido: {}", id);
            controller.delete(id);
        }

    }
    private Message parseJsonToMensagem(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Message.class);
    }

}
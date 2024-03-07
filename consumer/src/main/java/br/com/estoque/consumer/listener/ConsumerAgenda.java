package br.com.estoque.consumer.listener;

import br.com.estoque.consumer.controller.AgendaController;
import br.com.estoque.consumer.model.Agenda;
import br.com.estoque.consumer.model.Message;
import br.com.estoque.consumer.model.User;
import br.com.estoque.consumer.repository.UserRepository;
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
public class ConsumerAgenda {
    @Autowired
    private AgendaController controller;

    @Autowired
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(ConsumerAgenda.class);

    @KafkaListener(topics = "${topic.agenda-uclinic}", groupId = "group_id")
    public void consume(String kafkaMessage) throws IOException, ParseException {
        Message mensagemRecebida = parseJsonToMensagem(kafkaMessage);
        Long id = mensagemRecebida.getId();
        String method = mensagemRecebida.getMethod();
        String message = mensagemRecebida.getMessage();

        if(method.equals("POST")){
            JsonMapper jsonMapper = new JsonMapper();
            jsonMapper.registerModule(new JavaTimeModule());
            Agenda agenda = jsonMapper.readValue(message, Agenda.class);
            logger.info("Agenda consumida: {}", message);

            String cpfDoUsuario = "12345678-01";
            Long userId = obterCPFDoUsuario(cpfDoUsuario);

            User user2 = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + userId));
            agenda.setUser(user2); // Define o usuário na agenda

            controller.insert(agenda);
        }
        else if(method.equals("PUT")){
            ObjectMapper objectMapper = new ObjectMapper();
            Agenda agenda = objectMapper.readValue(message, Agenda.class);
            logger.info("Agenda consumida: {}", message);
            controller.update(id, agenda);
        }
        else {
            logger.info("Agenda consumida: {}", id);
            controller.delete(id);
        }

    }
    public Long obterCPFDoUsuario(String cpf) {
        User user = userRepository.findByCpf(cpf);
        if (user != null) {
            return user.getUserId();
        } else {
            throw new RuntimeException("Usuário não encontrado com o cpf: " + cpf);
        }
    }
    private Message parseJsonToMensagem(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Message.class);
    }

}
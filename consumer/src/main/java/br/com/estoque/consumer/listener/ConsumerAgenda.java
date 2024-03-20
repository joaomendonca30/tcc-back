package br.com.estoque.consumer.listener;

import br.com.estoque.consumer.controller.AgendaController;
import br.com.estoque.consumer.model.*;
import br.com.estoque.consumer.repository.AgendaRepository;
import br.com.estoque.consumer.repository.PacienteRepository;
import br.com.estoque.consumer.repository.UserRepository;
import br.com.estoque.consumer.service.PacienteService;
import br.com.estoque.consumer.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class ConsumerAgenda {
    @Autowired
    private AgendaController controller;

    private final AgendaRepository agendaRepository;
    private final UserRepository userRepository;
    private final PacienteRepository patientRepository;
    private final UserService userService;

    private final PacienteService pacienteService;

    private final Logger logger = LoggerFactory.getLogger(ConsumerAgenda.class);

    public ConsumerAgenda(AgendaRepository agendaRepository, UserRepository userRepository,
                          PacienteRepository patientRepository, UserService userService, PacienteService pacienteService) {
        this.userService = userService;
        this.pacienteService = pacienteService;
        this.agendaRepository = agendaRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
    }

    @KafkaListener(topics = "${topic.agenda-uclinic}", groupId = "group_id")
    public void consume(String kafkaMessage) throws IOException, ParseException {
        Message mensagemRecebida = parseJsonToMensagem(kafkaMessage);
        Long id = mensagemRecebida.getId();
        String method = mensagemRecebida.getMethod();
        String message = mensagemRecebida.getMessage();

        if (method.equals("POST")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(message);

            JsonNode userId = jsonNode.get("userId");
            JsonNode patientId = jsonNode.get("patientId");
            JsonNode start = jsonNode.get("start");
            JsonNode end = jsonNode.get("end");
            JsonNode title = jsonNode.get("title");
            JsonNode scheduleType = jsonNode.get("scheduleType");

            long user = Long.parseLong(userId.asText());
            long patient = Long.parseLong(patientId.asText());
            String titleSchedule = title.asText();
            String startDate = start.asText();
            String endDate = end.asText();
            String type = scheduleType.asText();

            ((ObjectNode) jsonNode).put("userId", user);
            ((ObjectNode) jsonNode).put("patientId", patient);

            Agenda novaAgenda = new Agenda();
            User usuario = userService.encontrarPorId(user);
            Paciente paciente = pacienteService.encontrarPorId(patient);
            novaAgenda.setUserId(usuario);
            novaAgenda.setPatientId(paciente);
            novaAgenda.setTitle(titleSchedule);
            novaAgenda.setEnd(endDate);
            novaAgenda.setStart(startDate);
            novaAgenda.setScheduleType(type);

            logger.info("Agenda consumido - Insert: {}", novaAgenda);
            agendaRepository.save(novaAgenda);

        } else if (method.equals("PUT")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Agenda agenda = objectMapper.readValue(message, Agenda.class);
            logger.info("Agenda consumida: {}", message);
            controller.update(id, agenda);
        } else {
            logger.info("Agenda consumida: {}", id);
            controller.delete(id, id);
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
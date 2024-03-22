package br.com.estoque.producer.resource;

import br.com.estoque.producer.model.*;
import br.com.estoque.producer.repository.AgendaRepository;
import br.com.estoque.producer.repository.UserRepository;
import br.com.estoque.producer.service.AgendaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaResource {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    AgendaService mensagemService;

    @CrossOrigin(origins = "http://localhost:3000") // Permita solicitações apenas a partir de http://localhost:3000
    @PostMapping("/inserir")
    public ResponseEntity<String> enviarMensagem(@RequestBody String mensagem, HttpServletRequest request) throws JsonProcessingException {

        String method = request.getMethod();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(mensagem);

        JsonNode userId = jsonNode.get("userId");
        JsonNode patientId = jsonNode.get("patientId");
        long user = Long.parseLong(userId.asText());
        long patient = Long.parseLong(patientId.asText());

        ((ObjectNode) jsonNode).put("userId", user);
        ((ObjectNode) jsonNode).put("patientId", patient);

        String mensagemJson =jsonNode.toPrettyString();
        Message mensagemPOST = new Message();

        mensagemPOST.setMethod(method);
        mensagemPOST.setMessage(mensagemJson);
        String mensagemFinal = convertMensagemToJson(mensagemPOST);
        mensagemService.sendMessage(mensagemFinal);
        return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/editar/{scheduleId}")
    public ResponseEntity<String> atualizar(@PathVariable Long scheduleId, @RequestBody String itens,
                                            HttpServletRequest request) throws JsonProcessingException {

        String method = request.getMethod();

        Message mensagem = new Message();
        if (!agendaRepository.existsById(scheduleId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagem.setId(scheduleId);
            mensagem.setMethod(method);
            mensagem.setMessage(itens);

            String mensagemJson = convertMensagemToJson(mensagem);
            mensagemService.sendMessage(mensagemJson);
            return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + itens);
        }
    }

    private String convertMensagemToJson(Message mensagem) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(mensagem);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/deletar/{scheduleId}")
    public ResponseEntity<String> delete(@PathVariable Long scheduleId, HttpServletRequest request) throws JsonProcessingException {
        String method = request.getMethod();

        Message mensagemDelete = new Message();
        if (!agendaRepository.existsById(scheduleId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagemDelete.setMethod(method);
            mensagemDelete.setId(scheduleId);
            String mensagemJson = convertMensagemToJson(mensagemDelete);
            mensagemService.sendMessage(mensagemJson);
            return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userId}")
    public ResponseEntity<?> buscar(@PathVariable Long userId) {

        List<Agenda> agenda = agendaRepository.findSchedule(userId);

        if (agenda.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma consulta encontrada na base de dados.");
        } else {
            return ResponseEntity.ok(agenda);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        List<Agenda> agenda = agendaRepository.findAll();

        if (agenda.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum agendamento foi encontrado na base de dados.");
        } else {
            return ResponseEntity.ok(agenda);
        }
    }
}
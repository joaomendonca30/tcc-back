package br.com.estoque.producer.resource;

import br.com.estoque.producer.model.Message;
import br.com.estoque.producer.model.Paciente;
import br.com.estoque.producer.model.User;
import br.com.estoque.producer.repository.PacienteRepository;
import br.com.estoque.producer.service.PacienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteResource {

    @Autowired
    private PacienteRepository pacienteRepository;

    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    PacienteService mensagemService;

    @CrossOrigin(origins = "http://localhost:3000") // Permita solicitações apenas a partir de http://localhost:3000
    @PostMapping("/inserir")
    public ResponseEntity<String> enviarMensagem(@RequestBody String mensagem, HttpServletRequest request) throws JsonProcessingException {
        String method = request.getMethod();

        Message mensagemPOST = new Message();

        mensagemPOST.setMethod(method);
        mensagemPOST.setMessage(mensagem);

        String mensagemJson = convertMensagemToJson(mensagemPOST);
        mensagemService.sendMessage(mensagemJson);
        return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagem);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/editar/{patientId}")
    public ResponseEntity<String> atualizar(@PathVariable Long patientId, @RequestBody String itens,
                                            HttpServletRequest request) throws JsonProcessingException {

        String method = request.getMethod();
        Message mensagem = new Message();
        if (!pacienteRepository.existsById(patientId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagem.setId(patientId);
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
    @DeleteMapping("/deletar/{patientId}")
    public ResponseEntity<String> delete(@PathVariable Long patientId,HttpServletRequest request) throws JsonProcessingException {
        String method = request.getMethod();

        Message mensagemDelete = new Message();

        if (!pacienteRepository.existsById(patientId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagemDelete.setMethod(method);
            mensagemDelete.setId(patientId);
            String mensagemJson = convertMensagemToJson(mensagemDelete);
            mensagemService.sendMessage(mensagemJson);
            return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{patientId}")
    public ResponseEntity<?> buscar(@PathVariable Long patientId) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findById(patientId);

        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            return ResponseEntity.ok(paciente);
        } else {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<?> buscarTodos() {

        List<Paciente> pacientes = pacienteRepository.findAll();

        if (pacientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum paciente encontrado na base de dados.");
        } else {
            return ResponseEntity.ok(pacientes);
        }
    }

}
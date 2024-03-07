package br.com.estoque.producer.resource;

import br.com.estoque.producer.model.Message;
import br.com.estoque.producer.model.User;
import br.com.estoque.producer.repository.UserRepository;
import br.com.estoque.producer.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserResource.class);
    @Autowired
    UserService mensagemService;

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
    @PutMapping("/editar/{userId}")
    public ResponseEntity<String> atualizar(@PathVariable Long userId, @RequestBody String body,
                                            HttpServletRequest request) throws JsonProcessingException {

        String method = request.getMethod();

        Message mensagem = new Message();
        if (!userRepository.existsById(userId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagem.setId(userId);
            mensagem.setMethod(method);
            mensagem.setMessage(body);

            String mensagemJson = convertMensagemToJson(mensagem);
            mensagemService.sendMessage(mensagemJson);
            return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + body);
        }
    }
    private String convertMensagemToJson(Message mensagem) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(mensagem);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/deletar/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId,HttpServletRequest request) throws JsonProcessingException {
        String method = request.getMethod();

        Message mensagemDelete = new Message();

        if (!userRepository.existsById(userId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagemDelete.setMethod(method);
            mensagemDelete.setId(userId);
            String mensagemJson = convertMensagemToJson(mensagemDelete);
            mensagemService.sendMessage(mensagemJson);
            return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userId}")
    public ResponseEntity<?> buscar(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user);
        } else {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<?> buscarTodos() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum usuário encontrado na base de dados.");
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/profissionaisDaSaude")
    public ResponseEntity<?> buscarPrrofissionaisSaude() {
        List<User> profissionais = userRepository.findByProfile("Profissional da Saúde");

        if (profissionais.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum profissional da saúde encontrado na base de dados.");
        } else {
            return ResponseEntity.ok(profissionais);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/recepcionistas")
    public ResponseEntity<?> buscarRecepcionais() {

        List<User> profissionais = userRepository.findByProfile("Recepcionista");

        if (profissionais.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum(a) recepcionista encontrado(a) na base de dados.");
        } else {
            return ResponseEntity.ok(profissionais);
        }
    }
}
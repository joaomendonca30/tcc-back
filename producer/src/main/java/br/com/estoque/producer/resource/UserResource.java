package br.com.estoque.producer.resource;

import br.com.estoque.producer.model.Message;
import br.com.estoque.producer.model.User;
import br.com.estoque.producer.repository.UserRepository;
import br.com.estoque.producer.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UserResource {

    @Autowired
    private UserRepository repo;

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
        return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/editar/{userId}")
    public ResponseEntity<String> atualizar(@PathVariable Long userId, @RequestBody String itens,
                                            HttpServletRequest request) throws JsonProcessingException {

        String method = request.getMethod();

        Message mensagem = new Message();

        mensagem.setId(userId);
        mensagem.setMethod(method);
        mensagem.setMessage(itens);

        String mensagemJson = convertMensagemToJson(mensagem);
        mensagemService.sendMessage(mensagemJson);
        return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + itens);
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

        mensagemDelete.setMethod(method);
        mensagemDelete.setId(userId);

        String mensagemJson = convertMensagemToJson(mensagemDelete);
        mensagemService.sendMessage(mensagemJson);

        return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{userId}")
    public ResponseEntity<User> buscar(@PathVariable Long userId) {
        return repo.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<User> buscarTodos() {
        return repo.findAll();
    }
}
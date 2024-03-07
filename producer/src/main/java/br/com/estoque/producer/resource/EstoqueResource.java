package br.com.estoque.producer.resource;

import br.com.estoque.producer.model.Itens;
import br.com.estoque.producer.model.Message;

import br.com.estoque.producer.repository.ItensRepository;
import br.com.estoque.producer.service.EstoqueService;
import br.com.estoque.producer.service.UserService;
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
@RequestMapping("/estoque")
public class EstoqueResource {

    @Autowired
    private ItensRepository estoqueRepository;
    private final Logger logger = LoggerFactory.getLogger(EstoqueResource.class);

    @Autowired
    EstoqueService mensagemService;

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
    @PutMapping("/editar/{productId}")
    public ResponseEntity<String> atualizar(@PathVariable Long productId, @RequestBody String itens,
                                            HttpServletRequest request) throws JsonProcessingException {

        String method = request.getMethod();

        Message mensagem = new Message();

        if (!estoqueRepository.existsById(productId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagem.setId(productId);
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
    @DeleteMapping("/deletar/{productId}")
    public ResponseEntity<String> delete(@PathVariable Long productId,HttpServletRequest request) throws JsonProcessingException {
        String method = request.getMethod();

        Message mensagemDelete = new Message();
        if (!estoqueRepository.existsById(productId)) {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        } else {
            mensagemDelete.setMethod(method);
            mensagemDelete.setId(productId);
            String mensagemJson = convertMensagemToJson(mensagemDelete);
            mensagemService.sendMessage(mensagemJson);
            return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{productId}")
    public ResponseEntity<?> buscar(@PathVariable Long productId) {
        Optional<Itens> itensOptional = estoqueRepository.findById(productId);
        if (itensOptional.isPresent()) {
            Itens itens = itensOptional.get();
            return ResponseEntity.ok(itens);
        } else {
            logger.info("Não foi encontrado esse ID na Base de Dados.");
            return ResponseEntity.badRequest().body("Não foi encontrado esse ID na Base de Dados.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        List<Itens> itens = estoqueRepository.findAll();

        if (itens.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum item foi encontrado na base de dados.");
        } else {
            return ResponseEntity.ok(itens);
        }
    }
}
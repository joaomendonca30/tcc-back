package br.com.estoque.producer.resource;

import br.com.estoque.producer.model.Itens;
import br.com.estoque.producer.model.Message;
import br.com.estoque.producer.repository.ItensRepository;
import br.com.estoque.producer.service.MensagemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/estoque")
public class MensagemResource {

    @Autowired
    private ItensRepository repo;

    @Autowired
    MensagemService mensagemService;

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

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody String itens,
                                            HttpServletRequest request) throws JsonProcessingException {

        String method = request.getMethod();

        Message mensagem = new Message();

        mensagem.setId(id);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id,HttpServletRequest request) throws JsonProcessingException {
        String method = request.getMethod();

        Message mensagemDelete = new Message();

        mensagemDelete.setMethod(method);
        mensagemDelete.setId(id);

        String mensagemJson = convertMensagemToJson(mensagemDelete);
        mensagemService.sendMessage(mensagemJson);

        return ResponseEntity.ok().body("Mensagem enviada com sucesso: " + mensagemJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itens> buscar(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Itens> buscarTodos() {
        return repo.findAll();
    }
}
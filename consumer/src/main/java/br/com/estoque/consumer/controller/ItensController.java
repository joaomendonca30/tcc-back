package br.com.estoque.consumer.controller;

import br.com.estoque.consumer.model.Itens;
import br.com.estoque.consumer.repository.ItensRepository;
import br.com.estoque.consumer.service.ItensService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/itens")
public class ItensController {

    @Autowired
    private ItensRepository repo;
    private final Logger logger = LoggerFactory.getLogger(ItensController.class);
    private final ItensService registroItensService;

    public void update(Long id, Itens itens) {
        if(!repo.existsById(id)){
            logger.info("Não foi encontrado esse ID na Base de Dados.");
        }
        itens.setId(id);
        Itens itensAtualizado = registroItensService.salvar(itens);
        logger.info("Item atualizado com sucesso.");
    }

    public void insert(Itens itens) {
        repo.save(itens);
        logger.info("Item adicionado com sucesso.");
    }

    public void delete(Long id) {
        if(!repo.existsById(id)){
            logger.info("Não foi encontrado esse ID na Base de Dados.");
        }
        else{
            registroItensService.excluir(id);
            logger.info("Item deletado com sucesso.");
        }
    }
}
package br.com.estoque.consumer.controller;

import br.com.estoque.consumer.model.Agenda;
import br.com.estoque.consumer.repository.AgendaRepository;
import br.com.estoque.consumer.service.AgendaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaRepository repo;
    private final Logger logger = LoggerFactory.getLogger(AgendaController.class);
    private final AgendaService agendaService;

    public void update(Long id, Agenda agenda) {
        agenda.setSheduleId(id);
        Agenda agendaAtualizado = agendaService.salvar(agenda);
        logger.info("Agenda atualizado com sucesso.");
    }

    public void insert(Agenda agenda) {
        repo.save(agenda);
        logger.info("Agenda adicionado com sucesso.");
    }

    public void delete(Long id) {
        agendaService.excluir(id);
        logger.info("Agenda deletada com sucesso.");
    }
}

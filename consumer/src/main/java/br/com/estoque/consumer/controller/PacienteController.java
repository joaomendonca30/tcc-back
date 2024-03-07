package br.com.estoque.consumer.controller;

import br.com.estoque.consumer.model.Paciente;
import br.com.estoque.consumer.repository.PacienteRepository;
import br.com.estoque.consumer.service.PacienteService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository repo;
    private final Logger logger = LoggerFactory.getLogger(PacienteController.class);
    private final PacienteService pacienteService;

    public void update(Long id, Paciente paciente) {
        paciente.setPatientId(id);
        Paciente pacienteAtualizado = pacienteService.salvar(paciente);
        logger.info("Paciente atualizado com sucesso.");
    }

    public void insert(Paciente paciente) {
        repo.save(paciente);
        logger.info("Paciente adicionado com sucesso.");
    }

    public void delete(Long id) {
        pacienteService.excluir(id);
        logger.info("Paciente deletado com sucesso.");
    }
}

package br.com.estoque.consumer.service;

import br.com.estoque.consumer.model.Paciente;
import br.com.estoque.consumer.model.User;
import br.com.estoque.consumer.repository.PacienteRepository;
import br.com.estoque.consumer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    //Realiza o rollback se algo der errado
    @Transactional
    public Paciente salvar(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void excluir(Long id){
        pacienteRepository.deleteById(id);
    }

}

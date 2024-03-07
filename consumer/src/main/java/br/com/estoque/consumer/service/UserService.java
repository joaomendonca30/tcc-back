package br.com.estoque.consumer.service;

import br.com.estoque.consumer.model.Agenda;
import br.com.estoque.consumer.model.User;
import br.com.estoque.consumer.repository.AgendaRepository;
import br.com.estoque.consumer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    //Realiza o rollback se algo der errado
    @Transactional
    public User salvar(User user){
        return userRepository.save(user);
    }

    @Transactional
    public void excluir(Long id){
        // Exclua todos os registros na tabela agenda relacionados a este usuário
        agendaRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

}

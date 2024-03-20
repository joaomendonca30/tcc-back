package br.com.estoque.consumer.service;

import br.com.estoque.consumer.model.Agenda;
import br.com.estoque.consumer.model.User;
import br.com.estoque.consumer.repository.AgendaRepository;
import br.com.estoque.consumer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UserRepository userRepository;



    //Realiza o rollback se algo der errado
    @Transactional
    public Agenda salvar(Agenda agenda){
        return agendaRepository.save(agenda);
    }
    @Transactional
    public void excluir(Long scheduleId, Long userId){
        // Encontre o agendamento dentro do usuário
//        Agenda agendaToRemove = user.getAgendas().stream()
//                .filter(agenda -> agenda.getSheduleId().equals(scheduleId))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado para o usuário com ID " + userId));

        // Remova o agendamento da lista de agendamentos do usuário
       // user.getAgendas().remove(agendaToRemove);

        // Atualize o usuário no banco de dados para refletir as alterações
        //userRepository.save(user);
    }


}

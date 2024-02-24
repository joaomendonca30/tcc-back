package br.com.estoque.consumer.service;

import br.com.estoque.consumer.model.Itens;
import br.com.estoque.consumer.repository.ItensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ItensService {
    @Autowired
    private ItensRepository itensRepository;

    //Realiza o rollback se algo der errado
    @Transactional
    public Itens salvar(Itens itens){
        return itensRepository.save(itens);
    }

    @Transactional
    public void excluir(Long id){
        itensRepository.deleteById(id);
    }

}

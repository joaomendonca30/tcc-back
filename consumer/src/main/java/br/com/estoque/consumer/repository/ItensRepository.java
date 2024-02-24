package br.com.estoque.consumer.repository;

import br.com.estoque.consumer.model.Itens;
import br.com.estoque.consumer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensRepository extends JpaRepository<Itens, Long> {

}

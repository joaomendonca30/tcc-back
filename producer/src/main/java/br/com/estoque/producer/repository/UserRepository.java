package br.com.estoque.producer.repository;

import br.com.estoque.producer.model.Itens;
import br.com.estoque.producer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

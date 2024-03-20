package br.com.estoque.consumer.repository;

import br.com.estoque.consumer.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}


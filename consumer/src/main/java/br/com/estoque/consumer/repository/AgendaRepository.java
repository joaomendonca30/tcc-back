package br.com.estoque.consumer.repository;

import br.com.estoque.consumer.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Agenda a WHERE a.user.userId = :userId")
    void deleteByUserId(Long userId);
}


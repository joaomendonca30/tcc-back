package br.com.estoque.producer.repository;

import br.com.estoque.producer.model.Agenda;
import br.com.estoque.producer.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Query("SELECT a FROM Agenda a JOIN a.userId u WHERE u.userId = :userId AND u.profile = 'Profissional da Saúde'")
    List<Agenda> findSchedule(@Param("userId") Long userId);

    @Query("SELECT new br.com.estoque.producer.model.ProfissionalSaude(a.sheduleId, a.start, a.end, a.title, a.scheduleType) FROM Agenda a JOIN a.userId u WHERE u.userId = :userId AND u.profile = 'Profissional da Saúde'")
    List<ProfissionalSaude> findByUserAndUser_Profile(@Param("userId") Long userId);
}

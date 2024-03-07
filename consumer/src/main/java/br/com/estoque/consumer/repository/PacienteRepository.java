package br.com.estoque.consumer.repository;

import br.com.estoque.consumer.model.Paciente;
import br.com.estoque.consumer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

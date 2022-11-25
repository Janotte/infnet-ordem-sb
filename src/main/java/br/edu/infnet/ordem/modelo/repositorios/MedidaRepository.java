package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Medida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedidaRepository extends JpaRepository<Medida, Long> {
    List<Medida> findByNomeContaining(String nome);
}

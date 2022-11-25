package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByNomeContaining(String nome);
}

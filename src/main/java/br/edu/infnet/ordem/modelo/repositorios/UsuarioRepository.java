package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByNomeContaining(String nome);
}

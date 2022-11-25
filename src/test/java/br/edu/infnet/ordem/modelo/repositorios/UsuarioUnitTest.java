package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UsuarioRepository repository;

    Usuario usuario1 = null;
    Usuario usuario2 = null;

    @BeforeEach
    void init() {
        usuario1 = new Usuario("Augusto Borguetti", "aborguetti@teste.com.br", "123", LocalDate.now());
        usuario2 = new Usuario("Pedro Borgues", "pborgues@teste.com.br", "123", LocalDate.now());
    }

    @Test
    public void nao_deve_encontrar_usuarios_se_o_repositorio_estiver_vazio() {
        Iterable usuarios = repository.findAll();

        assertThat(usuarios).isEmpty();
    }

    @Test
    public void deve_armazenar_um_usuario() {
        Usuario usuarioSalvo = repository.save(usuario1);

        assertThat(usuarioSalvo).isNotNull();
    }

    @Test
    public void deve_encontrar_todos_usuarios() {
        repository.save(usuario1);

        List<Usuario> usuarios = repository.findAll();

        assertThat(usuarios).isNotEmpty();
    }

    @Test
    public void deve_encontrar_usuario_por_id() {
        entityManager.persist(usuario1);

        Optional<Usuario> usuarioPorId = repository.findById(usuario1.getId());

        assertThat(usuarioPorId.get()).isEqualTo(usuario1);
    }

    @Test
    public void deve_encontrar_usuario_por_email() {
        entityManager.persist(usuario1);

        Optional<Usuario> usuarioPorEmail = repository.findByEmail(usuario1.getEmail());

        assertThat(usuarioPorEmail.get()).isEqualTo(usuario1);
    }

    @Test
    public void deve_encontrar_usuario_por_nome_containing_string() {
        entityManager.persist(usuario1);

        entityManager.persist(usuario2);

        List<Usuario> usuarios = repository.findByNomeContaining("Borgue");

        assertThat(usuarios).hasSize(2).contains(usuario1, usuario2);
    }

    @Test
    public void deve_atualizar_usuario_por_id() {
        entityManager.persist(usuario1);
        entityManager.persist(usuario2);
        String novoNome = "Novo nome";
        String novoEmail = "Novo email";
        String novaSenha = "Nova senha";
        Optional<Usuario> usuario = repository.findById(usuario2.getId());
        usuario.get().setNome(novoNome);
        usuario.get().setEmail(novoEmail);
        usuario.get().setSenha(novaSenha);

        Usuario usuarioSalvo = repository.save(usuario.get());

        assertThat(usuarioSalvo.getId()).isEqualTo(usuario2.getId());
        assertThat(usuarioSalvo.getNome()).isEqualTo(novoNome);
        assertThat(usuarioSalvo.getEmail()).isEqualTo(novoEmail);
        assertThat(usuarioSalvo.getSenha()).isEqualTo(novaSenha);
    }

    @Test
    public void deve_deletar_usuario_por_id() {
        entityManager.persist(usuario1);
        entityManager.persist(usuario2);

        repository.deleteById(usuario2.getId());
        List<Usuario> usuarios = repository.findAll();

        assertThat(usuarios).hasSize(1).contains(usuario1);
    }

    @Test
    public void deve_deletar_todos_os_usuarios() {
        entityManager.persist(usuario1);
        entityManager.persist(usuario2);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}

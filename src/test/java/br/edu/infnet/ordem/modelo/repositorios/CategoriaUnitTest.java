package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoriaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CategoriaRepository repository;

    Categoria categoria1 = null;
    Categoria categoria2 = null;
    Categoria categoria3 = null;

    @BeforeEach
    void init() {
        categoria1 = new Categoria("Armazenamento");
        categoria2 = new Categoria("Serviço");
        categoria3 = new Categoria("Licenciamento");
    }

    @Test
    public void nao_deve_encontrar_categorias_se_o_repositorio_estiver_vazio() {
        Iterable<Categoria> categorias = repository.findAll();

        assertThat(categorias).isEmpty();
    }

    @Test
    public void deve_armazenar_uma_categoria() {
        Categoria categoriaSalva = repository.save(categoria1);

        assertThat(categoriaSalva).isNotNull();
    }

    @Test
    public void deve_encontrar_todas_categorias() {
        repository.save(categoria1);
        repository.save(categoria2);
        repository.save(categoria3);

        List<Categoria> categorias = repository.findAll();

        assertThat(categorias).hasSize(3).contains(categoria1, categoria2, categoria3);
    }

    @Test
    public void deve_encontrar_categoria_por_id() {
        entityManager.persist(categoria1);

        Optional<Categoria> categoriaPorId = repository.findById(categoria1.getId());

        assertThat(categoriaPorId.get()).isEqualTo(categoria1);
    }

    @Test
    public void deve_encontrar_categoria_por_nome_contendo_string() {
        entityManager.persist(categoria1);
        entityManager.persist(categoria2);
        entityManager.persist(categoria3);

        List<Categoria> categorias = repository.findByNomeContaining("mento");

        assertThat(categorias).hasSize(2).contains(categoria1, categoria3);
    }

    @Test
    public void deve_atualizar_categoria_por_id() {
        entityManager.persist(categoria1);
        entityManager.persist(categoria2);
        String novoNome = "Novo nome";
        Optional<Categoria> categoria = repository.findById(categoria2.getId());
        categoria.get().setNome(novoNome);

        Categoria categoriaSalva = repository.save(categoria.get());

        assertThat(categoriaSalva.getId()).isEqualTo(categoria2.getId());
        assertThat(categoriaSalva.getNome()).isEqualTo(novoNome);
    }

    @Test
    public void deve_deletar_categoria_por_id() {
        entityManager.persist(categoria1);
        entityManager.persist(categoria2);
        entityManager.persist(categoria3);

        repository.deleteById(categoria2.getId());
        List<Categoria> categorias = repository.findAll();

        assertThat(categorias).hasSize(2).contains(categoria1, categoria3);
    }

    @Test
    public void deve_deletar_todos_as_categorias() {
        entityManager.persist(categoria1);
        entityManager.persist(categoria2);
        entityManager.persist(categoria3);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}

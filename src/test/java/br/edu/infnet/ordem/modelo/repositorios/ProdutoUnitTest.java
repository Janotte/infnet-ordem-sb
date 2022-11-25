package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Categoria;
import br.edu.infnet.ordem.modelo.entidades.Medida;
import br.edu.infnet.ordem.modelo.entidades.Produto;
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
public class ProdutoUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProdutoRepository repository;

    Medida medida1 = null;
    Medida medida2 = null;
    Categoria categoria1 = null;
    Categoria categoria2 = null;
    Categoria categoria3 = null;
    Produto produto1 = null;
    Produto produto2 = null;
    Produto produto3 = null;

    @BeforeEach
    void init() {
        medida1 = new Medida("Unidade", "UND");
        medida2 = new Medida("Hora técnica", "HT");
        categoria1 = new Categoria("Armazenamento");
        categoria2 = new Categoria("Serviço");
        categoria3 = new Categoria("Licenciamento");
        produto1 = new Produto("SSD NVME 480Gb WD", 399.00, medida1, categoria1);
        produto2 = new Produto("Suporte técnico em informática", 120.00, medida2, categoria2);
        produto3 = new Produto("Microsoft 365 Personal", 159.00, medida1, categoria3);
    }

    @Test
    public void nao_deve_encontrar_produtos_se_o_repositorio_estiver_vazio() {
        entityManager.persist(medida1);
        Iterable<Produto> produtos = repository.findAll();

        assertThat(produtos).isEmpty();
    }

    @Test
    public void deve_armazenar_um_produto() {
        Produto produtoSalvo = repository.save(produto1);

        assertThat(produtoSalvo).isNotNull();
    }

    @Test
    public void deve_encontrar_todos_produtos() {
        repository.save(produto1);
        repository.save(produto2);
        repository.save(produto3);

        List<Produto> produtos = repository.findAll();

        assertThat(produtos).hasSize(3).contains(produto1, produto2, produto3);
    }

    @Test
    public void deve_encontrar_produto_por_id() {
        entityManager.persist(produto1);

        Optional<Produto> produtoPorId = repository.findById(produto1.getId());

        assertThat(produtoPorId.get()).isEqualTo(produto1);
    }

    @Test
    public void deve_encontrar_produto_por_nome_contendo_string() {
        entityManager.persist(produto1);
        entityManager.persist(produto2);
        entityManager.persist(produto3);
        List<Produto> produtos = repository.findByNomeContaining("SSD");

        assertThat(produtos).hasSize(1).contains(produto1);
    }

    @Test
    public void deve_atualizar_produto_por_id() {
        entityManager.persist(produto3);
        String novoNome = "Microsoft 365 Personal 1 ano";
        Double novoValor = 169.00;
        Optional<Produto> produto = repository.findById(produto3.getId());
        produto.get().setNome(novoNome);
        produto.get().setValor(novoValor);

        Produto produtoSalvo = repository.save(produto.get());

        assertThat(produtoSalvo.getId()).isEqualTo(produto3.getId());
        assertThat(produtoSalvo.getNome()).isEqualTo(novoNome);
        assertThat(produtoSalvo.getValor()).isEqualTo(novoValor);
    }

    @Test
    public void deve_deletar_produto_por_id() {
        entityManager.persist(produto1);
        entityManager.persist(produto2);
        entityManager.persist(produto3);

        repository.deleteById(produto2.getId());
        List<Produto> produtos = repository.findAll();

        assertThat(produtos).hasSize(2).contains(produto1, produto3);
    }

    @Test
    public void deve_deletar_todos_os_produtos() {
        entityManager.persist(produto1);
        entityManager.persist(produto2);
        entityManager.persist(produto3);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}

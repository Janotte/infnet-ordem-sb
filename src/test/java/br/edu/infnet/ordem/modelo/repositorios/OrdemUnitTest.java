package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrdemUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    OrdemRepository repository;

    Usuario atendente = null;
    Pessoa cliente1 = null;
    Pessoa cliente2 = null;
    Produto produto1 = null;
    Produto produto2 = null;
    Produto produto3 = null;
    List<ItemProduto> produtos = null;
    Ordem ordem1 = null;
    Ordem ordem2 = null;


    @BeforeEach
    void init() {
        atendente = new Usuario("Sandro Janotte", "sandro@teste.com.br", "123", LocalDate.now());
        entityManager.persist(atendente);
        cliente1 = new Pessoa(TipoPessoa.FISICA, "Maria Batista Silva", new Endereco("88.", "Rua Itajubá", "334", "Apto 07", "Bom Retiro", "Joinville", "SC"), "+55 31 88888888");
        entityManager.persist(cliente1);
        cliente2 = new Pessoa(TipoPessoa.JURIDICA, "João das Neves", new Endereco("89.227-025", "Avenida Prefeito Wittich Freitag", "1180", "Sala 03", "Iririú", "Joinville", "SC"), "+55 47 99999999");
        entityManager.persist(cliente2);
        produto1 = new Produto("SSD NVME 480Gb WD", 399.00, new Medida("Unidade", "UN"), new Categoria("Armazenamento"));
        entityManager.persist(produto1);
        produto2 = new Produto("Suporte técnico em informática", 120.00, new Medida("Hora", "HR"), new Categoria("Serviço"));
        entityManager.persist(produto2);
        produto3 = new Produto("Fonte de Alimentação Lenovo", 1460.00, new Medida("Peça", "PÇ"), new Categoria("Fonte de Servidor"));
        entityManager.persist(produto3);
        produtos = new ArrayList<>();
        ordem1 = new Ordem(
                LocalDateTime.now(),
                LocalAtendimento.REMOTO,
                atendente,
                cliente1,
                "Notebook Dell Inspiron",
                "Muito Lento para carregar o Windows",
                "HD apresentando erros de leitura e gravação",
                "Troca do HD por SSD backup e reinstalação",
                produtos);
        ordem1.getProdutos().add(new ItemProduto(produto1, 1D, produto1.getValor()));
        ordem1.getProdutos().add(new ItemProduto(produto2, 1D, produto2.getValor()));

        ordem2 = new Ordem(
                LocalDateTime.now(),
                LocalAtendimento.EXTERNO,
                atendente,
                cliente2,
                "Servidor Lenovo XS550",
                "Não carrega o Windows",
                null,
                null,
                null);
    }

    @Test
    public void nao_deve_encontrar_ordens_se_o_repositorio_estiver_vazio() {
        Iterable<Ordem> ordens = repository.findAll();

        assertThat(ordens).isEmpty();
    }

    @Test
    public void deve_armazenar_uma_ordem() {
        Ordem ordemSalva = repository.save(ordem1);

        assertThat(ordemSalva).isNotNull();
    }

    @Test
    public void deve_encontrar_todas_ordens() {
        repository.save(ordem1);
        repository.save(ordem2);

        List<Ordem> ordens = repository.findAll();

        assertThat(ordens).hasSize(2).contains(ordem1, ordem2);
    }

    @Test
    public void deve_encontrar_ordem_por_id() {
        entityManager.persist(ordem1);

        Optional<Ordem> ordemPorId = repository.findById(ordem1.getId());

        assertThat(ordemPorId.get()).isEqualTo(ordem1);
    }

    @Test
    public void deve_atualizar_ordem_por_id() {
        entityManager.persist(ordem1);
        entityManager.persist(ordem2);
        Optional<Ordem> ordem = repository.findById(ordem2.getId());
        ordem.get().setDiagnostico("Equipamento não liga");
        ordem.get().setSolucao("Troca da fonte de alimentação.");
        List<ItemProduto> produtos = new ArrayList<>();
        ordem.get().setProdutos(produtos);
        ordem.get().getProdutos().add(new ItemProduto(produto3, 1D, produto3.getValor()));
        ordem.get().getProdutos().add(new ItemProduto(produto2, 2D, produto2.getValor()));

        Ordem ordemSalva = repository.save(ordem.get());

        assertThat(ordemSalva.getId()).isEqualTo(ordem2.getId());
        assertThat(ordemSalva.getProdutos().size()).isEqualTo(2);
    }

    @Test
    public void deve_deletar_ordem_por_id() {
        entityManager.persist(ordem1);
        entityManager.persist(ordem2);

        repository.deleteById(ordem2.getId());
        List<Ordem> ordens = repository.findAll();

        assertThat(ordens).hasSize(1).contains(ordem1);
    }

    @Test
    public void deve_deletar_todos_as_ordems() {
        entityManager.persist(ordem1);
        entityManager.persist(ordem2);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}

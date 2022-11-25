package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Endereco;
import br.edu.infnet.ordem.modelo.entidades.Pessoa;
import br.edu.infnet.ordem.modelo.entidades.TipoPessoa;
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
public class PessoaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PessoaRepository repository;

    Pessoa pessoa1 = null;
    Pessoa pessoa2 = null;
    Pessoa pessoa3 = null;

    @BeforeEach
    void init() {
        pessoa1 = new Pessoa(TipoPessoa.JURIDICA, "João das Neves", new Endereco("89.227-025", "Avenida Prefeito Wittich Freitag", "1180", "Sala 03", "Iririú", "Joinville", "SC"), "+55 47 99999999");
        pessoa2 = new Pessoa(TipoPessoa.FISICA, "Maria Batista Silva", new Endereco("88.", "Rua Itajubá", "334", "Apto 07", "Bom Retiro", "Joinville", "SC"), "+55 31 88888888");
        pessoa3 = new Pessoa(TipoPessoa.FISICA, "Jorge Batistella", new Endereco("88385-000", "Rua José Cirício de Souza,", "742", "Fundos", "Praia de Armação do Itapocorói", "Penha", "SC"), "+55 21 77777777");
    }

    @Test
    public void nao_deve_encontrar_pessoas_se_o_repositorio_estiver_vazio() {
        Iterable<Pessoa> pessoas = repository.findAll();

        assertThat(pessoas).isEmpty();
    }

    @Test
    public void deve_armazenar_um_pessoa() {
        Pessoa pessoaSalvo = repository.save(pessoa1);

        assertThat(pessoaSalvo).isNotNull();
    }

    @Test
    public void deve_encontrar_todos_pessoas() {
        repository.save(pessoa1);
        repository.save(pessoa2);
        repository.save(pessoa3);

        List<Pessoa> pessoas = repository.findAll();

        assertThat(pessoas).hasSize(3).contains(pessoa1, pessoa2, pessoa3);
    }

    @Test
    public void deve_encontrar_pessoa_por_id() {
        entityManager.persist(pessoa1);

        Optional<Pessoa> pessoaPorId = repository.findById(pessoa1.getId());

        assertThat(pessoaPorId.get()).isEqualTo(pessoa1);
    }

    @Test
    public void deve_encontrar_pessoa_por_nome_contendo_string() {
        entityManager.persist(pessoa1);
        entityManager.persist(pessoa2);
        entityManager.persist(pessoa3);

        List<Pessoa> pessoas = repository.findByNomeContaining("Batist");

        assertThat(pessoas).hasSize(2).contains(pessoa2, pessoa3);
    }

    @Test
    public void deve_atualizar_pessoa_por_id() {
        entityManager.persist(pessoa1);
        entityManager.persist(pessoa2);
        String novoNome = "Novo nome";
        String novoWhatsapp = "Novo whatsapp";
        Optional<Pessoa> pessoa = repository.findById(pessoa2.getId());
        pessoa.get().setNome(novoNome);
        pessoa.get().setWhatsapp(novoWhatsapp);

        Pessoa pessoaSalvo = repository.save(pessoa.get());

        assertThat(pessoaSalvo.getId()).isEqualTo(pessoa2.getId());
        assertThat(pessoaSalvo.getNome()).isEqualTo(novoNome);
        assertThat(pessoaSalvo.getWhatsapp()).isEqualTo(novoWhatsapp);
    }

    @Test
    public void deve_deletar_pessoa_por_id() {
        entityManager.persist(pessoa1);
        entityManager.persist(pessoa2);
        entityManager.persist(pessoa3);

        repository.deleteById(pessoa2.getId());
        List<Pessoa> pessoas = repository.findAll();

        assertThat(pessoas).hasSize(2).contains(pessoa1, pessoa3);
    }

    @Test
    public void deve_deletar_todos_os_pessoas() {
        entityManager.persist(pessoa1);
        entityManager.persist(pessoa2);
        entityManager.persist(pessoa3);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}

package br.edu.infnet.ordem.modelo.repositorios;

import br.edu.infnet.ordem.modelo.entidades.Medida;
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
public class MedidaUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MedidaRepository repository;

    Medida medida1 = null;
    Medida medida2 = null;
    Medida medida3 = null;

    @BeforeEach
    void init() {
        medida1 = new Medida("Unidade", "UN");
        medida2 = new Medida("Hora", "HR");
        medida3 = new Medida("Metro", "MT");
    }

    @Test
    public void nao_deve_encontrar_medidas_se_o_repositorio_estiver_vazio() {
        Iterable<Medida> medidas = repository.findAll();

        assertThat(medidas).isEmpty();
    }

    @Test
    public void deve_armazenar_uma_medida() {
        Medida medidaSalva = repository.save(medida1);

        assertThat(medidaSalva).isNotNull();
    }

    @Test
    public void deve_encontrar_todas_as_medidas() {
        repository.save(medida1);
        repository.save(medida2);
        repository.save(medida3);

        List<Medida> medidas = repository.findAll();

        assertThat(medidas).hasSize(3).contains(medida1, medida2, medida3);
    }

    @Test
    public void deve_encontrar_medida_por_id() {
        entityManager.persist(medida1);

        Optional<Medida> medidaPorId = repository.findById(medida1.getId());

        assertThat(medidaPorId.get()).isEqualTo(medida1);
    }

    @Test
    public void deve_encontrar_medida_por_nome_contendo_string() {
        entityManager.persist(medida1);
        entityManager.persist(medida2);
        entityManager.persist(medida3);

        List<Medida> medidas = repository.findByNomeContaining("dade");

        assertThat(medidas).hasSize(1).contains(medida1);
    }

    @Test
    public void deve_atualizar_medida_por_id() {
        entityManager.persist(medida1);
        entityManager.persist(medida2);
        String novoNome = "Novo nome";
        String novoSigla = "NS";
        Optional<Medida> medida = repository.findById(medida2.getId());
        medida.get().setNome(novoNome);
        medida.get().setSigla(novoSigla);

        Medida medidaSalva = repository.save(medida.get());

        assertThat(medidaSalva.getId()).isEqualTo(medida2.getId());
        assertThat(medidaSalva.getNome()).isEqualTo(novoNome);
        assertThat(medidaSalva.getSigla()).isEqualTo(novoSigla);
    }

    @Test
    public void deve_deletar_medida_por_id() {
        entityManager.persist(medida1);
        entityManager.persist(medida2);
        entityManager.persist(medida3);

        repository.deleteById(medida2.getId());
        List<Medida> medidas = repository.findAll();

        assertThat(medidas).hasSize(2).contains(medida1, medida3);
    }

    @Test
    public void deve_deletar_todos_as_medidas() {
        entityManager.persist(medida1);
        entityManager.persist(medida2);
        entityManager.persist(medida3);

        repository.deleteAll();

        assertThat(repository.findAll()).isEmpty();
    }
}

package br.edu.infnet.ordem.modelo.entidades;

import javax.persistence.*;

@Entity
public class Medida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false, length = 3)
    private String sigla;

    public Medida(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return "Medida {" +
                "Id=" + id +
                ", Nome: '" + nome + '\'' +
                ", Sigla: '" + sigla + '\'' +
                '}';
    }
}

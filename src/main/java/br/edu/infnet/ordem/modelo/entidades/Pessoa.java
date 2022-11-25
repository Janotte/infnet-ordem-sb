package br.edu.infnet.ordem.modelo.entidades;

import javax.persistence.*;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @Column(nullable = false, length = 80)
    private String nome;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false, length = 20)
    private String whatsapp;

    public Pessoa(TipoPessoa tipoPessoa, String nome, Endereco endereco, String whatsapp) {
        this.tipoPessoa = tipoPessoa;
        this.nome = nome;
        this.endereco = endereco;
        this.whatsapp = whatsapp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    @Override
    public String toString() {
        return "Pessoa {" +
                "Id=" + id +
                ", Tipo  de Pessoa=" + tipoPessoa +
                ", Nome: '" + nome + '\'' +
                ", Endereço: " + endereco +
                ", Whatsapp: '" + whatsapp + '\'' +
                '}';
    }
}

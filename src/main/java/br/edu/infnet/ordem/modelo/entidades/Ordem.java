package br.edu.infnet.ordem.modelo.entidades;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Enumerated(EnumType.STRING)
    @Column(name = "local_atendimento")
    private LocalAtendimento localAtendimento;

    @ManyToOne
    @JoinColumn(name = "atendente_id", nullable = false)
    private Usuario atendente;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Pessoa cliente;

    @Column(nullable = false)
    private String equipamento;

    @Column(nullable = false)
    private String solicitacao;

    private String diagnostico;

    private String solucao;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ordem_id")
    private List<ItemProduto> produtos;

    public Ordem() {
    }

    public Ordem(LocalDateTime dataAbertura, LocalAtendimento localAtendimento, Usuario atendente, Pessoa cliente, String equipamento, String solicitacao, String diagnostico, String solucao, List<ItemProduto> produtos) {
        this.dataAbertura = dataAbertura;
        this.localAtendimento = localAtendimento;
        this.atendente = atendente;
        this.cliente = cliente;
        this.equipamento = equipamento;
        this.solicitacao = solicitacao;
        this.diagnostico = diagnostico;
        this.solucao = solucao;
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalAtendimento getLocalAtendimento() {
        return localAtendimento;
    }

    public void setLocalAtendimento(LocalAtendimento localAtendimento) {
        this.localAtendimento = localAtendimento;
    }

    public Usuario getAtendente() {
        return atendente;
    }

    public void setAtendente(Usuario atendente) {
        this.atendente = atendente;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(String solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public List<ItemProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItemProduto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Ordem {" +
                "Id: " + id +
                ", Data de Abertura: " + dataAbertura +
                ", Local de Atendimento: " + localAtendimento +
                ", Atendente: " + atendente +
                ", Cliente: " + cliente +
                ", Equipamento: '" + equipamento + '\'' +
                ", Solicitação: '" + solicitacao + '\'' +
                ", Diagnóstico: '" + diagnostico + '\'' +
                ", Solução: '" + solucao + '\'' +
                ", Produtos: " + produtos +
                '}';
    }
}

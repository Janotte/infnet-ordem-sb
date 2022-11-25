package br.edu.infnet.ordem.modelo.entidades;

import javax.persistence.*;

@Entity
public class ItemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_id")
    private Ordem ordem;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(nullable = false)
    private Double quantidade;

    @Column(nullable = false)
    private Double valor;

    public ItemProduto() {
    }

    public ItemProduto(Produto produto, Double quantidade, Double valor) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Item de Produto {" +
                "Id: " + id +
                ", Ordem: " + ordem +
                ", Produto: " + produto +
                ", Quantidade:" + quantidade +
                ", Valor: " + valor +
                '}';
    }
}

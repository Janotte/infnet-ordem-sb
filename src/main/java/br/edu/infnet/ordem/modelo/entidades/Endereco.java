package br.edu.infnet.ordem.modelo.entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

    @Column(name = "endereco_cep", length = 15)
    private String cep;

    @Column(name = "endereco_logradouro", length = 60)
    private String logradouro;

    @Column(name = "endereco_numero", length = 10)
    private String numero;

    @Column(name = "endereco_complemento", length = 20)
    private String complemento;

    @Column(name = "endereco_bairro", length = 40)
    private String bairro;

    @Column(name = "endereco_cidade", length = 40)
    private String cidade;

    @Column(name = "endereco_uf", length = 2)
    private String uf;

    public Endereco() {
    }

    public Endereco(String cep, String logradouro, String numero, String complemento, String bairro, String cidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Endereço {" +
                "CEP: '" + cep + '\'' +
                ", Logradouro: '" + logradouro + '\'' +
                ", Número: '" + numero + '\'' +
                ", Complemento: '" + complemento + '\'' +
                ", Bairro: '" + bairro + '\'' +
                ", Cidade: '" + cidade + '\'' +
                ", UF: '" + uf + '\'' +
                '}';
    }
}

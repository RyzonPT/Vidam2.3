package com.example.vidam.vidam;

import java.io.Serializable;

public class Componente implements Serializable {
    private String designacao;
    private String descricao;
    private double pvp;
    private String imagem;
    private double preco;
    private Integer quantidade;
    private Integer idComponente;

    public Componente(String designacao, String descricao, double pvp, String imagem, double preco, Integer quantidade, Integer idComponente) {
        this.designacao = designacao;
        this.descricao = descricao;
        this.pvp = pvp;
        this.imagem = imagem;
        this.preco = preco;
        this.quantidade = quantidade;
        this.idComponente = idComponente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Integer getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Integer idComponente) {
        this.idComponente = idComponente;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}

package com.example.vidam.vidam;

import java.io.Serializable;

public class TipoServico implements Serializable {
    private Integer idTipoServico;
    private String designacao;
    private String descricao;
    private String obs;
    private Equipamento equipamento;
    public Integer count =1;
    

    public TipoServico(Integer idTipoServico, String designacao, String descricao, String obs, Equipamento equipamento) {
        this.idTipoServico = idTipoServico;
        this.designacao = designacao;
        this.descricao = descricao;
        this.obs = obs;
        this.equipamento = equipamento;
        this.count = 1;
    }

    public TipoServico(TipoServico t) {
        this.idTipoServico = t.getIdTipoServico();
        this.designacao = t.getDesignacao();
        this.descricao = t.getDescricao();
        this.obs = t.getObs();
        this.equipamento = t.getEquipamento();
        this.count = t.getCount();
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdTipoServico() {
        return idTipoServico;
    }

    public void setIdTipoServico(Integer idTipoServico) {
        this.idTipoServico = idTipoServico;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}

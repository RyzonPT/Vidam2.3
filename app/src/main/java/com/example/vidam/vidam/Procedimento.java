package com.example.vidam.vidam;

import java.io.Serializable;

public class Procedimento implements Serializable {
    private Integer idProcedimento;
    private String designacao;
    private int estado;
    private String obs;
    private char state;
    private int idEquipamento;
    private int changed;


    public int getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(int idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public int getChanged() {
        return changed;
    }

    public void setChanged(int changed) {
        this.changed = changed;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public Procedimento(Integer idProcedimento, String designacao, int estado, String obs, char state) {
        this.idProcedimento = idProcedimento;
        this.designacao = designacao;
        this.estado = estado;
        this.obs = obs;
        this.state = state;
        this.changed = 0;
    }

    public Integer getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Integer idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
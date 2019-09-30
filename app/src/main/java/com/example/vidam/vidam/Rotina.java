package com.example.vidam.vidam;

public class Rotina {
    private Integer idRotina;
    private String data;
    private String tipo;
    private String obs;

    public Rotina(Integer idRotina, String data, String tipo, String obs) {
        this.idRotina = idRotina;
        this.data = data;
        this.tipo = tipo;
        this.obs = obs;
    }

    public Integer getIdRotina() {
        return idRotina;
    }

    public void setIdRotina(Integer idRotina) {
        this.idRotina = idRotina;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}

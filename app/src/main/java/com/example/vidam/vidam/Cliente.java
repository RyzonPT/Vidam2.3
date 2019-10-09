package com.example.vidam.vidam;

import java.io.Serializable;

public class Cliente implements Serializable {
    private Integer idCliente;
    private String nome;
    private Integer nContribuinte;
    private String tipo;
    private char grau;
    private String obs;
    private String rua;
    //kkffdsd
    private String localidade;
    private String codigoPostal;
    private Integer telemovel1;
    private Integer telemovel2;
    private String email;


    public Cliente(Integer idCliente, String nome, Integer nContribuinte, String tipo, char grau, String obs, String rua, String localidade, String codigoPostal, Integer telemovel1, Integer telemovel2, String email) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.nContribuinte = nContribuinte;
        this.tipo = tipo;
        this.grau = grau;
        this.obs = obs;
        this.rua = rua;
        this.localidade = localidade;
        this.codigoPostal = codigoPostal;
        this.telemovel1 = telemovel1;
        this.telemovel2 = telemovel2;
        this.email = email;
    }

    public char getGrau() {
        return grau;
    }

    public void setGrau(char grau) {
        this.grau = grau;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getnContribuinte() {
        return nContribuinte;
    }

    public void setnContribuinte(Integer nContribuinte) {
        this.nContribuinte = nContribuinte;
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        codigoPostal = codigoPostal;
    }

    public Integer getTelemovel1() {
        return telemovel1;
    }

    public void setTelemovel1(Integer telemovel1) {
        this.telemovel1 = telemovel1;
    }

    public Integer getTelemovel2() {
        return telemovel2;
    }

    public void setTelemovel2(Integer telemovel2) {
        this.telemovel2 = telemovel2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.example.vidam.vidam;

public class Funcionario {
    private int id;
    private String nome;
    private String tipo;
    private Integer telemovel;
    private String email;
    private String rua;
    private String localidade;
    private String codigoPostal;
    private String password;

    public Funcionario(String nome, String tipo, Integer telemovel, String email, String rua, String localidade, String codigoPostal, String password) {
        this.nome = nome;
        this.tipo = tipo;
        this.telemovel = telemovel;
        this.email = email;
        this.rua = rua;
        this.localidade = localidade;
        this.codigoPostal = codigoPostal;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getTelemovel() {
        return telemovel;
    }

    public String getEmail() {
        return email;
    }

    public String getRua() {
        return rua;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getPassword() {
        return password;
    }
}
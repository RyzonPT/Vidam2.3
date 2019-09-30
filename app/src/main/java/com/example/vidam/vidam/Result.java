package com.example.vidam.vidam;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 14/04/17.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("servicos")
    private Servicos servicos;

    @SerializedName("componentes")
    private ArrayList<Componente> componentes;

    private int id;

    public Result(Boolean error, String message, Servicos servicos, ArrayList<Componente> componentes, int id) {
        this.error = error;
        this.message = message;
        this.servicos = servicos;
        this.componentes = componentes;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public Servicos getServicos() {
        return servicos;
    }

    public ArrayList<Componente> getComponentes() {
        return componentes;
    }
}
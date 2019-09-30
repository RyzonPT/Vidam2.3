package com.example.vidam.vidam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Servicos implements Serializable {
    public ArrayList<Servico> servicos;

    public Servicos(ArrayList<Servico> servicos) {
        this.servicos = servicos;
    }

    public ArrayList<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(ArrayList<Servico> servicos) {
        this.servicos = servicos;
    }
}

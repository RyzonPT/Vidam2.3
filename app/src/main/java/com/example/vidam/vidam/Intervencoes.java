package com.example.vidam.vidam;

import java.util.ArrayList;
import java.util.HashMap;

public class Intervencoes {
    private ArrayList<Intervencao> intervencoes;

    public Intervencoes(ArrayList<Intervencao> intervencoes) {
        this.intervencoes = intervencoes;
    }

    public ArrayList<Intervencao> getIntervencoes() {
        return intervencoes;
    }

    public void setIntervencoes(ArrayList<Intervencao> intervencoes) {
        this.intervencoes = intervencoes;
    }
}

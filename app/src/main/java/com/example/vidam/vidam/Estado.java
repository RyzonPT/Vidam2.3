package com.example.vidam.vidam;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Estado implements Serializable {
    private Integer idEstado;
    private char estadoGeral;
    private String estadoGeralObs;
    private char manutenibilidade;
    private String manutenibilidadeObs;
    private char acessibilidade;
    private String j;
    private String acessibilidadeObs;
    private char elementosAux;
    private String elementosAuxObs;
    private char ruidosEstranhos;
    private String ruidosEstranhosObs;
    private String data;
    private char state; //// 'E' (Espera) Enviado n verificado      //////   'N' Nao enviado /////////////  'V' enviado Verificado

    public Estado(Integer idEstado, char estadoGeral, String estadoGeralObs, char manutenibilidade, String manutenibilidadeObs, char acessibilidade, String acessibilidadeObs, char elementosAux, String elementosAuxObs, char ruidosEstranhos, String ruidosEstranhosObs, String data, char state) {
        this.idEstado = idEstado;
        this.estadoGeral = estadoGeral;
        this.estadoGeralObs = estadoGeralObs;
        this.manutenibilidade = manutenibilidade;
        this.manutenibilidadeObs = manutenibilidadeObs;
        this.acessibilidade = acessibilidade;
        this.acessibilidadeObs = acessibilidadeObs;
        this.elementosAux = elementosAux;
        this.elementosAuxObs = elementosAuxObs;
        this.ruidosEstranhos = ruidosEstranhos;
        this.ruidosEstranhosObs = ruidosEstranhosObs;
        this.data = data;
        this.state = state;
    }

    public Estado(){
        estadoGeral = '0';
        estadoGeralObs = "";
        manutenibilidade = '0';
        manutenibilidadeObs = "";
        acessibilidade = '0';
        acessibilidadeObs = "";
        elementosAux = '0';
        elementosAuxObs = "";
        ruidosEstranhos = '0';
        ruidosEstranhosObs = "";
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        data = formattedDate;
        state = 'N';
    }
    public Estado(Estado e){
          idEstado = e.idEstado;
          estadoGeral = e.estadoGeral;
          estadoGeralObs = e.estadoGeralObs;
          manutenibilidade = e.manutenibilidade;
          manutenibilidadeObs = e.manutenibilidadeObs;
          acessibilidade = e.acessibilidade;
          acessibilidadeObs = e.acessibilidadeObs;
          elementosAux = e.elementosAux;
          elementosAuxObs = e.elementosAuxObs;
          ruidosEstranhos = e.ruidosEstranhos;
          ruidosEstranhosObs = e.ruidosEstranhosObs;
          data = e.data;
          state = 'N';
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public char getEstadoGeral() {
        return estadoGeral;
    }

    public void setEstadoGeral(char estadoGeral) {
        this.estadoGeral = estadoGeral;
    }

    public String getEstadoGeralObs() {
        return estadoGeralObs;
    }

    public void setEstadoGeralObs(String estadoGeralObs) {
        this.estadoGeralObs = estadoGeralObs;
    }

    public char getManutenibilidade() {
        return manutenibilidade;
    }

    public void setManutenibilidade(char manutenibilidade) {
        this.manutenibilidade = manutenibilidade;
    }

    public String getManutenibilidadeObs() {
        return manutenibilidadeObs;
    }

    public void setManutenibilidadeObs(String manutenibilidadeObs) {
        this.manutenibilidadeObs = manutenibilidadeObs;
    }

    public char getAcessibilidade() {
        return acessibilidade;
    }

    public void setAcessibilidade(char acessibilidade) {
        this.acessibilidade = acessibilidade;
    }

    public String getAcessibilidadeObs() {
        return acessibilidadeObs;
    }

    public void setAcessibilidadeObs(String acessibilidadeObs) {
        this.acessibilidadeObs = acessibilidadeObs;
    }

    public char getElementosAux() {
        return elementosAux;
    }

    public void setElementosAux(char elementosAux) {
        this.elementosAux = elementosAux;
    }

    public String getElementosAuxObs() {
        return elementosAuxObs;
    }

    public void setElementosAuxObs(String elementosAuxObs) {
        this.elementosAuxObs = elementosAuxObs;
    }

    public char getRuidosEstranhos() {
        return ruidosEstranhos;
    }

    public void setRuidosEstranhos(char ruidosEstranhos) {
        this.ruidosEstranhos = ruidosEstranhos;
    }

    public String getRuidosEstranhosObs() {
        return ruidosEstranhosObs;
    }

    public void setRuidosEstranhosObs(String ruidosEstranhosObs) {
        this.ruidosEstranhosObs = ruidosEstranhosObs;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
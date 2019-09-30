package com.example.vidam.vidam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Intervencao implements Serializable {
    private Integer idIntervencao;
    private String trabalhoPrestado;
    private String horaInicio;
    private String horaFim;
    private String dataIntervencao;
    private char state;
    private int js;
    private Integer idFuncionario;
    private List<TipoServico> tiposServico;
    private List<Componente> componentes;
    private Integer idServico;

    public Intervencao(Integer idIntervencao, String trabalhoPrestado, String horaIncio, String horaFim, String dataIntervencao, char state, Integer idFuncionario, ArrayList<Componente> componentes) {
        this.idIntervencao = idIntervencao;
        this.trabalhoPrestado = trabalhoPrestado;
        this.horaInicio = horaIncio;
        this.horaFim = horaFim;
        this.dataIntervencao = dataIntervencao;
        this.state = state;
        this.idFuncionario = idFuncionario;
        this.componentes = componentes;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(Integer idServico) {
        this.idServico = idServico;
    }

    public List<TipoServico> getTiposServico() {
        return tiposServico;
    }

    public void setTiposServico(List<TipoServico> tiposServico) {
        this.tiposServico = tiposServico;
    }

    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public Integer getIdIntervencao() {
        return idIntervencao;
    }

    public void setIdIntervencao(Integer idIntervencao) {
        this.idIntervencao = idIntervencao;
    }

    public String getTrabalhoPrestado() {
        return trabalhoPrestado;
    }

    public void setTrabalhoPrestado(String trabalhoPrestado) {
        this.trabalhoPrestado = trabalhoPrestado;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public String getDataIntervencao() {
        return dataIntervencao;
    }

    public void setDataIntervencao(String dataIntervencao) {
        this.dataIntervencao = dataIntervencao;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }


    public boolean containsComponente(Componente comp){
        for(Componente c : componentes){
            if(c.getIdComponente() == comp.getIdComponente())
                return true;
        }
        return false;
    }

    public void addComponente(Componente comp, int quant){
        System.out.println(componentes.size()+" WTFF");
        Iterator<Componente> iter = componentes.iterator();
        while (iter.hasNext()) {
            Componente c = iter.next();
            System.out.println(c.getIdComponente() +"   "+ comp.getIdComponente());
            if(c.getIdComponente().intValue() == comp.getIdComponente().intValue()){
                System.out.println("REMOVI O " +c.getIdComponente());
                iter.remove();

            }
        }

        Componente clone = (Componente) ServicosManager.deepClone(comp);
        clone.setQuantidade(quant);
        componentes.add(clone);
    }


    public void  setStateSend(){
        setState('E');
        for(TipoServico ts : tiposServico){
            for(Estado e : ts.getEquipamento().getEstados()){
                if(e.getState() == 'N'){
                    e.setState('E');
                }
            }
            for(Procedimento p :ts.getEquipamento().getProcedimentos()){
                if(p.getChanged() == 1){
                    p.setState('E');
                }
            }
        }
    }


}
package com.example.vidam.vidam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Servico implements Serializable {
    private Integer idServico;
    private Cliente cliente;
    private String ordemServico;
    private String estado;
    private String dataPrevista;
    private String dataPrestacao;
    private String dataMarcacao;
    private Integer idFuncionario;
    private String rua;
    private String localidade;
    private String designacao;
    private String codigoPostal;
    private Integer idTipoServico;
    private List<Intervencao> intervencoes;
    private List<TipoServico> tiposServico;
    private List<Componente> componentes;
    private String coordenadas;

    public Servico(Integer idServico, Cliente cliente, String ordemServico, String estado, String dataPrevista, String dataPrestacao, String dataMarcacao, Integer idFuncionario, String rua, String localidade, String designacao, String codigoPostal, Integer idTipoServico, List<Intervencao> intervencoes, List<TipoServico> tiposServico, List<Componente> componentes, String coordenadas) {
        this.idServico = idServico;
        this.cliente = cliente;
        this.ordemServico = ordemServico;
        this.estado = estado;
        this.dataPrevista = dataPrevista;
        this.dataPrestacao = dataPrestacao;
        this.dataMarcacao = dataMarcacao;
        this.idFuncionario = idFuncionario;
        this.rua = rua;
        this.localidade = localidade;
        this.designacao = designacao;
        this.codigoPostal = codigoPostal;
        this.idTipoServico = idTipoServico;
        this.intervencoes = intervencoes;
        this.tiposServico = tiposServico;
        this.componentes = componentes;
        this.coordenadas = coordenadas;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(String ordemServico) {
        this.ordemServico = ordemServico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(String dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public String getDataPrestacao() {
        return dataPrestacao;
    }

    public void setDataPrestacao(String dataPrestacao) {
        this.dataPrestacao = dataPrestacao;
    }

    public String getDataMarcacao() {
        return dataMarcacao;
    }

    public void setDataMarcacao(String dataMarcacao) {
        this.dataMarcacao = dataMarcacao;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
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
        this.codigoPostal = codigoPostal;
    }

    public Integer getIdTipoServico() {
        return idTipoServico;
    }

    public void setIdTipoServico(Integer idTipoServico) {
        this.idTipoServico = idTipoServico;
    }

    public List<Intervencao> getIntervencoes() {
        return intervencoes;
    }

    public void setIntervencoes(List<Intervencao> intervencoes) {
        this.intervencoes = intervencoes;
    }

    public List<TipoServico> getTiposServico() {
        return tiposServico;
    }

    public void setTiposServico(List<TipoServico> tiposServico) {
        this.tiposServico = tiposServico;
    }


    public void addIntervencaoInfo(Intervencao intervencao){


        for(TipoServico ts : tiposServico){
                for(TipoServico its :intervencao.getTiposServico()){
                    if(its.getEquipamento().getIdEquipamento() == ts.getEquipamento().getIdEquipamento()){

                    }
            }
        }
    }
}
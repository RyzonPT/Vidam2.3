package com.example.vidam.vidam;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipamento implements Serializable {
    private Integer idEquipamento;
    private String dataGarantia;
    private String anoFabrico;
    private Integer nSerie;
    private String designacao;
    private String modelo;
    private String imagepath;
    private double pvp;
    private MyBitMap imagem;
    private String marca;
    private String tManutencao;
    private double preco;
    private String tipo;
    private String tipoServico;
    private String rotina;
    private List<Estado> estados;
    private List<Procedimento> procedimentos;

    public Equipamento(Integer idEquipamento, String dataGarantia, String anoFabrico, Integer nSerie, String designacao, String modelo, String imagepath, double pvp, MyBitMap imagem, String marca, String tManutencao, double preco, String tipo, String tipoServico, String rotina, List<Estado> estados, List<Procedimento> procedimentos) {
        this.idEquipamento = idEquipamento;
        this.dataGarantia = dataGarantia;
        this.anoFabrico = anoFabrico;
        this.nSerie = nSerie;
        this.designacao = designacao;
        this.modelo = modelo;
        this.imagepath = imagepath;
        this.pvp = pvp;
        this.imagem = imagem;
        this.marca = marca;
        this.tManutencao = tManutencao;
        this.preco = preco;
        this.tipo = tipo;
        this.tipoServico = tipoServico;
        this.rotina = rotina;
        this.estados = estados;
        this.procedimentos = procedimentos;
    }

    public double getPvp() {
        return pvp;
    }

    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    public Integer getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Integer idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public String getDataGarantia() {
        return dataGarantia;
    }

    public void setDataGarantia(String dataGarantia) {
        this.dataGarantia = dataGarantia;
    }

    public String getAnoFabrico() {
        return anoFabrico;
    }

    public void setAnoFabrico(String anoFabrico) {
        this.anoFabrico = anoFabrico;
    }

    public Integer getnSerie() {
        return nSerie;
    }

    public void setnSerie(Integer nSerie) {
        this.nSerie = nSerie;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public MyBitMap getImagem() {
        return imagem;
    }

    public void setImagem(MyBitMap imagem) {
        this.imagem = imagem;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String gettManutencao() {
        return tManutencao;
    }

    public void settManutencao(String tManutencao) {
        this.tManutencao = tManutencao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getRotina() {
        return rotina;
    }

    public void setRotina(String rotina) {
        this.rotina = rotina;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Procedimento> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }
    public void addImagem(Bitmap bitmap){
        imagem = new MyBitMap(bitmap);
    }

    public void initProcedimentos(){
        for(Procedimento p :procedimentos){
            p.setChanged(0);
        }
    }
    public void clearEstados(){
        estados.clear();
    }
}
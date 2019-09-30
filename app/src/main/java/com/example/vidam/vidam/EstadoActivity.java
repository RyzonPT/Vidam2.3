package com.example.vidam.vidam;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EstadoActivity extends AppCompatActivity {

    private Integer intervencaoIndex;
    private Integer servicoIndex;
    private Estado estado;
    private Integer tipoServicoIndex;
    private Integer estadoIndex;
    private Context  context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
        intervencaoIndex = (Integer) getIntent().getSerializableExtra("IntervencaoIndex");
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");
        tipoServicoIndex = (Integer) getIntent().getSerializableExtra("TipoServicoIndex");
        estadoIndex = (Integer) getIntent().getSerializableExtra("EstadoIndex");
        estado = ServicosManager.servicos.get(servicoIndex).getTiposServico().get(tipoServicoIndex).getEquipamento().getEstados().get(estadoIndex);
        context = this;
        init();
    }

    public void init() {
        TextView geral = (TextView) findViewById(R.id.estadogeral);
        TextView geralobs = (TextView) findViewById(R.id.estadogeralobs);
        TextView manutenibilidade = (TextView) findViewById(R.id.manutenibilidade);
        TextView manutenabilidadeobs = (TextView) findViewById(R.id.manutenabilidadeobs);
        TextView acessibilidade = (TextView) findViewById(R.id.acessibilidade);
        TextView acessibilidadeobs = (TextView) findViewById(R.id.acessibilidadeobs);
        TextView elementosaux = (TextView) findViewById(R.id.elementosaux);
        TextView elementosauxobs = (TextView) findViewById(R.id.elementosauxobs);
        TextView ruidosestranhos = (TextView) findViewById(R.id.ruidosestranhos);
        TextView ruidosestranhosobs = (TextView) findViewById(R.id.ruidosestranhosobs);
        TextView estadodata = (TextView) findViewById(R.id.estadoData);

        geral.setText(parseEstado(estado.getEstadoGeral()));
        geralobs.setText(estado.getEstadoGeralObs());
        manutenibilidade.setText(parseEstado(estado.getManutenibilidade()));
        manutenabilidadeobs.setText(estado.getManutenibilidadeObs());
        acessibilidade.setText(parseEstado(estado.getAcessibilidade()));
        acessibilidadeobs.setText(estado.getAcessibilidadeObs());
        elementosaux.setText(parseEstado(estado.getElementosAux()));
        elementosauxobs.setText(estado.getElementosAuxObs());
        ruidosestranhos.setText(parseEstado(estado.getRuidosEstranhos()));
        ruidosestranhosobs.setText(estado.getRuidosEstranhosObs());
        estadodata.setText(estado.getData());

    }

    public String parseEstado(char c){
        int x = Integer.parseInt(String.valueOf(c));
        switch (x) {
            case 0:
                return "Escolher";
            case 1:
                return "Bem";
            case 2:
                return "Aceitavel";
            case 3:
                return "Regular";
            case 4:
                return "Mau";
            case 5:
                return "Muito Mau\"";
            case 6:
                return "Inaceitável";
        }
        return "erro";
    }
}

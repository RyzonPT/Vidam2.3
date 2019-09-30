package com.example.vidam.vidam;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

public class EstadosActivity extends AppCompatActivity {
    private Integer intervencaoIndex;
    private LinearLayout layout;
    private Integer tipoServicoIndex;
    private Integer servicoIndex;
    private Equipamento equipamento;
    private Servico servico;
    private String mode;
    private Intervencao intervencao;
    private Equipamento iEquipamento;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados);
        intervencaoIndex = (Integer) getIntent().getSerializableExtra("IntervencaoIndex");
        tipoServicoIndex = (Integer) getIntent().getSerializableExtra("TipoServicoIndex");
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");
        mode = (String) getIntent().getSerializableExtra("Mode");
        servico = ServicosManager.servicos.get(servicoIndex);

        equipamento = servico.getTiposServico().get(tipoServicoIndex).getEquipamento();

        layout = (LinearLayout) findViewById(R.id.layoutt);
        context = this;


        Button criarEstado = findViewById(R.id.CriarEstadoButton);
        if(mode.equals("READ")) {
            criarEstado.setEnabled(false);
        }
        criarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( context,CriarEstadoActivity.class);
                i.putExtra("IntervencaoIndex",intervencaoIndex);
                i.putExtra("TipoServicoIndex",tipoServicoIndex);
                i.putExtra("ServicoIndex",servicoIndex);
                i.putExtra("Mode","CREATE");
                startActivityForResult(i, 1);
            }
        });


        addServicoEstado();
        if(mode.equals("EDIT")) {
            iEquipamento = ServicosManager.localIntervencoes.get(intervencaoIndex).getTiposServico().get(tipoServicoIndex).getEquipamento();
            addLocalEstados();
        }

    }
    public void addServicoEstado(){
        int i = 0;
        System.out.println("SERVICO ESTADOS "+equipamento.getEstados().size() + " "+ servico.getIntervencoes().get(7).getTiposServico().get(0).getEquipamento().getEstados().get(0).getAcessibilidade());
        for(Estado estado : equipamento.getEstados()) {
        Button button = new Button(context);
        button.setTag(i);
        button.setText(estado.getData());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer ind = (Integer) v.getTag();
                    Intent i = new Intent(context, EstadoActivity.class);
                    i.putExtra("IntervencaoIndex", intervencaoIndex);
                    i.putExtra("EstadoIndex", ind);
                    i.putExtra("Mode","READ");
                    i.putExtra("TipoServicoIndex", tipoServicoIndex);
                    i.putExtra("ServicoIndex", servicoIndex);
                    startActivityForResult(i, 1);
                }
            });
            if(estado.getState() == 'V')
                ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(this, R.color.greeni));
            else
                ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(this, android.R.color.holo_orange_light));
        layout.addView(button);
            i++;
        }

        for(Estado estado : servico.getIntervencoes().get(intervencaoIndex).getTiposServico().get(tipoServicoIndex).getEquipamento().getEstados()) {
            Button button = new Button(context);
            button.setTag(i);
            button.setText(estado.getData());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer ind = (Integer) v.getTag();
                    Intent i = new Intent(context, EstadoActivity.class);
                    i.putExtra("IntervencaoIndex", intervencaoIndex);
                    i.putExtra("EstadoIndex", ind);
                    i.putExtra("Mode", "READ");
                    i.putExtra("TipoServicoIndex", tipoServicoIndex);
                    i.putExtra("ServicoIndex", servicoIndex);
                    startActivityForResult(i, 1);
                }
            });
            if (estado.getState() == 'V')
                ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(this, R.color.greeni));
            else
                ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(this, android.R.color.holo_orange_light));
            layout.addView(button);
            i++;
        }

    }

    public void addLocalEstados(){
        int index = 0;
        System.out.println("LOCAL ESTADOS "+iEquipamento.getEstados().size());
        for(Estado estado : iEquipamento.getEstados()){
            Button button = new Button(context);
            button.setTag(index);
            button.setText(estado.getData());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer ind = (Integer) v.getTag();
                    Intent i = new Intent( context,CriarEstadoActivity.class);
                    i.putExtra("IntervencaoIndex",intervencaoIndex);
                    i.putExtra("TipoServicoIndex",tipoServicoIndex);
                    i.putExtra("EstadoIndex", ind);
                    i.putExtra("Mode","EDIT");
                    i.putExtra("ServicoIndex",servicoIndex);
                    startActivityForResult(i, 1);
                }
            });
            layout.addView(button);
            index++;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            layout.removeAllViews();
            if(mode.equals("EDIT"))
                addLocalEstados();
           addServicoEstado();
        }
    }


}

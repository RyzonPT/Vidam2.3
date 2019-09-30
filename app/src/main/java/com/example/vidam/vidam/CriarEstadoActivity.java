package com.example.vidam.vidam;
import com.example.vidam.vidam.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CriarEstadoActivity extends AppCompatActivity {
    private Integer intervencaoIndex;
    private Integer servicoIndex;
    private Integer tipoServicoIndex;
    private Estado newEstado;
    private Context context;
    private  Spinner geral;
    private  EditText geralobs;
    private Spinner manutenibilidade;
    private  EditText manutenabilidadeobs;
    private Spinner acessibilidade;
    private EditText acessibilidadeobs;
    private Integer  estadoIndex;
    private Spinner elementosaux;
    private Intervencao intervencao;
    private EditText elementosauxobs;
    private Spinner ruidosestranhos;
    private EditText ruidosestranhosobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criarestado);
        context = this;
        geral = findViewById(R.id.estadoGerall);
        geralobs = findViewById(R.id.estadoGeralObs);
        manutenibilidade = findViewById(R.id.manutenibilidade);
        manutenabilidadeobs = findViewById(R.id.manutenabilidadeObs);
        acessibilidade = findViewById(R.id.Acessibilidade);
        acessibilidadeobs = findViewById(R.id.AcessibilidadeObs);
        elementosaux = findViewById(R.id.ElementosAux);
        elementosauxobs = findViewById(R.id.ElementosAuxObs);
        ruidosestranhos = findViewById(R.id.RuidosEstranhos);
        ruidosestranhosobs = findViewById(R.id.RuidosEstranhosObs);


        intervencaoIndex = (Integer) getIntent().getSerializableExtra("IntervencaoIndex");
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");
        tipoServicoIndex = (Integer) getIntent().getSerializableExtra("TipoServicoIndex");
        context = this;
        Button deletebutton = findViewById(R.id.DeleteButton);
        String mode = (String) getIntent().getSerializableExtra("Mode");
        intervencao = ServicosManager.localIntervencoes.get(intervencaoIndex);
        Button save = findViewById(R.id.SaveButton);
        if(mode.equals("CREATE")) {

            deletebutton.setVisibility(View.INVISIBLE);
            if(ServicosManager.servicos.get(servicoIndex).getTiposServico().get(tipoServicoIndex).getEquipamento().getEstados().size() != 0)
                newEstado = new Estado(ServicosManager.servicos.get(servicoIndex).getTiposServico().get(tipoServicoIndex).getEquipamento().getEstados().get(0));
            else newEstado = new Estado();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(testEstado()) {
                        intervencao.getTiposServico().get(tipoServicoIndex).getEquipamento().getEstados().add(newEstado);
                        ServicosManager.saveLocal(context);
                        Intent mIntent=new Intent();
                        setResult(RESULT_OK, mIntent);
                        finish();
                    }
                    else Toast.makeText(getApplicationContext(), "Estado Incomplento, preencha todos os campos obrigatórios.", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            estadoIndex = (Integer) getIntent().getSerializableExtra("EstadoIndex");
            newEstado = intervencao.getTiposServico().get(tipoServicoIndex).getEquipamento().getEstados().get(estadoIndex);
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("Aviso")
                            .setMessage("Tem a certeza que pretende apagar este estado?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    intervencao.getTiposServico().get(tipoServicoIndex).getEquipamento().getEstados().remove(newEstado);
                                    ServicosManager.saveLocal(context);
                                    Intent mIntent=new Intent();
                                    setResult(RESULT_OK, mIntent);
                                    finish();
                                }})
                            .setNegativeButton("NÃO", null).show();
                }
            });
            ViewCompat.setBackgroundTintList(deletebutton, ContextCompat.getColorStateList(this, R.color.redi));

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(testEstado()) {
                        ServicosManager.save(context);
                        Intent mIntent=new Intent();
                        setResult(RESULT_OK, mIntent);
                        finish();
                    }
                    else Toast.makeText(getApplicationContext(), "Estado Incomplento, preencha todos os campos obrigatórios.", Toast.LENGTH_LONG).show();
                }
            });
        }

        init();

    }


    public void init() {
        if (newEstado != null) {

            TextView estadodata = (TextView) findViewById(R.id.estadoData);


            ////ESTADO GERAL
            geral.setSelection(Character.getNumericValue(newEstado.getEstadoGeral()));
            geral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    newEstado.setEstadoGeral((char) (position + '0'));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            geralobs.setText(newEstado.getEstadoGeralObs());
            geralobs.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    newEstado.setEstadoGeralObs(geralobs.getText().toString());
                }
            });


            ////Manutenibilidade
            manutenibilidade.setSelection(Character.getNumericValue(newEstado.getManutenibilidade()));
            manutenibilidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    newEstado.setManutenibilidade((char) (position + '0'));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            manutenabilidadeobs.setText(newEstado.getManutenibilidadeObs());
            manutenabilidadeobs.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    newEstado.setManutenibilidadeObs(manutenabilidadeobs.getText().toString());
                }
            });

            //////ACESSIBILIDADE
            acessibilidade.setSelection(Character.getNumericValue(newEstado.getAcessibilidade()));
            acessibilidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    newEstado.setAcessibilidade((char) (position + '0'));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


            acessibilidadeobs.setText(newEstado.getAcessibilidadeObs());
            acessibilidadeobs.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    newEstado.setAcessibilidadeObs(acessibilidadeobs.getText().toString());
                }
            });


            elementosaux.setSelection(Character.getNumericValue(newEstado.getElementosAux()));
            elementosaux.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    newEstado.setElementosAux((char) (position + '0'));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

            elementosauxobs.setText(newEstado.getElementosAuxObs());
            elementosauxobs.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    newEstado.setElementosAuxObs(elementosauxobs.getText().toString());
                }
            });

            //////RUIDOS
            ruidosestranhos.setSelection(Character.getNumericValue(newEstado.getRuidosEstranhos()));
            ruidosestranhos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    newEstado.setRuidosEstranhos((char) (position + '0'));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });


            ruidosestranhosobs.setText(newEstado.getRuidosEstranhosObs());
            ruidosestranhosobs.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    newEstado.setRuidosEstranhosObs(ruidosestranhosobs.getText().toString());
                }
            });
        }
    }

    public boolean testEstado(){
        if((geral.getSelectedItemPosition() != 0 )&& (manutenibilidade.getSelectedItemPosition() !=0 )&&(
                acessibilidade.getSelectedItemPosition() != 0) && (elementosaux.getSelectedItemPosition() != 0) &&
                (ruidosestranhos.getSelectedItemPosition() != 0))
            return true;
        return  false;
    }

}

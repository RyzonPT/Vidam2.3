package com.example.vidam.vidam;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class ServicoActivity extends AppCompatActivity implements Serializable {
    private Servico servico;
    private Integer servicoIndex;
    private TextView ordemText;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico);
        Button relatoriosButton = findViewById(R.id.RelatoriosButton);
        ordemText = findViewById(R.id.OrdemText);
        TextView ruaText = findViewById(R.id.RuaText);
        TextView localidadeText = findViewById(R.id.LocalidadeText);
        TextView codigoPostalText = findViewById(R.id.CodigoPostalText);
        TextView dataPrestacao = findViewById(R.id.dataPrestacao);
        TextView titulo = findViewById(R.id.tituloText);
        TextView dataPrevista = findViewById(R.id.dataPrevista);
        TextView dataMarcacao = findViewById(R.id.dataMarcacao);
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");
        servico = ServicosManager.servicos.get(servicoIndex);

        Button mapaB = findViewById(R.id.mapButton);

        mapaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+servico.getCoordenadas());

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);

            }
        });

        if (servico != null) {
            ordemText.setText(servico.getOrdemServico());
            ruaText.setText("Rua: " + servico.getRua());
            localidadeText.setText("Localidade: " + servico.getLocalidade());
            codigoPostalText.setText("Codigo Postal: " + servico.getCodigoPostal());
            dataMarcacao.setText("Marcação: "+servico.getDataMarcacao());
            dataPrestacao.setText("Prestação: "+ servico.getDataPrestacao());
            dataPrevista.setText("Prevista: "+servico.getDataPrevista());
            titulo.setText("Servico: "+servico.getDesignacao());

        }
        displayOrdemServico();
        displayClienteinfo();
        displayComponentesinfo();
    }


    public TipoServico existTiposAux(ArrayList<TipoServico> tipos,TipoServico ts){
        for(TipoServico tipo : tipos){
            if(tipo.getIdTipoServico() == ts.getIdTipoServico())
                return tipo;
        }
        return null;
    }

    public void displayOrdemServico(){
        for(TipoServico lel : servico.getTiposServico()){
            lel.count = 1;
        }
        String ordem = "";
        ArrayList<TipoServico> tipos = new ArrayList<>();
        TipoServico aux = null;
        for(TipoServico tss :servico.getTiposServico()){
            aux = existTiposAux(tipos,tss);
            if(aux != null){
            aux.count++;
            System.out.println("LALALALALALA"+ aux.count);}
            else
                tipos.add(tss);
            }


        for(TipoServico ts : tipos){
            if(ts.count == 1)
                ordem = ordem + "• "+ts.getDescricao()+"\n";
            else
                ordem = ordem + "• "+ts.getDescricao()+"(x"+ts.getCount()+")"+"\n";
        }
        ordem = ordem + "\n";
        ordem = ordem + "Observações:\n";
        ordem = ordem + servico.getOrdemServico();
        ordemText.setText(ordem);
    }


    public void displayClienteinfo(){
        TextView nomeCliente = findViewById(R.id.nomeCliente);
        nomeCliente.setText("Nome: "+ servico.getCliente().getNome());
        Button clienteB = findViewById(R.id.ClienteButton);
        clienteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onButtonShowPopupWindowClick(v);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ServicoActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.activity_cliente, null);

                Cliente cliente = ServicosManager.servicos.get(servicoIndex).getCliente();
                TextView nome =  mView.findViewById(R.id.Nome);
                TextView nContribuinete =  mView.findViewById(R.id.nContribuinete);
                TextView tipo =  mView.findViewById(R.id.tipo);
                TextView obs =  mView.findViewById(R.id.Obs);
                TextView rua =  mView.findViewById(R.id.rua);
                TextView localidade =  mView.findViewById(R.id.localidade);
                TextView codigoPostal =  mView.findViewById(R.id.codigoPostal);
                TextView telemovel1 =  mView.findViewById(R.id.telemovel1);
                TextView telemovel2 =  mView.findViewById(R.id.telemovel2);
                TextView email =  mView.findViewById(R.id.email);

                nome.setText("Nome: "+cliente.getNome());
                tipo.setText("Tipo: "+cliente.getTipo());
                nContribuinete.setText("Nrº Contribuinte: "+cliente.getnContribuinte().toString());
                obs.setText("Observações: "+cliente.getObs());
                rua.setText("Rua: "+cliente.getRua());
                localidade.setText("Localidade: "+cliente.getLocalidade());
                codigoPostal.setText("Código Postal: "+cliente.getCodigoPostal());
                telemovel1.setText("Telemóvel1: "+cliente.getTelemovel1().toString());
                telemovel2.setText("Telemóvel2: "+cliente.getTelemovel2().toString());
                email.setText("Email: "+cliente.getEmail());



                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                mBuilder.setView(mView);
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog = mBuilder.create();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog.show();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

            }
        });
    }



    public void displayComponentesinfo(){
        Button compsB = findViewById(R.id.componentesB);
        compsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onButtonShowPopupWindowClick(v);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ServicoActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.activity_servico_componentes, null);

                TableLayout table = mView.findViewById(R.id.table);

                for(Componente comp : servico.getComponentes()){
                    TableRow tr = new TableRow(ServicoActivity.this);

                    TextView nomeT = new TextView(ServicoActivity.this);
                    nomeT.setText(comp.getDesignacao());
                    nomeT.setTag(comp);
                    nomeT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //onButtonShowPopupWindowClick(v);
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ServicoActivity.this);
                            final View mView = getLayoutInflater().inflate(R.layout.activity_componente, null);
                            Componente comp = (Componente) v.getTag();
                            ImageView imagem =  mView.findViewById(R.id.Imagem);
                            TextView designacao =  mView.findViewById(R.id.NomeText);
                            TextView descricao =  mView.findViewById(R.id.Descricao);

                            //imagem.setText("Nome: "+cliente.getNome());
                            designacao.setText(comp.getDesignacao());
                            descricao.setText("Descrição: "+comp.getDescricao());


                            getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                            mBuilder.setView(mView);
                            getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                            dialog = mBuilder.create();
                            getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                            dialog.show();
                            getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

                        }
                    });




                    TextView quant = new TextView(ServicoActivity.this);
                    quant.setText(comp.getQuantidade().toString());

                    tr.setBackgroundDrawable(ContextCompat.getDrawable(ServicoActivity.this, R.drawable.border) );
                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                    tr.addView(nomeT);
                    tr.setPadding(20,5,20,5);
                    tr.addView(quant);
                    table.addView(tr);
                }

                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                mBuilder.setView(mView);
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog = mBuilder.create();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog.show();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

            }
        });
    }









    public void gotoTutorial(View v){
        Intent intent = new Intent(this, IntervencoesActivity.class);
        intent.putExtra("ServicoIndex",servicoIndex);
        startActivity(intent);
        //startActivityForResult(intent, 1);
        //EditText editText = (EditText) findViewById(R.id.editText);
        // String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
    }
    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode,resultCode,data);
            if(resultCode == RESULT_OK && requestCode == 1){
                servico = (Servico) getIntent().getSerializableExtra("Servico");


            }
        }*/



}

package com.example.vidam.vidam;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

public class componentesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Intervencao intervencao;
    private Integer servicoIndex;
    private Integer intervencaoIndex;
    private Servico servico;
    private String mode;
    private TableLayout table;
    private AlertDialog dialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");
        intervencaoIndex = (Integer) getIntent().getSerializableExtra("IntervencaoIndex");

        servico = ServicosManager.servicos.get(servicoIndex);
        mode = (String) getIntent().getSerializableExtra("Mode");
        System.out.println("INTERVENCASC" + intervencaoIndex+ " "+mode);
        table = findViewById(R.id.table);
        Button addComponente = findViewById(R.id.AddComponente);
        context = this;
        TextView nom = findViewById(R.id.nome);
        TextView acao = findViewById(R.id.acao);
        if(mode.equals("CREATE")){
            addComponente.setEnabled(false);
            addComponente.setVisibility(View.GONE);

            acao.setVisibility(View.GONE);
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (200 * scale + 0.5f);
            nom.setLayoutParams(new TableRow.LayoutParams (pixels,TableRow.LayoutParams.WRAP_CONTENT));
            intervencao = ServicosManager.localIntervencoes.get(intervencaoIndex);
           // displayComponentes(ServicosManager.componentes);

        }
        else if(mode.equals("READ")){
            intervencao = servico.getIntervencoes().get(intervencaoIndex);
            addComponente.setEnabled(false);
           // displayComponentes((ArrayList)intervencao.getComponentes());
            acao.setVisibility(View.GONE);
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (200 * scale + 0.5f);
            nom.setLayoutParams(new TableRow.LayoutParams (pixels,TableRow.LayoutParams.WRAP_CONTENT));
        }
        else {
            intervencao = ServicosManager.localIntervencoes.get(intervencaoIndex);
           // displayComponentes((ArrayList)intervencao.getComponentes());
            addComponente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,componentesActivity.class);
                    i.putExtra("IntervencaoIndex",intervencaoIndex);
                    i.putExtra("ServicoIndex",servicoIndex);
                    i.putExtra("Mode","CREATE");
                    startActivity(i);
                }
            });
        }


        System.out.println(intervencao.getComponentes().size()+"FFFFFFFFFFFFFF");
    }

    @Override
    public void onResume(){
        super.onResume();
        while (table.getChildCount() > 1)
            table.removeView(table.getChildAt(table.getChildCount() - 1));
        if(!mode.equals("CREATE")) {
            displayComponentes((ArrayList) intervencao.getComponentes());
            System.out.println(intervencao.getComponentes().size()+" SIZEEEE");
        }
        else
            displayComponentes(ServicosManager.componentes);
    }

    public void displayComponentes(ArrayList<Componente> comps){
        int index = 0;
        for(final Componente comp : comps){
            TableRow tr = new TableRow(this);

            TextView nomeT = new TextView(this);
            nomeT.setText(comp.getDesignacao());
            nomeT.setTag(comp);
            nomeT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //onButtonShowPopupWindowClick(v);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(componentesActivity.this);
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

            final TextView txtCount = new TextView(this);
            ImageButton buttonInc= new ImageButton(this);
            txtCount.setText("111");
            ImageButton buttonDec= new ImageButton(this);
            buttonInc.setImageResource(android.R.drawable.ic_input_add);
            buttonDec.setImageResource(android.R.drawable.ic_delete);
            LinearLayout lay = new LinearLayout(this);

            buttonInc.setLayoutParams(new TableRow.LayoutParams(100, 105));
            buttonDec.setLayoutParams(new TableRow.LayoutParams(100, 105));

            lay.addView(buttonDec);
            lay.addView(txtCount);
            lay.addView(buttonInc);
            tr.addView(lay);
            buttonInc.setTag(comp);
            buttonDec.setTag(comp);

            buttonInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Componente comp = (Componente) v.getTag();
                    comp.setQuantidade(comp.getQuantidade()+1);
                    txtCount.setText(String.valueOf(comp.getQuantidade()));

                }
            });

            buttonDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Componente comp = (Componente) v.getTag();
                    comp.setQuantidade(comp.getQuantidade()-1);
                    txtCount.setText(String.valueOf(comp.getQuantidade()));

                }
            });


            /*
            Spinner spinner = new Spinner(this);
            spinner.setTag(comp);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item,  getResources().getStringArray(R.array.quantidades));
                                                        // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            if(!mode.equals("CREATE")) {
                spinner.setSelection(comp.getQuantidade(), false);
            }
            else spinner.setSelection(0, false);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {

                    Object item = adapterView.getItemAtPosition(position);
                    if (item != null) {
                        Componente comp = (Componente) adapterView.getTag();
                        if(mode.equals("CREATE")) {
                            System.out.println(intervencao.getComponentes().size()+ " TAMANHOOO");
                            intervencao.addComponente(comp, position);
                            ServicosManager.saveLocal(context);
                        }
                        else
                            comp.setQuantidade(position);
                        ServicosManager.saveLocal(context);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // TODO Auto-generated method stub

                }
            });*/
            tr.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border) );
            ImageButton apagarB = new ImageButton(this);

            if(mode.equals("READ"))
                //spinner.setEnabled(false);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tr.addView(nomeT);
            tr.setPadding(20,5,20,5);
            //tr.addView(spinner);
            if(mode.equals("EDIT")){
                apagarB.setImageResource(android.R.drawable.ic_menu_delete);
                apagarB.setTag(comp);
                apagarB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Componente c = (Componente) v.getTag();
                        new android.app.AlertDialog.Builder(context)
                                .setTitle("Aviso")
                                .setMessage("Tem a certeza que pretende apagar este Componente?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {

                                        intervencao.getComponentes().remove(c);
                                        ServicosManager.saveLocal(context);
                                        while (table.getChildCount() > 1)
                                            table.removeView(table.getChildAt(table.getChildCount() - 1));
                                            displayComponentes((ArrayList)intervencao.getComponentes());

                                    }})
                                .setNegativeButton("NÃO", null).show();
                    }
                });
                ViewCompat.setBackgroundTintList(apagarB, ContextCompat.getColorStateList(this, R.color.redi));
                apagarB.setLayoutParams(new TableRow.LayoutParams(100, 105));
                tr.addView(apagarB);
            }
            table.addView(tr);
            index++;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, IntervencaoActivity.class);
        intent.putExtra("intervencao",intervencao);
        setResult(RESULT_OK, intent);
    }
}

package com.example.vidam.vidam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ProcedimentosActivity extends AppCompatActivity {
    private Context context;
    private Integer intervencaoIndex;
    private TableLayout table;
    private Equipamento equipamento;
    private Equipamento iequipamento;
    private Integer tipoServicoIndex;
    private Integer servicoIndex;
    private String mode;
    private Servico servico;
    private Intervencao intervencao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedimento);
        table  = (TableLayout) findViewById(R.id.table);
        intervencaoIndex = (Integer) getIntent().getSerializableExtra("IntervencaoIndex");
        tipoServicoIndex = (Integer) getIntent().getSerializableExtra("TipoServicoIndex");
        mode = (String) getIntent().getSerializableExtra("Mode");
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");

        servico = ServicosManager.servicos.get(servicoIndex);
        equipamento = servico.getTiposServico().get(tipoServicoIndex).getEquipamento();

        if(mode.equals("EDIT")) {
            intervencao = ServicosManager.localIntervencoes.get(intervencaoIndex);
            iequipamento = intervencao.getTiposServico().get(tipoServicoIndex).getEquipamento();
        }
        context = this;

        TextView tipo  = (TextView) findViewById(R.id.tipoServicoText);
        tipo.setText(equipamento.getTipoServico());


        int index = 0;
        for(Procedimento proc: equipamento.getProcedimentos()){
            addProcedimentoRow(proc,index);
            index ++;
        }
    }



    public void addProcedimentoRow(final Procedimento p,final int index){
        if(mode.equals("EDIT")) System.out.println("CHANGED  "+iequipamento.getProcedimentos().get(index).getChanged());
        System.out.println("SERVICO CHANGED  "+equipamento.getProcedimentos().get(index).getChanged());

        /* Create a new row to be added. */
        TableRow tr = new TableRow(this);
        Spinner spinner = new Spinner(context);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,  getResources().getStringArray(R.array.percentagens)) {
            // Disable click item < month current
            /*@Override
            public boolean isEnabled(int position) {
                // TODO Auto-generated method stub
                if (position*10 <= p.getEstado()) {

                    return false;
                }
                return true;
            }*/
        };
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(p.getEstado()/10, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {

                    Object item = adapterView.getItemAtPosition(position);
                    if (item != null) {
                        p.setEstado(position * 10);
                        iequipamento.getProcedimentos().get(index).setEstado(position * 10);
                        iequipamento.getProcedimentos().get(index).setChanged(1);
                        ServicosManager.save(context);
                    }
                    Toast.makeText(context, "Selected",
                            Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });

        TextView tv = new TextView(context);
        //spinner.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));

        tv.setLayoutParams(new TableRow.LayoutParams(500, TableRow.LayoutParams.WRAP_CONTENT));
        tv.setText(p.getDesignacao());

         final EditText edittext = new EditText(context);
        edittext.setLayoutParams(new TableRow.LayoutParams(600, TableRow.LayoutParams.WRAP_CONTENT));
        edittext.setTag(p);



        if(mode.equals("READ")) {
            spinner.setEnabled(false);
            edittext.setEnabled(false);
        }


        if(p.getObs() != null){
            edittext.setText(p.getObs());
        }

        LinearLayout hLayout = new LinearLayout(this);
        hLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        hLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout vLayout = new LinearLayout(this);
        vLayout.setOrientation(LinearLayout.VERTICAL);


        if(p.getState() == 'V'){
            edittext.setEnabled(false);
            edittext.setText(p.getObs());
            spinner.setEnabled(false);
            hLayout.setBackgroundColor(Color.parseColor("#ffff00"));
        }

        hLayout.addView(tv);
        hLayout.addView(spinner);

        vLayout.addView(hLayout);
        vLayout.addView(edittext);

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Procedimento proc = (Procedimento) edittext.getTag();
                iequipamento.getProcedimentos().get(index).setObs(edittext.getText().toString());
                iequipamento.getProcedimentos().get(index).setChanged(1);
                System.out.println("TROLLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                proc.setObs(edittext.getText().toString());
                ServicosManager.save(context);
            }
        });


        tr.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border) );
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
        tr.addView(vLayout);

        tr.setPadding(20,20,20,20);
        /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
        table.addView(tr);
    }


    @Override
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

        Intent intent = new Intent(this,IntervencaoActivity.class);
        intent.putExtra("IntervencaoIndex",intervencaoIndex);
        intent.putExtra("ServicoIndex",servicoIndex);
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }
}

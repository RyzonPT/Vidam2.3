package com.example.vidam.vidam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import java.util.Arrays;

public class IntervencoesActivity extends AppCompatActivity {
    private LinearLayout layout;
    private Servico servico;
    private Integer servicoIndex;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervencoes);
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");
        servico = ServicosManager.servicos.get(servicoIndex);
        context = this;
        layout = findViewById(R.id.LinearLayout);


        layout.removeAllViews();



        generateIntervencaoList();

        Button criarIntervencaoB = findViewById(R.id.criarButton);
        criarIntervencaoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  ind = ServicosManager.createIntervencao(servicoIndex);
                ServicosManager.saveLocal(context);
                Intent intent = new Intent(context, IntervencaoActivity.class);
                intent.putExtra("IntervencaoIndex", ind);
                intent.putExtra("Mode", "EDIT");
                intent.putExtra("ServicoIndex", servicoIndex);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();


        layout.removeAllViews();
        generateIntervencaoList();

    }

    public void generateIntervencaoList(){
        int index = 0;
        System.out.println("LOCAL "+ServicosManager.localIntervencoes.size());
        for (Intervencao i : ServicosManager.localIntervencoes) {
            System.out.println(servico.getIdServico()+"  "+i.getIdServico());
            if (i.getIdServico().intValue() == servico.getIdServico().intValue()) {
                System.out.println("PASSEI");
                final Button bt = new Button(this);
                bt.setText("Data: " + i.getDataIntervencao());
                bt.setTag(index);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer ind = (Integer) v.getTag();
                        Intent intent = new Intent(context, IntervencaoActivity.class);
                        Integer iindex = (Integer) bt.getTag();
                        intent.putExtra("IntervencaoIndex", iindex);
                        intent.putExtra("Mode", "EDIT");
                        intent.putExtra("ServicoIndex", servicoIndex);
                        startActivity(intent);
                    }
                });
                bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.addView(bt);

            }
            index++;
        }

        index = 0;
        System.out.println("VALID "+servico.getIntervencoes().size());
        for (Intervencao i : servico.getIntervencoes()) {
            final Button bt = new Button(this);
            bt.setText("Data: " + i.getDataIntervencao());
            bt.setTag(index);
            if (i.getState() == 'V' || i.getState() == 'E') {
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer ind = (Integer) v.getTag();
                        Intent intent = new Intent(context, IntervencaoActivity.class);
                        Integer iindex = (Integer) bt.getTag();
                        intent.putExtra("IntervencaoIndex", iindex);
                        intent.putExtra("Mode", "READ");
                        intent.putExtra("ServicoIndex", servicoIndex);
                        startActivity(intent);
                    }
                });
                if(i.getState() == 'V')
                ViewCompat.setBackgroundTintList(bt, ContextCompat.getColorStateList(this, R.color.greeni));
                if(i.getState() == 'E')
                ViewCompat.setBackgroundTintList(bt, ContextCompat.getColorStateList(this, android.R.color.holo_orange_light));
            } else {
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer ind = (Integer) v.getTag();
                        Intent intent = new Intent(context, IntervencaoActivity.class);
                        Integer iindex = (Integer) bt.getTag();
                        intent.putExtra("IntervencaoIndex", iindex);
                        intent.putExtra("Mode", "EDIT");
                        intent.putExtra("ServicoIndex", servicoIndex);
                        startActivity(intent);
                    }
                });
            }
            bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(bt);
            index++;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            layout.removeAllViews();
            generateIntervencaoList();
        }
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
        super.onBackPressed();
      //  Intent intent = new Intent(this, IntervencoesActivity.class);
      //  intent.putExtra("Servico",servico);
    }
}

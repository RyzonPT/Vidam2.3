package com.example.vidam.vidam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private Button loginButton;
    private EditText emailText, passText;
    private Intent intent;
    private Servicos servicos;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        context = this;

        loginButton = (Button) findViewById(R.id.LoginButton);
        emailText = (EditText) findViewById(R.id.email);
        passText = (EditText) findViewById(R.id.password);
        loginButton.setOnClickListener(this);
        intent = new Intent(this, ServicosActivity.class);
    }


    public void updateServicos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
/*
        Call<List<Servico>> call = service.getActiveServicos();
        Call<List<Intervencao>> call1 = service.getActiveIntervencoes();
        Call<List<Cliente>> call2 = service.getActiveClientes();

        List<Servico> servicosAux;
        List<Intervencao> IntervencaoAux;
        List<Cliente> clienteAux;
        // servicos.clear();
        //your codes here
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            servicosAux = call.execute().body();
            for(Servico s : servicosAux){
                s.setIntervencoes(new ArrayList<Intervencao>());
                servicos.getServicos().put(s.getIdServico(),s);
            }
            System.out.println("SERVICOS  " + servicos.getServicos().size());
            IntervencaoAux = call1.execute().body();

            System.out.println("INTERVENCOES  " + IntervencaoAux.size());
            for(Intervencao i : IntervencaoAux){
                servicos.getServicos().get(i.getIdServico()).addIntervencao(i);
            }

            clienteAux = call2.execute().body();
            HashMap<Integer,Cliente> clientes = new HashMap<>();
            for(Cliente c : clienteAux){
                clientes.put(c.getIdCliente(),c);
            }

            for(Servico s : servicos.getServicos().values()){
                s.setCliente(clientes.get(s.getIdCliente()));
            }

            try {
                FileOutputStream fos = context.openFileOutput("servicos", Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(servicos);
                os.close();
                fos.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            layout.removeAllViews();
            for(Servico s : servicos.getServicos().values()) {
                Button bt = new Button(context);
                bt.setText("rua: "+s.getRua() +"  "+ "localidade: " +s.getLocalidade());
                bt.setTag(s);
                bt.setOnClickListener(listner);
                bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.addView(bt);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
*/ /*
        Call<Servicos> call0 = service.getteste();

        call0.enqueue(new Callback<Servicos>() {
            @Override
            public void onResponse(Call<Servicos> call, Response<Servicos> response) {
                servicos = response.body();

                try {
                    FileOutputStream fos = context.openFileOutput("servicos", Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(servicos);
                    os.close();
                    fos.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                layout.removeAllViews();
                for(Servico s : servicos.getServicos()) {
                    Button bt = new Button(context);
                    bt.setText("rua: "+s.getRua() +"  "+ "localidade: " +s.getLocalidade());
                    bt.setTag(s);
                    bt.setOnClickListener(listner);
                    bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    layout.addView(bt);
                }

            }

            @Override
            public void onFailure(Call<Servicos> call, Throwable t) {
                System.out.println("WSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                System.out.println(t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
    }





    @Override
    public void onClick(View view) {
        if (view == loginButton) {
            //startActivity(lele);
            //funcionarioLoggin();
        }
    }

}

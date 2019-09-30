package com.example.vidam.vidam;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A login screen that offers login via email/password.
 */
public class ServicosActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private LinearLayout layout;
    private Context context;
    private Intent intent;
    private Intervencoes intervencoes;
    private View.OnClickListener listner;
    private EditText email;
    private EditText pass;
    private AlertDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);
        layout = findViewById(R.id.LinearLayout);
        Button updateB = findViewById(R.id.UpdateButton);

        updateB.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           //onButtonShowPopupWindowClick(v);
                                           AlertDialog.Builder mBuilder = new AlertDialog.Builder(ServicosActivity.this);
                                           View mView = getLayoutInflater().inflate(R.layout.activity_signin, null);
                                           email = (EditText) mView.findViewById(R.id.email);
                                           pass = (EditText) mView.findViewById(R.id.password);
                                           Button mLogin = (Button) mView.findViewById(R.id.LoginButton);

                                           mLogin.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {

                                                   funcionarioLoggin(email.getText().toString(),pass.getText().toString());

                                               }
                                           });
                                           mBuilder.setView(mView);
                                           dialog = mBuilder.create();
                                           dialog.show();
                                       }
                                   });
        context = this;
        intent = new Intent(this, ServicoActivity.class);
        listner = this;
        ServicosManager.load(this);
        ServicosManager.loadLocal(this);
        ServicosManager.loadComponentes(this);
        ServicosManager.loadFunc(this);
        System.out.println(ServicosManager.localIntervencoes.size()+" LOCALLLLL");
        int index = 0;
        layout.removeAllViews();
        for(Servico s :  ServicosManager.servicos) {

            Button bt = new Button(context);
            bt.setText("rua: " + s.getRua() + "  " + "localidade: " + s.getLocalidade());
            bt.setTag(index);
            bt.setOnClickListener(listner);
            bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(bt);
            index++;
        }


    }


    public void funcionarioLoggin(String email, String password) {

/*
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // init cookie manager
        CookieHandler cookieHandler = new CookieManager();

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();*/

        final ProgressDialog progressDialog = new ProgressDialog(ServicosActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);


        //Defining the user object as we need to pass it with the call

        //defining the call
        Call<Result> call = service.funcionariologgin(
                email,
                password
        );


        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                // EditText editText = (EditText) findViewById(R.id.editText);
                // String message = editText.getText().toString();
                //intent.putExtra(EXTRA_MESSAGE, message);

                //displaying the message from the response as toast
                 Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().getError()) {
                    int id = response.body().getId();
                    ServicosManager.funcionarioID = id;
                    ServicosManager.saveFunc(context);
                    System.out.println("IIIIIDDDDDDDDDDDDD"+id);
                    updateServicos();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void updateServicos(){
    /*
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // init cookie manager
        CookieHandler cookieHandler = new CookieManager();

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


*/

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        APIService service  = retrofit.create(APIService.class);

        Call<Result> call0 = service.getteste();

        call0.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Servicos aux = response.body().getServicos();
                ArrayList<Componente> aux2 = response.body().getComponentes();
                System.out.println(aux2.size());
                ServicosManager.setServicos(aux.getServicos());
                ServicosManager.componentes = aux2;
                System.out.println(ServicosManager.getServicos().size()+"  MANAGER");

                if (!response.body().getError()) {
                    ServicosManager.save(context);
                    ServicosManager.saveComponentes(context);
                    int index = 0;
                    layout.removeAllViews();
                    for (Servico s : ServicosManager.servicos) {
                        Button bt = new Button(context);
                        bt.setText("rua: " + s.getRua() + "  " + "localidade: " + s.getLocalidade());
                        bt.setTag(index);
                        bt.setOnClickListener(listner);
                        bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        layout.addView(bt);
                        index++;
                    }
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                System.out.println("WSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                System.out.println(t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
            Integer index = (Integer) view.getTag();
            intent.putExtra("ServicoIndex",index);
            startActivity(intent);
            //funcionarioLoggin();
        }

    public void gotoTutorial(View v){
        Intent intent = new Intent(this, ServicoActivity.class);
        // EditText editText = (EditText) findViewById(R.id.editText);
        // String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}


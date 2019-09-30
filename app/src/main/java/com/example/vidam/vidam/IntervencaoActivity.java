package com.example.vidam.vidam;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntervencaoActivity extends AppCompatActivity {

    private Button SendIntervencaoButton;
    private Button componentesButton;
    private Intervencao intervencao;
    private Integer intervencaoIndex;
    private Integer servicoIndex;
    private TimePickerDialog  timePicker;
    private TableLayout table;
    private ImageView selectedImageView;
    private boolean zoomOut =  false;
    DatePickerDialog picker;
    private String mode;
    private Servico servico;
    private Context context;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervencao);
        intervencaoIndex = (Integer) getIntent().getSerializableExtra("IntervencaoIndex");
        servicoIndex = (Integer) getIntent().getSerializableExtra("ServicoIndex");
        mode = (String) getIntent().getSerializableExtra("Mode");
        servico = ServicosManager.servicos.get(servicoIndex);

        table  = findViewById(R.id.table);
        context = this;


        SendIntervencaoButton = (Button) findViewById(R.id.SendIntervencaoButton);
        SendIntervencaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(context)
                        .setTitle("Aviso")
                        .setMessage("Tem a certeza que pretende enviar esta Intervenção?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                    criaIntervencao();
                            }})
                        .setNegativeButton("NÃO", null).show();
            }
        });
        componentesButton = (Button) (Button) findViewById(R.id.ComponentesButton);
        componentesButton.setOnClickListener(componentesListener);
        Button deleteB = findViewById(R.id.DeleteButton);
        EditText trabalhoPrestado = (EditText) findViewById(R.id.TrabalhoPrestadoText);


        if(mode.equals("READ")){

            Button horaEntrada = findViewById(R.id.HoraEntrada);
            Button horaSaida = findViewById(R.id.HoraSaida);
            trabalhoPrestado.setEnabled(false);
            horaEntrada.setEnabled(false);
            horaSaida.setEnabled(false);
            deleteB.setEnabled(false);
            SendIntervencaoButton.setEnabled(false);
            intervencao = servico.getIntervencoes().get(intervencaoIndex);
        }
        else{
            intervencao = ServicosManager.localIntervencoes.get(intervencaoIndex);
        }

        TextView tituloIntervencao = findViewById(R.id.tituloIntervencao);
        tituloIntervencao.setText("Intervenção "+ servico.getDesignacao());
        trabalhoPrestado.setText(intervencao.getTrabalhoPrestado());
        

        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(context)
                        .setTitle("Aviso")
                        .setMessage("Tem a certeza que pretende apagar esta Intervenção?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                ServicosManager.localIntervencoes.remove(intervencao);
                                ServicosManager.saveLocal(context);
                                Intent mIntent=new Intent();
                                setResult(RESULT_OK, mIntent);
                                finish();
                            }})
                        .setNegativeButton("NÃO", null).show();
            }
        });
        ViewCompat.setBackgroundTintList(deleteB, ContextCompat.getColorStateList(this, R.color.redi));
        generateTable();
        generateHoras();


    }

    private View.OnClickListener componentesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent( context,componentesActivity.class);
            i.putExtra("IntervencaoIndex",intervencaoIndex);
            i.putExtra("ServicoIndex",servicoIndex);
            i.putExtra("Mode",mode);
            startActivityForResult(i, 1);
        }
    };


    public void openProcedimentos(View v){
        Intent intent = new Intent(this, Procedimentos.class);
        //EditText editText = (EditText) findViewById(R.id.editText);F
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void generateHoras(){
        final Button horaEntrada = findViewById(R.id.HoraEntrada);
        String[] hourMin = intervencao.getHoraInicio().split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        String curTime = String.format("%02d:%02dh", hour, mins);
        horaEntrada.setText(curTime);
        System.out.println(intervencao.getHoraInicio());
        horaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String curTime = String.format("%02d:%02dh", sHour, sMinute);
                                SpannableString content = new SpannableString(curTime);
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                horaEntrada.setText(content);
                                intervencao.setHoraInicio(sHour + ":" + sMinute);

                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });


        final Button horaSaida = findViewById(R.id.HoraSaida);
        String[] hourMin2 = intervencao.getHoraInicio().split(":");
        int hour2 = Integer.parseInt(hourMin[0]);
        int mins2 = Integer.parseInt(hourMin[1]);
        String curTime2 = String.format("%02d:%02dh", hour2, mins2);
        horaSaida.setText(curTime2);
        horaSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                                String curTime = String.format("%02d:%02dh", sHour, sMinute);
                                SpannableString content = new SpannableString(curTime);
                                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                                horaSaida.setText(content);
                                intervencao.setHoraFim(sHour + ":" + sMinute);

                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });
    }

    public void generateTable(){
        int i = 0;
        for (TipoServico ts : servico.getTiposServico()){
            addTipoServicoRow(ts,i);
            i++;
        }
    }

    public void addTipoServicoRow(TipoServico ts, int index){


        /* Create a new row to be added. */
        TableRow tr = new TableRow(this);
        Button proc = new Button(this);
        proc.setTag(index);
        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer ind = (Integer) v.getTag();
                Intent i = new Intent( context,ProcedimentosActivity.class);
                i.putExtra("IntervencaoIndex",intervencaoIndex);
                i.putExtra("TipoServicoIndex", ind);
                i.putExtra("Mode",mode);
                i.putExtra("ServicoIndex", servicoIndex);
                startActivity(i);
            }
        });
        //ViewCompat.setBackgroundTintList(proc, ContextCompat.getColorStateList(this, R.color.redi));

        TextView equipNome = new TextView(this);
        equipNome.setText(ts.getEquipamento().getDesignacao());
        /* Find Tablelayout defined in main.xml */



        tr.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border) );

        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        /* Create a Button to be the row-content. */
        proc.setText("Ver");
        proc.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        equipNome.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        /* Add Button to row. */
        TextView texto = new TextView(this);
        equipNome.setTag(index);
        texto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        texto.setText(ts.getDesignacao());
        texto.setTag(index);
        texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onButtonShowPopupWindowClick(v);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                final View mView = getLayoutInflater().inflate(R.layout.activity_componente, null);
                int index = (int) v.getTag();
                TipoServico tSer = intervencao.getTiposServico().get(index);
                ImageView imagem =  mView.findViewById(R.id.Imagem);
                imagem.setVisibility(View.GONE);
                TextView designacao =  mView.findViewById(R.id.NomeText);
                TextView descricao =  mView.findViewById(R.id.Descricao);

                //imagem.setText("Nome: "+cliente.getNome());
                designacao.setText(tSer.getDesignacao());
                descricao.setText("Descrição: "+tSer.getDescricao());


                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                mBuilder.setView(mView);
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog = mBuilder.create();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog.show();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);

            }
        });


        equipNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onButtonShowPopupWindowClick(v);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(IntervencaoActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.activity_equipamento, null);
                int index = (int) v.getTag();
                Equipamento e = servico.getTiposServico().get(index).getEquipamento();
                TextView designacao = (TextView) mView.findViewById(R.id.designacao);
                TextView marca = (TextView) mView.findViewById(R.id.marca);
                TextView modelo = (TextView) mView.findViewById(R.id.modelo);
                TextView tmanutencao = (TextView) mView.findViewById(R.id.tmanutencao);
                TextView tipo = (TextView) mView.findViewById(R.id.tipo);
                Button estadoB = mView.findViewById(R.id.estadoButton);
                estadoB.setTag(index);
                estadoB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Integer ind = (Integer) v.getTag();
                        Intent i = new Intent( context,EstadosActivity.class);
                        i.putExtra("IntervencaoIndex",intervencaoIndex);
                        i.putExtra("TipoServicoIndex",ind);
                        i.putExtra("Mode",mode);
                        i.putExtra("ServicoIndex", servicoIndex);
                        startActivityForResult(i, 1);
                    }
                });





                final EditText dataGarantia = (EditText) mView.findViewById(R.id.dataGarantia);
                dataGarantia.setTag(e);
                dataGarantia.setGravity(Gravity.CENTER_HORIZONTAL);
                dataGarantia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);
                        // date picker dialog
                        picker = new DatePickerDialog(IntervencaoActivity.this,android.R.style.Theme_Holo_Light_Dialog,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                        dataGarantia.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        Equipamento eq = (Equipamento) v.getTag();
                                        eq.setDataGarantia(dataGarantia.getText().toString());
                                        ServicosManager.save(context);

                                    }
                                }, year, month, day);
                        picker.show();
                    }
                });
                final EditText anoFabrico = (EditText) mView.findViewById(R.id.anofabrico);
                anoFabrico.setInputType(InputType.TYPE_NULL);
                anoFabrico.setTag(e);
                anoFabrico.setGravity(Gravity.CENTER_HORIZONTAL);
                anoFabrico.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);

                        // date picker dialog
                        picker = new DatePickerDialog(IntervencaoActivity.this,android.R.style.Theme_Holo_Light_Dialog,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                        anoFabrico.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                        Equipamento eq = (Equipamento) v.getTag();
                                        eq.setAnoFabrico(anoFabrico.getText().toString());
                                        ServicosManager.save(context);
                                    }
                                }, year, month, day);
                        picker.show();

                    }
                });
                final EditText nSerie = (EditText) mView.findViewById(R.id.nSerie);
                nSerie.setTag(e);
                nSerie.setGravity(Gravity.CENTER_HORIZONTAL);
                nSerie.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {}
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Equipamento e = (Equipamento) nSerie.getTag();
                        try {
                            e.setnSerie(Integer.parseInt(nSerie.getText().toString()));
                            ServicosManager.save(context);
                        }
                        catch (NumberFormatException r) {
                            Toast.makeText(getApplicationContext(), "Insira um némero válido!", Toast.LENGTH_LONG).show();
                        }

                        System.out.println("OBS: "+e.getDataGarantia());
                    }
                });

                TextView rotina = (TextView) mView.findViewById(R.id.rotina);
                designacao.setText(e.getDesignacao());
                final ImageView imagem = (ImageView) mView.findViewById(R.id.imagem);
                imagem.setTag(index);
                if(e.getImagepath()!=null){
                    File f = new File(e.getImagepath());
                    Uri contentUri = Uri.fromFile(f);
                    try {
                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), contentUri);
                        imagem.setImageBitmap(imageBitmap);
                    }
                    catch (Exception ef){

                    }
                }

                imagem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, FSImageActivity.class);
                        intent.putExtra("ServicoIndex",servicoIndex);
                        int index = (Integer) v.getTag();
                        intent.putExtra("TipoServicoIndex",index);
                        startActivity(intent);

                    }
                });


                ImageButton photoB = (ImageButton) mView.findViewById(R.id.PhotoButton);
                photoB.setTag(imagem);
                photoB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchTakePictureIntent();
                        selectedImageView = (ImageView) v.getTag();
                    }
                });
                marca.setText(e.getMarca());
                modelo.setText(e.getModelo());
                tmanutencao.setText(e.gettManutencao());
                tipo.setText(e.getTipo());
                dataGarantia.setText(e.getDataGarantia());
                anoFabrico.setText(e.getAnoFabrico());
                if(e.getnSerie() != null){
                    nSerie.setText(e.getnSerie().toString());
                }
                nSerie.setText("");
                rotina.setText(e.getRotina());

                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                mBuilder.setView(mView);
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog = mBuilder.create();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                dialog.show();
                getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
            }
        });
        //texto.setTextColor(Color.BLACK);
        Space espaco = new Space(this);
        espaco.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.addView(texto);
        //tr.addView(espaco);

        tr.addView(equipNome);
        tr.addView(proc);

        int padding_in_dp = 8;  // 6 dps
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);

        tr.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
        /* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
        table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }


    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            seletectEquip.setImagem(imageBitmap);
            ServicosManager.servicos.get(0).getTiposServico().get(0).getEquipamento().setImagem(imageBitmap);
        }
    }

*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File f = new File(currentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);
                Bitmap fixedbm = fixImage(imageBitmap);
                selectedImageView.setImageBitmap(fixedbm);
                Integer tsindex = (Integer) selectedImageView.getTag();
                servico.getTiposServico().get(tsindex).getEquipamento().setImagepath(currentPhotoPath);
                ServicosManager.save(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap fixImage(Bitmap bitmap){

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    public void criaIntervencao() {

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending...");
        progressDialog.show();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        intervencao.setDataIntervencao(dateFormat.format(cal.getTime()));
        //defining the call
        intervencao.setStateSend();
        EditText trabalhoPrestado = (EditText) findViewById(R.id.TrabalhoPrestadoText);
        intervencao.setTrabalhoPrestado(trabalhoPrestado.getText().toString());
        intervencao.setIdFuncionario(ServicosManager.funcionarioID);
        ServicosManager.saveLocal(this);
        Call<Result> call = service.createintervencao(intervencao
        );

        //calling the apiq
        call.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                //displaying the message from the response as toast
                 Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().getError()) {
                    //starting profile activity
                    servico.getIntervencoes().add(intervencao);
                   // servico.addIntervencaoInfo()
                    ServicosManager.localIntervencoes.remove(intervencao);
                    ServicosManager.saveLocal(context);
                    ServicosManager.save(context);
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Intent mIntent=new Intent();
                    setResult(RESULT_OK, mIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                intervencao.setState('N');
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Intent mIntent=new Intent();
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });
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
        Intent intent = new Intent(this, IntervencoesActivity.class);

    }

}

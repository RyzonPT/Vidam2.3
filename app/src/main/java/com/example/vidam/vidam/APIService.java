package com.example.vidam.vidam;



import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    //The register call
    @FormUrlEncoded
    @POST("login")
    Call<Result> funcionariologgin(
            @Field("email") String email,
            @Field("password") String password);

    //The Intervencao call

    @POST("intervencao")
    Call<Result> createintervencao(@Body Intervencao intervencao);

    @FormUrlEncoded
    @POST("iProcedimento")
    Call<Result> sendIProcedimento(
            @Field("trabalhoPrestado") int idProcedimento,
            @Field("idServico") int idServico,
            @Field("idEquipamento") int idEquipamento,
            @Field("idIntervencao") int idIntervencao,
            @Field("obs") String obs,
            @Field("estado") int estado);

    @GET("aservicos")
    Call<List<Servico>> getActiveServicos();

    @GET("aintervencoes")
    Call<List<Intervencao>> getActiveIntervencoes();

    @GET("teste")
    Call<Result> getteste();

    @GET("aclientes")
    Call<List<Cliente>> getActiveClientes();

    @GET("intervencoes/{idServico}")
    Call<Intervencoes> getMessages(@Path("idServico") int idServico);


}

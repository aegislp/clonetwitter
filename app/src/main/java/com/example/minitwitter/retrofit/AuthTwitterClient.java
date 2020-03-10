package com.example.minitwitter.retrofit;

import com.example.minitwitter.common.Constantes;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthTwitterClient {

    private static AuthTwitterClient intance = null;
    private AuthTwitterService authTwitterService;
    private Retrofit retrofit;

    public AuthTwitterClient(){

        //incluid el token
        OkHttpClient.Builder okhttpClienteBuilder = new OkHttpClient.Builder();
        okhttpClienteBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient cleinte =  okhttpClienteBuilder.build();



        retrofit = new Retrofit.Builder().baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cleinte)
                .build();

        authTwitterService = retrofit.create(AuthTwitterService.class);
    }

    public static AuthTwitterClient getInstance(){
        if(intance == null){
            intance =  new AuthTwitterClient();
        }

        return  intance;
    }

    public AuthTwitterService getAuthTwitterService(){
        return authTwitterService;
    }

}

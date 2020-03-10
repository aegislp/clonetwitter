package com.example.minitwitter.retrofit;

import com.example.minitwitter.common.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient {

    private static MiniTwitterClient intance = null;
    private MiniTwitterService miniTwitterService;
    private Retrofit retrofit;

    public MiniTwitterClient(){

        retrofit = new Retrofit.Builder().baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }

    public static MiniTwitterClient getInstance(){
        if(intance == null){
            intance =  new MiniTwitterClient();
        }

        return  intance;
    }

    public MiniTwitterService getMiniTwitterService(){
        return miniTwitterService;
    }

}

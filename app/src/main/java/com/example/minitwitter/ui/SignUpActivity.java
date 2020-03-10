package com.example.minitwitter.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minitwitter.R;
import com.example.minitwitter.common.Constantes;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.example.minitwitter.retrofit.MiniTwitterService;
import com.example.minitwitter.retrofit.MiniTwitterClient;
import com.example.minitwitter.retrofit.request.RequestSignup;
import com.example.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity   {

    private TextView btnLogin;
    private Button btnSignup;
    private EditText etEmail, etPass,etUsername;
    private MiniTwitterClient miniTwitterClient;
    private MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        retrofitInit();
        findViews();
        setEvemtsListeners();


    }

    private void retrofitInit() {

        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }


    private void findViews() {

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignupSave);
        etEmail = findViewById(R.id.editTextSignUpEmail);
        etPass = findViewById(R.id.editTextSignUpPass);
        etUsername = findViewById(R.id.editTextSignupUsername);
    }

    private void setEvemtsListeners() {
        btnLogin.setOnClickListener((View v)->{
            startActivity(new Intent(getApplication(), MainActivity.class));
        });

        btnSignup.setOnClickListener((View v)-> this.doSignup(v));
    }

    private boolean isValid() {

        boolean res = true;

        if(etUsername.getText().toString().isEmpty()){
            etUsername.setError("Debe ingresar un nombre de usuario");
            res = res && false;
        }

        if(etEmail.getText().toString().isEmpty()){
            etEmail.setError("Debe ingresar un email");
            res = res && false;

        }

        if(etPass.getText().toString().isEmpty()){
            etPass.setError("Debe ingresar un password");
            res = res && false;
        }



        return res;


    }

    public void doSignup(View v){

        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String username = etUsername.getText().toString();

        if(this.isValid()){



            RequestSignup requestSignup = new RequestSignup(username,email,pass,"UDEMYANDROID");

            Call<ResponseAuth> call = miniTwitterService.doSignup(requestSignup);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Signuo ok", Toast.LENGTH_SHORT).show();

                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TOKEN,response.body().getToken());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_USERNAME,response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_EMAIL,response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PHOTO,response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CREATED,response.body().getCreated());
                        SharedPreferencesManager.setSomeBooleanValue(Constantes.PREF_ACTIVE,response.body().getActive());

                        startActivity(new Intent(SignUpActivity.this, DashboardActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SignUpActivity.this,"Algo anduvo mal reitenta",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this,"Problema de conexion ...reitenta",Toast.LENGTH_LONG).show();

                }
            });




        }

    }


}

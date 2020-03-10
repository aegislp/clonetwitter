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
import com.example.minitwitter.retrofit.request.RequestLogin;
import com.example.minitwitter.retrofit.response.ResponseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView linkSignUp;
    private EditText etUser, etPass;
    private Button btnLogin;
    private MiniTwitterClient miniTwitterClient;
    private MiniTwitterService miniTwitterService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        retrofitInit();
        findViews();
        setListeners();
    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    private void findViews() {
        linkSignUp = findViewById(R.id.btnLogin);
        etUser = findViewById(R.id.editTextLoginEmail);
        etPass = findViewById(R.id.editTextLoginPass);
        btnLogin = findViewById(R.id.btnDoLogin);
    }

    private void setListeners() {

        btnLogin.setOnClickListener(this);

        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplication(), SignUpActivity.class);
                startActivity(signUpIntent);

            }
        });

    }


    ///do de login
    @Override
    public void onClick(View v) {
        String email = etUser.getText().toString();
        String pass = etPass.getText().toString();

        if(email.isEmpty()){
            etUser.setError("Debe ingresar un email");
        }else if(pass.isEmpty()){
            etPass.setError("Debe ingresar un el password");
        }else{
            RequestLogin requestLogin = new RequestLogin(email,pass);
            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Login COrrecto", Toast.LENGTH_SHORT).show();
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TOKEN,response.body().getToken());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_USERNAME,response.body().getUsername());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_EMAIL,response.body().getEmail());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PHOTO,response.body().getPhotoUrl());
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CREATED,response.body().getCreated());
                        SharedPreferencesManager.setSomeBooleanValue(Constantes.PREF_ACTIVE,response.body().getActive());

                        Intent dashboardIntent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(dashboardIntent);
                        finish();

                    }else{
                        Toast.makeText(MainActivity.this, "Problemas en el login", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Problemas con la conexion", Toast.LENGTH_SHORT).show();
                }
            });
        }





    }
}

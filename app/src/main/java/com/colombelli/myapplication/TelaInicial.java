package com.colombelli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
    }


    public void calendario(View view) {

        Intent abrirCalendario = new Intent(this, CalendarioActivitie.class);
        startActivity(abrirCalendario);

    }

}

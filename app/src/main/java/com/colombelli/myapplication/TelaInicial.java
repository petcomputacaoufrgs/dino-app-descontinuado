package com.colombelli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }


    public void abreAgenda(View view){

        Intent abrirAgenda = new Intent(this, AgendaActivities.class);
        startActivity(abrirAgenda);
    }
}

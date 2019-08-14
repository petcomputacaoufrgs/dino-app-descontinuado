package com.colombelli.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**CÓDIGO EM CONSTRUÇÃO - AINDA SEM DOCUMENTAÇÃO*/

public class EventoActivities extends AppCompatActivity {


    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_activities);

        Intent intent = getIntent();
        Evento selectedEvent = (Evento) intent.getSerializableExtra("Evento");

        this.evento = selectedEvent;

        setupScreenTexts(this.evento);

        }

    private void setupScreenTexts(Evento evento){

        EditText tipo = (EditText)findViewById(R.id.EventoTIPO);

        EditText data = (EditText)findViewById(R.id.EventoDATA);
        EditText hora = (EditText)findViewById(R.id.EventoHORA);
        EditText anotac = (EditText)findViewById(R.id.EventoANOTAC);

        tipo.setText(evento.getTipo());
        data.setText(evento.getData());
        hora.setText(evento.getHora());
        anotac.setText(evento.getAnotacoes());

    }

    public void voltarAgenda(View view){

        Intent abrirAgenda = new Intent(this, AgendaActivities.class);
        startActivity(abrirAgenda);
    }

    public void enviarEdicao(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        EditText tipo = (EditText)findViewById(R.id.EventoTIPO);
        EditText data = (EditText)findViewById(R.id.EventoDATA);
        EditText hora = (EditText)findViewById(R.id.EventoHORA);
        EditText anotacoes = (EditText)findViewById(R.id.EventoANOTAC);

        this.evento.setTipo(tipo.getText().toString());
        this.evento.setData(data.getText().toString());
        this.evento.setHora(hora.getText().toString());
        this.evento.setAnotacoes(anotacoes.getText().toString());

        String key = this.evento.getKey();

        myRef.child("eventos").child(key).setValue(evento);

        Toast.makeText(getApplicationContext(), "Evento alterado com sucesso", Toast.LENGTH_LONG).show();

    }


}












package com.colombelli.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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

        TextView tipo = (TextView)findViewById(R.id.EventoTIPO);
        TextView data = (TextView)findViewById(R.id.EventoDATA);
        TextView hora = (TextView)findViewById(R.id.EventoHORA);
        TextView anotac = (TextView)findViewById(R.id.EventoANOTAC);

        tipo.setText(evento.getTipo());
        data.setText(evento.getData());
        hora.setText(evento.getHora());
        anotac.setText(evento.getAnotacoes());

    }

    public void voltarAgenda(View view){

        Intent abrirAgenda = new Intent(this, AgendaActivities.class);
        startActivity(abrirAgenda);
    }


}












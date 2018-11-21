package com.colombelli.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;


public class agenda extends AppCompatActivity {


    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);


        setupEventViews();
        setupListView();

    }


    private void setupEventViews(){


        listview = (ListView)findViewById(R.id.AgendaListView);


    }

    private void setupListView(){


        dbControllerAgenda crud = new dbControllerAgenda(getBaseContext());
        Cursor cursor = crud.carregarEventos();

        String[] nomeCampos = new String[] {dbCreationAgenda.DATA,dbCreationAgenda.DIA,dbCreationAgenda.MES,dbCreationAgenda.ANO,dbCreationAgenda.HORA,dbCreationAgenda.TIPO};
        int[] idList = new int[] {R.id.data,R.id.dia,R.id.mes,R.id.ano,R.id.hora,R.id.tipoepa};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.agendal_view_single_item,cursor,nomeCampos,idList);

        listview.setAdapter(adaptador);



        /*
        String[] datas = getResources().getStringArray(R.array.datastest);
        String[] dias = getResources().getStringArray(R.array.diastest);
        String[] meses = getResources().getStringArray(R.array.mesestest);
        String[] anos = getResources().getStringArray(R.array.anostest);
        String[] horas = getResources().getStringArray(R.array.horastest);
        String[] tipos = getResources().getStringArray(R.array.tipostest);


        SimpleAdapter simpleadapter = new SimpleAdapter(this, datas,dias,meses,anos,horas,tipos);

        listview.setAdapter(simpleadapter);


*/


    }



    public void abreCalendario(View view){

        Intent abrirCalendario = new Intent(this, CalendarioActivitie.class);
        startActivity(abrirCalendario);
    }



    /* TEM QUE ASSISTIR UMAS VIDEO AULAS PRA SACAR O QUE TÁ ACONTENDO A PARTIR DAQUI!!! */

    /*Classe que serve pra "adaptar" o layout separado criado para cada um dos
     itens da lista de eventos, dentro da list view presente nessa tela.

     ADAPTAÇÃO OCORRE EM setupListView();

      */
    public class SimpleAdapter extends BaseAdapter{


        private Context mContext;
        private LayoutInflater layoutInflater;
        private String[] dataArray;
        private String[] diaArray;
        private String[] mesArray;
        private String[] anoArray;
        private String[] horaArray;
        private String[] tipoArray;

        public SimpleAdapter(Context context, String[] data, String[] dia, String[] mes, String[] ano, String[] hora, String[] tipo){

            mContext=context;
            dataArray=data;
            diaArray=dia;
            mesArray=mes;
            anoArray=ano;
            horaArray=hora;
            tipoArray=tipo;

            layoutInflater= LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return dataArray.length;
        }

        @Override
        public Object getItem(int position) {
            return dataArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= layoutInflater.inflate(R.layout.agendal_view_single_item,null);
            }

            TextView data= (TextView) convertView.findViewById(R.id.data);
            TextView dia= (TextView) convertView.findViewById(R.id.dia);
            TextView mes= (TextView) convertView.findViewById(R.id.mes);
            TextView ano=  (TextView) convertView.findViewById(R.id.ano);
            TextView hora= (TextView) convertView.findViewById(R.id.hora);
            TextView tipo= (TextView) convertView.findViewById(R.id.tipoepa);


            data.setText(dataArray[position]);
            dia.setText(diaArray[position]);
            mes.setText((mesArray[position]));
            ano.setText(anoArray[position]);
            hora.setText(horaArray[position]);
            tipo.setText(tipoArray[position]);

            return convertView;

        }
    }



    public class SimpleCursorAdapter extends android.widget.SimpleCursorAdapter{

        public SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
        }
    }






}









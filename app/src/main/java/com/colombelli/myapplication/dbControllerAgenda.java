package com.colombelli.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class dbControllerAgenda {

    private SQLiteDatabase db;
    private dbCreationAgenda banco;


    public dbControllerAgenda(Context context){
        banco = new dbCreationAgenda(context);

    }



    public String adicionarEvento(String Data, String Dia, String Mes, String Ano, String Hora, String Tipo, String Anotacoes){

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(dbCreationAgenda.DATA, Data);
        valores.put(dbCreationAgenda.DIA, Dia);
        valores.put(dbCreationAgenda.MES, Mes);
        valores.put(dbCreationAgenda.ANO, Ano);
        valores.put(dbCreationAgenda.HORA, Hora);
        valores.put(dbCreationAgenda.TIPO, Tipo);
        valores.put(dbCreationAgenda.ANOTACOES, Anotacoes);

        //Grava coisas no banco e fecha ele em seguida:
        resultado = db.insert(dbCreationAgenda.TABELA,null, valores);
        db.close();

        if(resultado == -1)
            return "Erro ao gravar dados.";
        else {
            return "Evento Adicionado com Sucesso!";
        }
    }


    public Cursor carregarEventos(){

        Cursor cursor;
        String[] campos = new String[]{banco.ID,banco.DATA,banco.DIA,banco.MES,banco.ANO,banco.HORA,banco.TIPO,banco.ANOTACOES};
        db = banco.getReadableDatabase();

        cursor = db.query(banco.TABELA,campos,null, null,null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();
        return cursor;


    }









}




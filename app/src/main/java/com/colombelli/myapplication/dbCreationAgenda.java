package com.colombelli.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbCreationAgenda extends SQLiteOpenHelper {


    //THAT WAS >ALL< SUPPOSED TO BE PRIVATE but i live life fast

    public static final String NOME_BANCO = "bdAgenda.db";
    public static final String TABELA = "eventos";
    public static final String ID = "_id";
    public static final String DATA = "data";
    public static final String DIA = "dia";
    public static final String MES = "mes";
    public static final String ANO = "ano";
    public static final String HORA = "hora";
    public static final String TIPO = "tipo";
    public static final String ANOTACOES = "anotacoes";
    public static final int VERSAOBANCO = 1;


    public dbCreationAgenda(Context context) {
        super(context, NOME_BANCO, null, VERSAOBANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DATA + " TEXT,"
                + DIA + " TEXT,"
                + MES + " TEXT,"
                + ANO + " TEXT,"
                + HORA + " TEXT,"
                + TIPO + " TEXT,"
                + ANOTACOES + " TEXT"
                + ")";

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);

    }

}









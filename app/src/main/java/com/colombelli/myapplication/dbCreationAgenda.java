package com.colombelli.myapplication;

/**
 * Esta classe tem como único objetivo estruturar o banco de dados local que usaremos na Agenda.
 * Quando a classe CONTROLLER for operar sobre o banco de dados, uma instância de dbCreationAgenda
 * será instanciada para criar o banco de dados de acordo com a organização aqui descrita, com
 * os nomes de cada coluna da tabela, junto com o tipo de dado que é gravado em cada um.
 *
 * TOOL UTILIZADOS:
 *  SQLite   -> https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
 *
 * @author Henrique Barboza (theevilharry)
 * PET Computação UFRGS
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbCreationAgenda extends SQLiteOpenHelper {


    /**INICIALMENTE,CRIAMOS OS NOMES DAS COLUNAS DA TABELA*/

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


    /**
     * Método construtor de classe. Nada aqui foi mudado do default.
     * */
    public dbCreationAgenda(Context context) {
        super(context, NOME_BANCO, null, VERSAOBANCO);
    }

    /**
     * Método usado para criar a descrição em SQL do nosso banco de dados.
     * Um String com os comandos SQL de criação da tabela é criado a partir da união do comando SQL
     * CREATE TABLE, com o nome da Tabela, e com o nome de cada uma das colunas da mesma, seguidas
     * dos seus respectivos tipos de Dados.
     *
     * Note que apenas a ID é um número inteiro. Motivo: Economizar espaço da memória.
     * */
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

        /*ABAIXO, EXECUTAMOS O COMANDO SQL DESCRITO NO STRING ACIMA: */
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);

    }

}









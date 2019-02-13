package com.colombelli.myapplication;

/**
 * Esta classe é responsável por ser quem executa todas as operações no banco de dados.
 * Ou seja, qualquer outra classe que deseje receber, enviar ou alterar informações do banco,
 * precisarão criar uma instância desta classe, e operar através dela.
 *
 * TOOL UTILIZADOS:
 *  SQLite   -> https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
 *
 * @author Henrique Barboza (theevilharry)
 * PET Computação UFRGS
 */
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


    /**
     * Este método é responsável por gravar um novo evento no banco de dados, ele segue o
     * algoritmo padrão de inserção de dados no SQLite, que pode ser encontrado
     * aqui -> https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
     *
     * O Método retorna um String com a resposta da gravação no banco.
     *
     * */
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


    /**
     * Este método é responsável por carregar todos os dados em um banco de dados. Ele segue o
     * algoritmo padrão de leitura do SQLite usando Cursores.
     * Ver mais em -> https://developer.android.com/reference/android/database/Cursor
     *
     * */
    public Cursor carregarEventos(){

        Cursor cursor;

        //Definição do array de strings com os nomes de todas as informações do objeto a serem
        //coletadas.
        String[] campos = new String[]{banco.ID,banco.DATA,banco.DIA,banco.MES,banco.ANO,banco.HORA,banco.TIPO,banco.ANOTACOES};

        db = banco.getReadableDatabase(); //Carrega dados do banco.

        // Posiciona um objeto tipo Cursor para apontar para uma busca no banco, com os campos
        // sendo enviados.
        cursor = db.query(banco.TABELA,campos,null, null,null,null, null,null);

        if(cursor != null){
            cursor.moveToFirst(); //Se cursor não é nulo, move cursor para primeiro objeto.
        }

        db.close(); //Fecha o banco de dados.
        return cursor;


    }

    /**
     * Este método é responsável por deletar o evento relativo ao ID passado como parâmetro
     * Ver mais em -> https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
     *
     * */
    public void deletarEvento(int id){

        String where = banco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(banco.TABELA,where,null);
        db.close();

    }






}




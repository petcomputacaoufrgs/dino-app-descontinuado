package com.colombelli.myapplication;

/**
 * Essa classe tem como objetivo controlar a tela "AGENDA", que lista os eventos agendados pelo
 * usuário. Ela recebe os dados do BD através da classe CONTROLLER, e organiza-os para serem
 * distribuidos para o ListView nos arquivos Layout, onde são exibidos ao usuário.
 *
 * Muitos comentários no decorrer do código para ir esclarecendo o fluxo de dados.
 *
 * TOOLS UTILIZADOS:
 * ListView - Utilitário ANDROID Studio - Layout pronto para visualizar listas de objetos.
 *          -> https://www.androidpro.com.br/blog/desenvolvimento-android/listviews/
 * ArrayLists - Utilitário JAVA - Array com métodos próprios para manutenção de listas de objetos,
 *              com alocação dinâmica de dados.
 *          -> https://www.devmedia.com.br/explorando-a-classe-arraylist-no-java/24298
 * SQLite   -> https://www.devmedia.com.br/criando-um-crud-com-android-studio-e-sqlite/32815
 *
 * @author Henrique Barboza (theevilharry)
 * PET Computação UFRGS
 */

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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import android.database.Cursor;
import android.widget.SimpleCursorAdapter; // Usado apenas para testes de versão anterior.
import android.widget.AdapterView;

public class AgendaActivities extends AppCompatActivity {


    private ListView listview;          //- ListView para onde mandaremos os dados do banco no Layout.
    private ArrayList<Evento> eventos; // - Estrutura de dados que guarda os dados do banco antes de
                                      // irem para a tela do usuário.

    /**
     * onCreate é o método a ser executado na criação da atividade. Como é de costume, sempre
     * linkamos aqui o arquivo Layout a representar esta tela. Além disso, no mesmo método,
     * inicializamos os atributos da classe e montamos a ListView em dois métodos separados.
     *
     * Override sobrescreve o método onCreate padrão que toda nova atividade implicitamente tem.
     * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        setupEventViews();
        setupListView();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),EventoActivities.class);
                intent.putExtra("Evento", eventos.get(position));
                startActivity(intent);

            }
        });

    }

    /**
     * Este método inicializa os atributos da classe AgendaActivities, que são o ArrayList de
     * eventos carregados do Banco de Dados (Apenas a inicialização (alocamento de espaço) do
     * ArrayList é feito aqui), e a variável ListView que precisa ser linkada ao
     * ListView do arquivo XML de Layout.
     * */
    private void setupEventViews(){

        this.eventos = new ArrayList<Evento>();

        listview = (ListView)findViewById(R.id.AgendaListView);


    }

    /**
     * Este método é responsável por montar a ListView do Layout, com os dados do Banco de Dados
     * (agora podendo ser organizados da maneira que desejarmos)e o Layout de Item Único que
     * cada elemento da lista deve respeitar.
     * */
    private void setupListView(){

        /** LOTS OF SHIT GOING ON HERE, SO TRY TO KEEP UP WITH THE COMMENTS */

        //Declaração de vetores que serão enviados para o ListView no Layout.
        ArrayList<String> datas = new ArrayList<String>();
        ArrayList<String> dias = new ArrayList<String>();
        ArrayList<String> meses = new ArrayList<String>();
        ArrayList<String> anos = new ArrayList<String>();
        ArrayList<String> horas = new ArrayList<String>();
        ArrayList<String> tipos = new ArrayList<String>();

        /** CRIA-SE NOVA INSTÂNCIA DO CONTROLLER DO BANCO DE DADOS, PORQUE
           PRECISAMOS LER OS DADOS DO BANCO */

        dbControllerAgenda crud = new dbControllerAgenda(getBaseContext());
        Cursor cursor = crud.carregarEventos();


        /** COM OS DADOS DO BANCO DE DADOS COLOCADOS EM eventosNoBanco, PODEMOS FAZER O QUE QUISER
           COM A LISTA DE EVENTOS MARCADOS ANTES DE MOSTRAR ELA PARA O USUÁRIO.
           CHAMAMOS A constructList PARA MONTAR UMA ARRAYLIST QUE POSSUI TODOS OS ELEMENTOS
           PRESENTES NO BANCO DE DADOS, EXTRAÍDOS DE cursor. */

        ArrayList<Evento> eventosNoBanco = constructList(cursor);

        // Ordena eventos utilizando um compare personalizado para obejtos do tipo Evento (comparadorEventos)
        Collections.sort(eventosNoBanco, Evento.comparadorEventos);

        this.eventos = eventosNoBanco; // Gravamos no ArrayList de eventos da classe, os eventos do banco.

        //Percorre o ArrayList para preencher os vetores que serão enviados para
        //o ListView no Layout.
        for(int x = 0; x<this.eventos.size();x++)
        {
                datas.add(this.eventos.get(x).getData());
                dias.add(Integer.toString(this.eventos.get(x).getDia()));
                meses.add(Integer.toString(this.eventos.get(x).getMes()));
                anos.add(Integer.toString(this.eventos.get(x).getAno()));
                horas.add(this.eventos.get(x).getHora());
                tipos.add(this.eventos.get(x).getTipo());
        }

        //EXECUTAMOS O ADAPTER COM OS DADOS SELECIONADOS.
        SimpleAdapter adaptador = new SimpleAdapter(this,datas,dias,meses,anos,horas,tipos, this.eventos.size());
        listview.setAdapter(adaptador);

        }


    /**
     * Este método recebe um objeto do tipo Cursor (que, é o que recebe os dados do banco de dados),
     * e percorre o mesmo retornando os dados em Cursor em um ArrayList de objetos do tipo Evento.
     *
     * */
    private ArrayList<Evento> constructList(Cursor cursor){

        //Inicializamos ArrayList que retornaremos.
        ArrayList<Evento> eventList = new ArrayList<Evento>();


        // SE O CURSOR POSSUI INSTÂNCIAS DE DADOS
        if (cursor.moveToFirst()){

            do{
                Evento evento = new Evento(); //Criamos um novo Evento

                /**
                 * E PREENCHEMOS O ARRAYLIST CARREGANDO OS DADOS RETIRADOS
                 * DO BANCO EM CURSOR PARA UM NOVO ELEMENTO DO TIPO EVENTO
                 * QUE É POSTERIORMENTE ADICIONADO NA LISTA.
                 *
                 * Note que para tal, devemos ter conhecimento de como os
                 * dados estão organizados dentro do banco de dados. Precisamos
                 * ter em mente a ordem dos campos para acessar o index de cada
                 * coluna da tabela separadamente. Para saber tal, basta ler o código
                 * em dbCreationAgenda, onde a tabela do banco de dados é criada.
                 * */

                evento.setId(cursor.getInt(0));
                evento.setData(cursor.getString(1));
                evento.setDia(Integer.parseInt(cursor.getString(2)));
                evento.setMes(Integer.parseInt(cursor.getString(3)));
                evento.setAno(Integer.parseInt(cursor.getString(4)));
                evento.setHora(cursor.getString(5));
                evento.setTipo(cursor.getString(6));
                evento.setAnotacoes(cursor.getString(7));

                eventList.add(evento); //Por fim, o novo evento é adicionado a lista.

            }while(cursor.moveToNext());
        }
        return eventList;
}

// LEMBRETE: COMER O CU DE QUEM TÁ LENDO



    /**
     * Este método tem a simples ação de iniciar a atividade especifica para quando o usuário
     * deseja adicionar um novo evento à AgendaActivities.
     * */
    public void abreCalendario(View view){

        Intent abrirCalendario = new Intent(this, CalendarioActivitie.class);
        startActivity(abrirCalendario);
    }



    /** TEM QUE ASSISTIR UMAS VIDEO AULAS PRA SACAR O QUE TÁ ACONTENDO A PARTIR DAQUI!!!

     Abaixo, classe que serve pra "adaptar" o layout separado criado para cada um dos
     itens da lista de eventos, dentro da list view presente nessa tela.
     Para entender melhor a lógica no código, veja este tutorial de um indiano adaptando um layout
     para um aplicativo android com ListView (https://www.youtube.com/watch?v=Qg3L_B9--zY&t=1409s)

     ADAPTAÇÃO OCORRE EM setupListView();
     Repare que o que encontra-se abaixo é uma Classe, e não um método. Uma classe dentro da classe
     AgendaActivities. Não precisa estar fora e pode/deve operar apenas dentro de AgendaActivities.

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

        public SimpleAdapter(Context context, ArrayList<String> data, ArrayList<String> dia,  ArrayList<String> mes,  ArrayList<String> ano,  ArrayList<String> hora,  ArrayList<String> tipo, int tamanho){


            /** COMO ESSE É O MÉTODO CONSTRUTOR DA CLASSE (Chamado sempre que inicializamos uma instância da mesma),
             *  DEVEMOS INICIALIZAR OS ATRIBUTOS DA CLASSE
             *  SENÃO VAI DAR TUDO ERRADO. MOTIVO PELO QUAL ENVIAMOS O TAMANHO DA LISTA COMO
             *  UM DOS PARÂMETROS.*/

            dataArray = new String[tamanho];
            diaArray = new String[tamanho];;
            mesArray = new String[tamanho];;
            anoArray = new String[tamanho];;
            horaArray = new String[tamanho];;
            tipoArray = new String[tamanho];;


            /** AGORA PEGAMOS OS ARRAYLISTS COM O CONTEÚDO A SER MOSTRADO, E ASSOCIAMOS ELES
             * AOS ARRAYS DE STRING QUE SÃO ATRIBUTOS DESSA CLASSE ADAPTER. */

            mContext=context;
            dataArray= data.toArray(dataArray);
            diaArray= dia.toArray(diaArray);
            mesArray=mes.toArray(mesArray);
            anoArray=ano.toArray(anoArray);
            horaArray=hora.toArray(horaArray);
            tipoArray=tipo.toArray(tipoArray);

            layoutInflater= LayoutInflater.from(context);
        }

        /** IGNORE ABAIXO */
        @Override
        public int getCount() {
            return dataArray.length;
        }
        /** IGNORE ABAIXO */
        @Override
        public Object getItem(int position) {
            return dataArray[position];
        }
        /** IGNORE ABAIXO */
        @Override
        public long getItemId(int position) {
            return position;
        }
        /** AQUI ACONTECE PARTE DA MÁGICA */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= layoutInflater.inflate(R.layout.agendal_view_single_item,null);
            }

            /** INICIALIZAMOS VARIÁVEIS TIPO TextView E ASSOCIAMOS ELAS AOS TEXTVIEWS DO ARQUIVO
             *  LAYOUT DE ITEM ÚNICO NA LISTA. */
            TextView data= (TextView) convertView.findViewById(R.id.data);
            TextView dia= (TextView) convertView.findViewById(R.id.dia);
            TextView mes= (TextView) convertView.findViewById(R.id.mes);
            TextView ano=  (TextView) convertView.findViewById(R.id.ano);
            TextView hora= (TextView) convertView.findViewById(R.id.hora);
            TextView tipo= (TextView) convertView.findViewById(R.id.tipoepa);

            /** E FINALMENTE, ENVIAMOS AS VARIÁVEIS QUE AGORA POSSUEM OS DADOS DO BANCO,
             *  PARA OS TEXTVIEWS DO LAYOUT A SER EXIBIDO PARA O USUÁRIO. */
            data.setText(dataArray[position]);
            dia.setText(diaArray[position]);
            mes.setText((mesArray[position]));
            ano.setText(anoArray[position]);
            hora.setText(horaArray[position]);
            tipo.setText(tipoArray[position]);

            return convertView;

        }
    }

    /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ A PARTIR DAQUI,
     * ENCONTRAM-SE MÉTODOS PARA ENVIAR DADOS DO BANCO PARA UM LISTVIEW, DIRETO DO
     * CURSOR, SEM ANTES PASSÁ-LOS POR UMA ESTRUTURA DE DADOS. Código escrito na versão anterior
     * para fins de teste de funcionalidades do código, e deixado aqui para fins de aprendizagem.
     * >>>NADA ABAIXO ESTÁ ATUALMENTE SENDO USADO NO ALGORITMO.<<<*/

    public class SimpleCursorAdapter extends android.widget.SimpleCursorAdapter{

        public SimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
            super(context, layout, c, from, to);
        }
    }

    private void setupListViewFromCursor(){


        dbControllerAgenda crud = new dbControllerAgenda(getBaseContext());
        Cursor cursor = crud.carregarEventos();

        this.eventos = constructList(cursor);

        String[] nomeCampos = new String[] {dbCreationAgenda.DATA,dbCreationAgenda.DIA,dbCreationAgenda.MES,dbCreationAgenda.ANO,dbCreationAgenda.HORA,dbCreationAgenda.TIPO};
        int[] idList = new int[] {R.id.data,R.id.dia,R.id.mes,R.id.ano,R.id.hora,R.id.tipoepa};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.agendal_view_single_item,cursor,nomeCampos,idList);

        listview.setAdapter(adaptador);



    }






}









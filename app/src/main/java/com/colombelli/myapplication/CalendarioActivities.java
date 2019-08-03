package com.colombelli.myapplication;

/**
 * O objetivo desta classe é controlar as atividades da tela de Adicionar Novo Evento à AgendaActivities,
 * incluindo operações da seleção de data através de um CalendarView, organização dos dados
 * selecionados mostrados na tela, e envio deles para o CONTROLLER efetuar a gravação no
 * banco de dados.
 *
 * TOOLS UTILIZADOS
 * CalendarView - UTILITÁRIO ANDROID STUDIO - Um Layout pronto de um Calendário com métodos prontos
 *                de operações em cima do mesmo.
 *               -> https://developer.android.com/reference/android/widget/CalendarView
 *               -> (Tutorial Para Pegar Data) https://www.youtube.com/watch?v=hHjFIG0TtA0
 *
 * @author Henrique Barboza (theevilharry)
 * PET Computação UFRGS
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalendarioActivities extends AppCompatActivity {

    //Atributo do tipo CalendarView que representará o Calendário no Layout
    private CalendarView mCalendarView;

    //Variáveis para guardar a data
    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;
    private Button hourButton;
    private TextView dataNaInterface; //Textview para representar o local do Layout onde mostramos
                                     // a data selecionada pelo usuário antes de salvarmos.

    private String dataEscrita; //Strings Auxiliares.
    int hour, h_minute;
    private TextView hourText;
    private String mesEscrito;
    private int dialogID = 0;

    protected TimePickerDialog.OnTimeSetListener pimepickerlistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            h_minute = minute;
            String horaString = String.format("%02d : %02d", hour, h_minute);
            hourText.setText(horaString);
            Toast.makeText(CalendarioActivities.this,hour + " : " + h_minute, Toast.LENGTH_LONG).show();
        }
    };


    /**
     * onCreate é uma função que dita o que deve ser feito no momento que uma classe é criada.
     * Toda Activity é linkada ao seu arquivo XML Layout nessa função, assim como outras operações
     * importantes são feitas no caso da atividade atual.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_activities);




        //Associamos nossas variávies de TextView e de CalendarView aos seus respectivos
        //representantes no arquivo Layout.
        dataNaInterface = (TextView)findViewById(R.id.datacompleta); // Relaciona TextView do layout com a instância da classe.
        mCalendarView = (CalendarView)findViewById(R.id.calendario); //Relaciona calendário do arquivo layout com a instância

        //Ativa-se o Listener que recebe notificações de alteração no objeto CalendarView
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            /**
             * A função abaixo é padrão do CalendarView, responsável por descrever o que deve ser
             * feito quando uma data é selecionada em um CalendarView. O procedimento default seria
             * não acontecer nada. Usando Override, podemos sobrescrever o procedimento default
             * como faremos abaixo.
             *
             * */
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            //SALVA DATA SELECIONADA NAS VARIÁVEIS DA CLASSE:
            selectedDay = dayOfMonth;
            selectedMonth = month + 1; //NO ANDROID STUDIO, 0 = JANEIRO E 11 = DEZEMBRO
            selectedYear = year;

            //SELECIONA O STRING DO MÊS SELECIONADO:
            switch(selectedMonth){

                case 1:
                    mesEscrito = "Janeiro";
                    break;
                case 2:
                    mesEscrito = "Fevereiro";
                    break;
                case 3:
                    mesEscrito= "Março";
                    break;
                case 4:
                    mesEscrito= "Abril";
                    break;
                case 5:
                    mesEscrito= "Maio";
                    break;
                case 6:
                    mesEscrito= "Junho";
                    break;
                case 7:
                    mesEscrito= "Julho";
                    break;
                case 8:
                    mesEscrito= "Agosto";
                    break;
                case 9:
                    mesEscrito= "Setembro";
                    break;
                case 10:
                    mesEscrito= "Outubro";
                    break;
                case 11:
                    mesEscrito= "Novembro";
                    break;
                case 12:
                    mesEscrito= "Dezembro";
                    break;
                default:
                    break;
                    }

            //ORGANIZA STRING DA DATA QUE SERÁ EXIBIDO NA TELA E ENVIA STRING PARA EXIBIÇÃO:

            dataEscrita = selectedDay + " de " + mesEscrito + " de " + selectedYear;
            dataNaInterface.setText(dataEscrita);

            }
        });

        escolherHora();

    }

        public void escolherHora(){

            hourText = (TextView) findViewById(R.id.horaView);

            hourText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(dialogID);
                }
            });


        }

        @Override
        protected Dialog onCreateDialog(int id){

            if(id == dialogID){
                return new TimePickerDialog(CalendarioActivities.this, pimepickerlistener,hour,h_minute,false );
            }
            return null;


        }



        /**
         * Este método é ativado quando o usuário clica no botão de Salvar Novo Evento na AgendaActivities.
         * Ele recebe os strings de texto que o usuário digitou, prepara-os e envia-os para o
         * CONTROLLER adicioná-los ao banco de dados. No final, ele retorna para a tela AgendaActivities.
         *
         * */
        public void gravarEvento(View v){
            Evento evento = new Evento();
            //criamos referencia ao banco

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();


            // Linkamos variáveis tipo EditText com os textos digitados pelo usuário no Layout.
            EditText Hora = (EditText)findViewById(R.id.hora);
            EditText Tipo = (EditText)findViewById(R.id.tipo);
            EditText Anotacoes = (EditText)findViewById(R.id.editText6);

            //ORGANIZAMOS TODOS OS DADOS E CONVERTEMOS ELES PARA STRINGS
            String dataString = selectedDay + "/" + selectedMonth + "/" + selectedYear;
            String diaString = String.valueOf(selectedDay);
            String mesString = String.valueOf(selectedMonth);
            String anoString = String.valueOf(selectedYear);
            String horaString = String.format("%02d : %02d",
                    hour,
                    h_minute);

            String tipoString = Tipo.getText().toString();
            String anotacoesString = Anotacoes.getText().toString();

            evento.setData(dataString);
            evento.setDia(selectedDay);
            evento.setMes(selectedMonth);
            evento.setAno(selectedYear);
            evento.setHora(horaString);
            evento.setTipo(tipoString);
            evento.setAnotacoes(anotacoesString);


            //Teste de conversão:
           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            try{
                evento.setDateVariable(formatter.parse(dataString));
            }
            catch(ParseException e){
                Log.d("PARSER", "didn't work lol");
            }

            String key = myRef.child("eventos").push().getKey(); //cria o id unico para o evento novo

            myRef.child("eventos").child(key).setValue(evento); //insere o evento novo com esse id

            //Adicionamos o Novo Evento ao banco de dados, e checamos resultado da operação.
            String resultado = evento.toString();

            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

            Log.d("TEST", resultado);

            //Voltamos para a tela da AgendaActivities
            finish();


        }














}

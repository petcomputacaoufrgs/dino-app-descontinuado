package com.colombelli.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarioActivitie extends AppCompatActivity {


    private CalendarView mCalendarView;

    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;

    private TextView dataNaInterface;
    private String dataEscrita;
    private String mesEscrito;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_activitie);


        dataNaInterface = (TextView)findViewById(R.id.datacompleta); // Relaciona TextView do layout com a instância da classe.

        mCalendarView = (CalendarView)findViewById(R.id.calendario); //Relaciona calendário do arquivo layout com a instância

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            //SALVA DATA SELECIONADA NAS VARIÁVEIS DA CLASSE:
            selectedDay = dayOfMonth;
            selectedMonth = month +1; //NO ANDROID STUDIO, 0 = JANEIRO E 11 = DEZEMBRO
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
        }

        //FUNÇÃO ABAIXO DEVIA ESTAR EM dbControllerAgenda mas aqui ia ser mais fácil de testar.
        public void gravarEvento(View v){

            dbControllerAgenda crud = new dbControllerAgenda(getBaseContext());
            EditText Hora = (EditText)findViewById(R.id.editText4);
            EditText Tipo = (EditText)findViewById(R.id.editText5);
            EditText Anotacoes = (EditText)findViewById(R.id.editText6);


            String DataString = selectedDay + "/" + selectedMonth + "/" + selectedYear;
            String DiaString = String.valueOf(selectedDay);
            String MesString = String.valueOf(selectedMonth);
            String AnoString = String.valueOf(selectedYear);
            String HoraString = Hora.getText().toString();
            String TipoString = Tipo.getText().toString();
            String AnotacoesString = Anotacoes.getText().toString();
            String resultado;

            resultado = crud.adicionarEvento(DataString,DiaString,MesString,AnoString,HoraString,TipoString,AnotacoesString);

            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();

            Log.d("TEST", resultado);

            Intent voltarAgenda = new Intent(this, agenda.class);
            startActivity(voltarAgenda);


        }














}

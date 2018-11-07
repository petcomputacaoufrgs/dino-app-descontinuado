package com.colombelli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class CalendarioActivitie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_activitie);
    }


    public void agendarConsulta(View view) {

            Calendar calendarEvent = Calendar.getInstance();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", calendarEvent.getTimeInMillis());
            intent.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
            intent.putExtra("title", "Sample Event");
            intent.putExtra("allDay", true);
            intent.putExtra("rule", "FREQ=YEARLY");
            startActivity(intent);
    }

}

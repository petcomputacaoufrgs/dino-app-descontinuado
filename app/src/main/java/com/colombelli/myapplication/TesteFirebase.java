package com.colombelli.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.*;

public class TesteFirebase extends AppCompatActivity {


    private DatabaseReference mDatabase;

    private Button bSendText;
    private static final String TAG = "TesteFirebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_firebase);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        bSendText = (Button) findViewById(R.id.addString);

        bSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui escreve
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("message");
                bSendText.setText("ativado");
                myRef.setValue("Hello, World!");

            }
        });

    }



}



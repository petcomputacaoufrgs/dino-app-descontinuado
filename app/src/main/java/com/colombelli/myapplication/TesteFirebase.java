package com.colombelli.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.*;

import java.util.Map;

public class TesteFirebase extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private DatabaseReference mPostReference;
    private Evento ribasfest = new Evento();

    private Button bSendText;
    private static final String TAG = "TesteFirebase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_firebase);


        bSendText = (Button) findViewById(R.id.addString);

        bSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui escreve

                bSendText.setText("ativado");
                basicReadWrite();
            }
        });

        ribasfest.setAno(2030);
        ribasfest.setNome("Ribasfeist");
        ribasfest.setAnotacoes("formula 1 EXTREMO!!!");
    }

    public void basicReadWrite() {
        mPostReference = FirebaseDatabase.getInstance().getReference().child("message");
        //myRef.child("moofofo").setValue("Nao aparece");
        //myRef.child("message").setValue("Aparece");
        String key = myRef.child("eventos").push().getKey();
        myRef.child("eventos").child(key).setValue(ribasfest); //talvez n funcione sei la
        // [START read_message]
        // Read from the database

        ValueEventListener testeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> eventMap = (Map<String,Object>)dataSnapshot.getValue();
                //String value = dataSnapshot.getValue(String.class);
                for (Map.Entry<String, Object> entry : eventMap.entrySet()) {
                    Log.d(TAG, entry.getKey() + "/" + entry.getValue().toString());
                }
                Log.d(TAG, "Value is: lit bruhhh moment " + "value");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        myRef.child("eventos").addValueEventListener(testeListener);
        // [END read_message]
    }
}







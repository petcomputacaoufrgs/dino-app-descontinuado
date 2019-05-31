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


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private DatabaseReference mPostReference;

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

    }

    public void basicReadWrite() {
        mPostReference = FirebaseDatabase.getInstance().getReference().child("message");
        myRef.child("moofofo").setValue("Nao aparece");
        myRef.child("message").setValue("Aparece");
        // [START read_message]
        // Read from the database

        ValueEventListener testeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: lit bruhhh moment " + value );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        myRef.child("message").addValueEventListener(testeListener);
        // [END read_message]
    }
}







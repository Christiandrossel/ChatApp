package com.dude.cronoschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Chat extends AppCompatActivity {

    TextView textView;
    EditText nachricht;
    FirebaseDatabase meineDatenbank;

    String UsernameText;

    TextView userNameTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Intent intent = getIntent();
        UsernameText = getIntent().getExtras().getString("UserName");

        userNameTextView = (TextView) findViewById(R.id.textViewName);
        userNameTextView.setText("Dein Name: "+ UsernameText);

        textView = (TextView) findViewById(R.id.textView);
        nachricht = (EditText) findViewById(R.id.editMsg);

        textView.setMovementMethod(new ScrollingMovementMethod());

        meineDatenbank = FirebaseDatabase.getInstance();
        DatabaseReference reference = meineDatenbank.getReference("nachricht");

        //reference.child("msg1").setValue("Hallo ich bin da");

        DatabaseReference ref2 = meineDatenbank.getReference("nachricht/msg1");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String empfangenerText = dataSnapshot.getValue(String.class);
                SpannableString spannable = new SpannableString(empfangenerText);
                Selection.setSelection(spannable, spannable.length());
                textView.setText(spannable, TextView.BufferType.SPANNABLE);
                //textView.setText(empfangenerText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void senden(View view){
        String alteNachrichten = (String) textView.getText();

        String baueNachricht = alteNachrichten + UsernameText + ": " + nachricht.getText() + "\n";

        DatabaseReference reference = meineDatenbank.getReference("nachricht");
        reference.child("msg1").setValue(baueNachricht);

        nachricht.setText("");
    }
}

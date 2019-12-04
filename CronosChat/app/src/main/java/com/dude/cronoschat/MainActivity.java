package com.dude.cronoschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText UserName;
    Button buttonLogin;
    FirebaseDatabase meineDatenbank;
    String UserNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        UserName = (EditText) findViewById(R.id.UserName);
        UserNameString = readName();
        UserName.setText(UserNameString);
    }

    private String readName(){
        String name;
        SharedPreferences pref = getSharedPreferences("CronosChat", 0);
        name = pref.getString("UserName", "GAST");
        return name;
    }

    private void saveName(String name){
        SharedPreferences pref = getSharedPreferences("CronosChat", 0);
        SharedPreferences.Editor ed = pref.edit();
        ed.putString("UserName", name);
        ed.commit();
    }

    public void loginButtonClicked(View view){
        //UserNameString = String.valueOf(UserName.getText());
        UserNameString = UserName.getText().toString();

        saveName(UserNameString);

        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("UserName", UserNameString);
        startActivity(intent);

    }
}

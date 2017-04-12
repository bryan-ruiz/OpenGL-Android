package com.example.bryan.projectopengl;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    EditText etEmail, etPassword;
    BDHelper MyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDB = new BDHelper(this);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        Button signIn = (Button) findViewById(R.id.button);
        Button RU = (Button) findViewById(R.id.buttonRU);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        RU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        etEmail.setText("");
        etPassword.setText("");
        super.onResume();
    }

    public void login(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        Cursor loguedIn = MyDB.getUserData(email, password);
        if(loguedIn.moveToFirst()) {
            Toast.makeText(MainActivity.this,"Welcome: " + loguedIn.getString(1),Toast.LENGTH_LONG).show();
            User user = User.getInstance();
            user.UserFillInformation(loguedIn.getString(0), loguedIn.getString(1), loguedIn.getString(2), loguedIn.getString(3));
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            if (!loguedIn.isClosed()) {
                loguedIn.close();
            }
        }else{
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }
}

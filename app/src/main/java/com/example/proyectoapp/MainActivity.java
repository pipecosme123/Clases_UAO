package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void irAinicioSesion(View view){
        Intent i = new Intent(MainActivity.this, IniciarSesion.class);
        startActivity(i);
    }
}
package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class consultarCurso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_curso);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Crear Curso");
    }

}
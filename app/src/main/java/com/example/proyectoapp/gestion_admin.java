package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import android.content.Intent;

public class gestion_admin extends AppCompatActivity {

    private Button btCursos, btDocentes, btAlumnos, btNovedades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_admin);

        btCursos = (Button) findViewById(R.id.gestionAdmin_btCursos);
        btDocentes = (Button) findViewById(R.id.gestionAdmin_btDocentes);
        btAlumnos = (Button) findViewById(R.id.gestionAdmin_btEstudaintes);
        btNovedades = (Button) findViewById(R.id.gestionAdmin_Novedades);
    }

    public void irACursos(View view){
        Intent i = new Intent(gestion_admin.this, gestion_cursosAdmin.class);
        startActivity(i);
    }

    public void irADocentes(View view){
        Intent i = new Intent(gestion_admin.this, Gestion_DocentesAdmin.class);
        startActivity(i);
    }
}   
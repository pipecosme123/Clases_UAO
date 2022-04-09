package com.example.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ModificarDocente extends AppCompatActivity {
    private EditText EtNombre, EtApellido, EtCedula, EtFacultad, EtPrograma, EtCurso, EtContraseña;
    private Button BtEliminar, BtModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Modificar docentes");
    }
}
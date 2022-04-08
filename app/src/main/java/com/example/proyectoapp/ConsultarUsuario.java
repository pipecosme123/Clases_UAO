package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.HardwareRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConsultarUsuario extends AppCompatActivity {
    private EditText edBuscador;
    private ListView lvAllUser;
    private String tipoUser;
    private ArrayList<usuarios> listaUsuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        edBuscador = (EditText)findViewById(R.id.consultarUser_etBuscar);
        lvAllUser = (ListView)findViewById(R.id.ConsultarUser_lvUsuarios);
        tipoUser = getIntent().getStringExtra("TipoUsuario");
        listaUsuarios = new ArrayList<usuarios>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Consultar Usuarios");

        new ConsultarUsuario.TraerUsuarios(ConsultarUsuario.this).execute();
    }

    private ArrayList<usuarios> consultar() throws JSONException, IOException {

        String url = Constants.URL + "claseUAO/get-by-All-Users.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("tipo", tipoUser.trim())); // pasamos el id al servicio php

        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webservice
        Log.d("key of the message", "--------------------------" + json);
        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio
            JSONArray json_array = object.optJSONArray("user");// accedemos al objeto json llamado multas
            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                ArrayList<usuarios> listaUsu = new ArrayList<usuarios>();
               for(int i = 0;i<json_array.length();i++){
                   usuarios user = new usuarios(json_array.getJSONObject(i));// instanciamos la clase multa para obtener los datos json
                   Log.d("key of the message", "The message " + user);
                   listaUsu.add(user);
               }
               return listaUsu;
              //  return user;// retornamos la multa
            }
            return null;
        }
        return null;
    }

    class TraerUsuarios extends AsyncTask<String, String, String> {
        private Activity context;

        TraerUsuarios(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            try {
                listaUsuarios = consultar();
                //  Toast.makeText(MainActivity.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
                if (listaUsuarios != null)
                    context.runOnUiThread(new Runnable() {


                        @Override
                        public void run() {




                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Multa no encontrada", Toast.LENGTH_LONG).show();

                        }
                    });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

public class EstudiantesCursos_Frag extends Fragment {
    private TextView listaUsers;
    private ArrayList<usuarios> listaUsuarios;
    private ArrayList<String> listaInfo;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_estudiantes_cursos_frag, null);
        listaUsers = (TextView) view.findViewById(R.id.cursosFrag_lvUsers);
        listaUsuarios = new ArrayList<usuarios>();
        listaInfo = new ArrayList<String>();
        new EstudiantesCursos_Frag.TraerUsuarios(getActivity()).execute();
        return view;
    }

    private ArrayList<usuarios> consultar() throws JSONException, IOException {

        String url = Constants.URL + "claseUAO/getUsers.php"; // Ruta

        //DATOS
        List<NameValuePair> nameValuePairs; // lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(1);//definimos array
        nameValuePairs.add(new BasicNameValuePair("tipo", "Estudiante")); // pasamos el id al servicio php



        String json = APIHandler.POSTRESPONSE(url, nameValuePairs); // creamos var json que se le asocia la respuesta del webservice

        Log.d("key of the message", "--------------------------" + json);

        if (json != null) { // si la respuesta no es vacia
            JSONObject object = new JSONObject(json); // creamos el objeto json que recorrera el servicio

            JSONArray json_array = object.optJSONArray("user");// accedemos al objeto json llamado multas
            JSONArray jArray = new JSONArray();

            if (json_array.length() > 0) { // si lo encontrado tiene al menos un registro
                ArrayList<usuarios> listaUsu = new ArrayList<usuarios>();
                listaInfo=new ArrayList<String>();
                listaUsuarios=new ArrayList<usuarios>();
                for(int i = 0;i<json_array.length();i++){
                    usuarios user = new usuarios(json_array.getJSONObject(i));// instanciamos la clase multa para obtener los datos json
                    String userInfo = user.getNombre() + " " + user.getApellidos();
                    listaInfo.add(userInfo);
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

                            llenarListView();
                        }
                    });
                else
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_LONG).show();

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

    public void llenarListView(){
    String StringUsuarios ="";
        for(int i = 0;i<listaInfo.size();i++){
            StringUsuarios += listaInfo.get(i)+"\n";
    }
        listaUsers.setText(StringUsuarios);
    }

}
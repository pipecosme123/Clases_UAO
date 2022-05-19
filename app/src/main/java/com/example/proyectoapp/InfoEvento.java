package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.widget.TextView;

public class InfoEvento extends AppCompatActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    private TextView titulo,fecha,hora,lugar;
    private String latitud,longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_evento);
        titulo = (TextView)findViewById(R.id.infoEvento_tvNombre);
        fecha = (TextView)findViewById(R.id.infoEvento_tvFecha);
        lugar = (TextView)findViewById(R.id.infoEvento_tvLugar);
        hora = (TextView)findViewById(R.id.infoEvento_tvHora);




        titulo.setText(this.getIntent().getStringExtra("ENombre"));
        fecha.setText(this.getIntent().getStringExtra("EFecha"));
        lugar.setText(this.getIntent().getStringExtra("ELugar"));
        hora.setText(this.getIntent().getStringExtra("EHora"));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String lat=this.getIntent().getStringExtra("ELatitud");
        String lon =this.getIntent().getStringExtra("ELongitud");
        Double longitud = Double.parseDouble(lon);
        Double latitud = Double.parseDouble(lat);
        // Add a marker in Sydney and move the camera
        LatLng san_fernando = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(san_fernando).title("Sede san Fernando").snippet("Esta es la sede número 2 de la UAO"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(san_fernando));


    }
}
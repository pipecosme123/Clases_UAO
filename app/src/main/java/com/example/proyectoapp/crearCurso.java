package com.example.proyectoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class crearCurso extends AppCompatActivity {
    //Nombre del curso
    EditText nombreCurso;
    //Descripcion del curso
    EditText descripcionCurso;
    //Duracion
    EditText DuracionCurso;
    //Horario
    TextView resultDia;
    CheckBox lunes, martes, miercoles, jueves, viernes;
    EditText horaInicio, horaFin;
    //PARCIALES
    //private int mYear, mMonth, mDay;
    EditText prParcial, seParcial, teParcial;
    Calendar calendar;
    //NOVEADES
    CheckBox novedades;
    String estado;

    //INSERTAR A BASE DE DATOS
    Button btnInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_curso);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Crear Curso");
        //NOMBRE CURSO
        nombreCurso = (EditText) findViewById(R.id.EtNCurso);
        //DESCRIPCION CURSO
        descripcionCurso = (EditText) findViewById(R.id.EtDescripcionCurso);
        //Duracion Curso
        DuracionCurso = findViewById(R.id.EtDuracionCurso);
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();
        DuracionCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        DuracionCurso.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });

        //HORARIO
        //seleccion de dia
        resultDia = findViewById(R.id.TvResultDia);
        lunes = (CheckBox) findViewById(R.id.CBLunes);
        martes = (CheckBox) findViewById(R.id.CBmartes);
        miercoles = (CheckBox) findViewById(R.id.CBMiercoles);
        jueves = (CheckBox) findViewById(R.id.CBJueves);
        viernes = (CheckBox) findViewById(R.id.CBViernes);
        updateRadioGroup(lunes);
        //seleccion de hora
        horaInicio = findViewById(R.id.EtHoraInicio);
        horaFin = findViewById(R.id.EtHoraFin);
        horaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(crearCurso.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        horaInicio.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });
        horaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(crearCurso.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        horaFin.setText(hourOfDay + ":" + minutes);
                    }
                }, 0, 0, false);
                timePickerDialog.show();
            }
        });

        //PARCIALES
        prParcial = findViewById(R.id.EtParcial1);
        seParcial = findViewById(R.id.EtParcial2);
        teParcial = findViewById(R.id.EtParcial3);
        Calendar calendar = Calendar.getInstance();
        //1
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateCalendar();
            }

            private void updateCalendar() {
                String format = "MM/dd/yy";
                SimpleDateFormat format1 = new SimpleDateFormat(format, Locale.US);
                prParcial.setText(format1.format(calendar.getTime()));
            }
        };
        prParcial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(crearCurso.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //2
        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateCalendar();
            }

            private void updateCalendar() {
                String format = "MM/dd/yy";
                SimpleDateFormat format1 = new SimpleDateFormat(format, Locale.US);
                seParcial.setText(format1.format(calendar.getTime()));
            }
        };
        seParcial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(crearCurso.this, date2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //3
        DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateCalendar();
            }

            private void updateCalendar() {
                String format = "MM/dd/yy";
                SimpleDateFormat format1 = new SimpleDateFormat(format, Locale.US);
                teParcial.setText(format1.format(calendar.getTime()));
            }
        };
        teParcial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(crearCurso.this, date3, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Novedades
        novedades = (CheckBox) findViewById(R.id.CBNovedades);
        novedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (novedades.isChecked()) {
                    estado = "1";
                } else {
                    estado = "0";
                }
            }
        });

        //INSERTAR
        btnInsertar = findViewById(R.id.btnAnadirCurso);
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((!nombreCurso.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!DuracionCurso.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!resultDia.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!horaInicio.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!horaFin.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!prParcial.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!seParcial.getText().toString().trim().equalsIgnoreCase("")) ||
                        (!teParcial.getText().toString().trim().equalsIgnoreCase("")) ||
                        (estado == "0")) {

                    new Insertar(crearCurso.this).execute();
                } else {

                    Toast.makeText(crearCurso.this, "Hay informacion por llenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        if (lunes.isChecked()) {
            resultDia.setText(lunes.getText());
            updateRadioGroup(lunes);
            martes.setEnabled(false);
            miercoles.setEnabled(false);
            jueves.setEnabled(false);
            viernes.setEnabled(false);
        } else if (martes.isChecked()) {
            resultDia.setText(martes.getText());
            updateRadioGroup(martes);
            lunes.setEnabled(false);
            miercoles.setEnabled(false);
            jueves.setEnabled(false);
            viernes.setEnabled(false);
        } else if (miercoles.isChecked()) {
            resultDia.setText(miercoles.getText());
            updateRadioGroup(miercoles);
            martes.setEnabled(false);
            lunes.setEnabled(false);
            jueves.setEnabled(false);
            viernes.setEnabled(false);
        } else if (jueves.isChecked()) {
            resultDia.setText(jueves.getText());
            updateRadioGroup(jueves);
            martes.setEnabled(false);
            miercoles.setEnabled(false);
            lunes.setEnabled(false);
            viernes.setEnabled(false);
        } else if (viernes.isChecked()) {
            resultDia.setText(viernes.getText());
            updateRadioGroup(viernes);
            martes.setEnabled(false);
            miercoles.setEnabled(false);
            jueves.setEnabled(false);
            lunes.setEnabled(false);
        } else {
            resultDia.setText("Escoga un dia");
            lunes.setEnabled(true);
            martes.setEnabled(true);
            miercoles.setEnabled(true);
            jueves.setEnabled(true);
            viernes.setEnabled(true);
            lunes.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
            martes.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
            miercoles.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
            jueves.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
            viernes.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
        }
    }

    private void updateRadioGroup(CheckBox selected) {

        lunes.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
        martes.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
        miercoles.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
        jueves.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
        viernes.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_group_background));
        //sabado.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.radio_group_background));

        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.radio_on));
    }

    private boolean insertar() {
        String url = Constants.URL + "claseUAO/insert_curso.php";
        //DATOS
        List<NameValuePair> nameValuePairs; // definimos la lista de datos
        nameValuePairs = new ArrayList<NameValuePair>(10); // tamaño del array
        nameValuePairs.add(new BasicNameValuePair("nombre", nombreCurso.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("descripcion", descripcionCurso.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("duracion", DuracionCurso.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("dia", resultDia.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("horaInico", horaInicio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("horaFin", horaInicio.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("primParcial", prParcial.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("segParcial", seParcial.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("terParcial", teParcial.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("novedades", estado));
        boolean response = APIHandler.POST(url, nameValuePairs);
        return response;
    }

    class Insertar extends AsyncTask<String, String, String> {
        private Activity context;

        Insertar(Activity context) {
            this.context = context;
        }

        protected String doInBackground(String... params) {
            if (insertar())
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Curso insertado", Toast.LENGTH_LONG).show();
                        nombreCurso.setText("");
                        descripcionCurso.setText("");
                        DuracionCurso.setText("");
                        resultDia.setText("");
                        horaInicio.setText("");
                        horaFin.setText("");
                        prParcial.setText("");
                        seParcial.setText("");
                        teParcial.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Curso no insertado", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}



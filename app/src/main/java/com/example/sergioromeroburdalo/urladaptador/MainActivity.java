package com.example.sergioromeroburdalo.urladaptador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listaDatos;
    ArrayList<Datos> lista;
    EditText etURL, etTitulo, etBorrar;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDatos = findViewById(R.id.lvURL);

        etURL = findViewById(R.id.etURL);
        etTitulo = findViewById(R.id.etTitulo);
        etBorrar = findViewById(R.id.etBorrar);

        btnGuardar = findViewById(R.id.btnGuardar);

        lista=new ArrayList<Datos>();

        lista.add(new Datos(1, "Google", "http://www.google.com/"));
        lista.add(new Datos(2, "As", "http://www.as.com/"));
        lista.add(new Datos(3, "Facebook", "http://www.facebook.com/"));
        lista.add(new Datos(4, "SkyScanner", "http://www.SkyScanner.com/"));


        Adaptador miAdaptador = new Adaptador(getApplicationContext(), lista);

        listaDatos.setAdapter(miAdaptador);



    }

    public void ocultarTeclado(View v) {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void meterNuevo(View v) {
        String titu, direc;

        titu = etTitulo.getText().toString();
        direc = etURL.getText().toString();

        lista.add(new Datos(lista.size()+1, titu,"http://" + direc + "/"));

        Adaptador miAdaptador = new Adaptador(getApplicationContext(), lista);

        listaDatos.setAdapter(miAdaptador);

    }

    public void borrarElemento(View v){

        int titus;
        titus = Integer.valueOf(etBorrar.getText().toString())-1;
        if(lista.size()<titus+1)
        {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "ERROR, numero invalido", Toast.LENGTH_SHORT);

            toast1.show();
        }else {
            lista.remove(titus);
        }
        Adaptador miAdaptador = new Adaptador(getApplicationContext(), lista);

        listaDatos.setAdapter(miAdaptador);

    }
}

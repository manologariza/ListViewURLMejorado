package com.example.sergioromeroburdalo.urladaptador;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    int posicion;

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

        listaDatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                registerForContextMenu(view);
                posicion=i;

                return false;
            }
        });



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

        actualizarVistaListView();
    }

    public void borrarElemento(View v){

        int titus;
        titus = Integer.valueOf(etBorrar.getText().toString())-1;
        if(lista.size()<titus+1)
        {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),"ERROR, numero invalido", Toast.LENGTH_SHORT);
            toast1.show();
        }else {
            lista.remove(titus);
        }

        reordenarNumeros();
    }


    public void borrarElemento(int pos){
        lista.remove(pos);
        reordenarNumeros();

    }

    public void actualizarVistaListView(){
        Adaptador miAdaptador = new Adaptador(getApplicationContext(), lista);
        listaDatos.setAdapter(miAdaptador);
    }

    public void reordenarNumeros(){
        ArrayList <Datos> listaAux=new ArrayList<Datos>();
        for(int i=0; i<lista.size(); i++)
            listaAux.add(new Datos(i+1, lista.get(i).getNombre(), lista.get(i).getURL()));

        lista=listaAux;
        //Una vez reordenado el ArrayList de datos, llamo a la actulización de ListView para que se muestre el cambio
        actualizarVistaListView();
    }


    public void visitar(int pos){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lista.get(pos).getURL()));
        startActivity(intent);
    }

    public void subirElemento(int pos){
        if(pos != 0){
            Datos registroAux=new Datos(lista.get(pos).getId(), lista.get(pos).getNombre(), lista.get(pos).getURL());
            lista.set(pos, lista.get(pos-1));
            lista.set(pos-1, registroAux);
        }
        else
            Toast.makeText(getApplicationContext(), "No puede subir más la URL", Toast.LENGTH_LONG).show();

        reordenarNumeros();
    }

    public void bajarElemento(int pos){
        if(pos!=lista.size()-1){
            Datos registroAux=new Datos(lista.get(pos).getId(), lista.get(pos).getNombre(), lista.get(pos).getURL());
            lista.set(pos, lista.get(pos+1));
            lista.set(pos+1, registroAux);
        }
        else
            Toast.makeText(getApplicationContext(), "No puede bajar más la URL", Toast.LENGTH_LONG).show();

        reordenarNumeros();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.setHeaderTitle("Elija que desa hacer:");
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemVisitar:
                visitar(posicion);
                break;
            case R.id.itemEliminar:
                borrarElemento(posicion);
                break;
            case R.id.itemSubir:
                subirElemento(posicion);
                break;
            case R.id.itemBajar:
                bajarElemento(posicion);
                break;

        }
        return true;
    }

}

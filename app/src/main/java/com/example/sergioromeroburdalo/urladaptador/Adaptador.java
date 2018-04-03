package com.example.sergioromeroburdalo.urladaptador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergioromeroburdalo on 20/3/18.
 */

public class Adaptador extends BaseAdapter {

    Context contexto; //contexto de la aplicacion
    List<Datos> ListaObjetos; //lista de datos a generar. Podemos usar tb un ArrayList
    Datos dt ;

    public Adaptador(Context contexto, List<Datos> listaObjetos) {
        this.contexto = contexto;
        ListaObjetos = listaObjetos;
    }

    @Override
    public int getCount() {
        return ListaObjetos.size();
    }

    @Override
    public Object getItem(int i) {
        return ListaObjetos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ListaObjetos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista=view;
        LayoutInflater inflate = LayoutInflater.from(contexto);
        vista=inflate.inflate(R.layout.itemlist, null);


        TextView titulo=(TextView)vista.findViewById(R.id.tvNombre);
        final TextView detalle=(TextView)vista.findViewById(R.id.tvURL);



        titulo.setText(ListaObjetos.get(i).getNombre().toString());
        detalle.setText(ListaObjetos.get(i).getURL().toString());


        detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(detalle.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                contexto.startActivity(intent);
            }
        });

        return vista;
    }
}

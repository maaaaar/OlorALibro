package com.example.arant.olor_a_libro.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arant.olor_a_libro.Clases.ClaseLibreria;
import com.example.arant.olor_a_libro.R;

import java.util.ArrayList;

public class AdaptadorLibrerias extends ArrayAdapter<ClaseLibreria> {
    private ArrayList<ClaseLibreria>libro;
    private Context context;
    private TextView texto;
    private TextView texto2;


    public AdaptadorLibrerias(Context context, ArrayList<ClaseLibreria> libro) {
        super(context, R.layout.activity_librerias, libro);
        this.libro=libro;
        this.context=context;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.activity_librerias, null);

        texto = (TextView) item.findViewById(R.id.TextoTituloL1);
        texto2 = (TextView) item.findViewById(R.id.TextoTituloL2);

        texto.setText(this.libro.get(position).getUbicacion());
        texto2.setText(this.libro.get(position).getTitulo());


        return (item);
    }

}

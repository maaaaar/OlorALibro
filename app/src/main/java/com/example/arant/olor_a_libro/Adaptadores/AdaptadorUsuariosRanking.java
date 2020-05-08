package com.example.arant.olor_a_libro.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.arant.olor_a_libro.Clases.ClaseUsuarioActividades;
import com.example.arant.olor_a_libro.R;

import java.util.ArrayList;

public class AdaptadorUsuariosRanking extends ArrayAdapter {
    private Context context;
    private ArrayList<ClaseUsuarioActividades> usuarios;
    private String correo;

    public AdaptadorUsuariosRanking(Context context, ArrayList<ClaseUsuarioActividades> usuarios, String correo){

        super(context, R.layout.rellenoactividades, usuarios);
        this.context = context;
        this.usuarios = usuarios;
        this.correo = correo;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.rellenoactividades, null);

        ImageView imatge = (ImageView)item.findViewById(R.id.imatge);
        imatge.setImageResource(R.mipmap.user);

        TextView textView = (TextView) item.findViewById(R.id.nom);


        TextView textView1 = (TextView) item.findViewById(R.id.tema);

        /*Canvia el text si es l'usuari*/
        if (correo.equals(usuarios.get(position).getCorreo())){
            textView1.setText("TU PUNTUACIÓN: " +Integer.toString(usuarios.get(position).getPuntuacion()));
        }
        else {
            textView1.setText("Puntos: "+Integer.toString(usuarios.get(position).getPuntuacion()));
        }

        /*Canvia el text depenent de la posicio*/
        switch (position){
            case 0:
                textView.setText("Primero: "+ usuarios.get(position).getNombre());
                break;
            case 1:
                textView.setText("Segundo: "+usuarios.get(position).getNombre());
                break;
            case 2:
                textView.setText("Tercero: "+usuarios.get(position).getNombre());
                break;
                default:
                    textView.setText(usuarios.get(position).getNombre());
                    break;
        }

        return (item);
    }
}

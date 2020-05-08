package com.example.arant.olor_a_libro.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arant.olor_a_libro.Clases.ClaseActivitats;
import com.example.arant.olor_a_libro.R;

import java.util.ArrayList;

public class AdaptadorActividades extends ArrayAdapter {
    private Context context;
    private ArrayList<ClaseActivitats> activitats;

    public AdaptadorActividades(Context context, ArrayList<ClaseActivitats> activitats){

        super(context, R.layout.rellenoactividades, activitats);
        this.context = context;
        this.activitats = activitats;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.rellenoactividades, null);

        ImageView imatge = (ImageView)item.findViewById(R.id.imatge);
        imatge.setImageResource(R.mipmap.reliquias_muerte_blanc);

        TextView textView = (TextView) item.findViewById(R.id.nom);
        textView.setText(activitats.get(position).getNombre());

        TextView textView1 = (TextView) item.findViewById(R.id.tema);
        textView1.setText(activitats.get(position).getTemas().get(0).toString());

        return (item);
    }

}

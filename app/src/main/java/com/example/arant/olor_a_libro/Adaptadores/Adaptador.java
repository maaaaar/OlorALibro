package com.example.arant.olor_a_libro.Adaptadores;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arant.olor_a_libro.Clases.ClaseMenu;
import com.example.arant.olor_a_libro.R;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private Context context;
    private ArrayList<ClaseMenu> listitems;

    public Adaptador(Context context, ArrayList<ClaseMenu> listitems) {
        this.context = context;
        this.listitems = listitems;
    }

    @Override
    public int getCount() {
        return listitems.size();//array
    }

    @Override
    public Object getItem(int position) {
        return listitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClaseMenu Item= (ClaseMenu) getItem(position);

        convertView=LayoutInflater.from(context).inflate(R.layout.activity_olor_alibro,null);
        ImageView ImgFoto=convertView.findViewById(R.id.nombrefoto);
        TextView TextoMenu=convertView.findViewById(R.id.TextoTitulo);

        ImgFoto.setImageResource(Item.getFoto());
        TextoMenu.setText(Item.getTextoMenu());
        return convertView;
    }
}

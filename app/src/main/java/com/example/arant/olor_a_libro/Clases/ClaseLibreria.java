package com.example.arant.olor_a_libro.Clases;

import java.io.Serializable;

public class ClaseLibreria implements Serializable {
    String foto;
    String nombre;
    String direccion;
    int telefono;
    String nombreContacto;
    String descripcion;



    public String toString()
    {
        return "Libreria{" +
                "foto='" + foto + '\'' +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono=" + telefono +
                ", nombreContacto='" + nombreContacto + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }



    public String getTitulo() {
        return nombre;
    }

    public String getUbicacion() {
        return direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public ClaseLibreria(String foto, String titulo, String ubicacion, int telefono, String nombreContacto, String descripcion) {
        this.foto=foto;
        nombre = titulo;
        direccion = ubicacion;
        this.telefono = telefono;
        this.nombreContacto = nombreContacto;
        this.descripcion = descripcion;
    }
}


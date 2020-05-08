package com.example.arant.olor_a_libro.Clases;

import java.util.ArrayList;

public class ClaseActivitats {


         private String nombre;
        private String fechaInicio;
        private String fechaFinal;
        private boolean categorias;
        private ArrayList temas;
        private String descripcion;
        private ArrayList llibrerias;
        private int inscritos;


        @Override
        public String toString()
        {
            return "Actividad{" +
                    "nombre='" + nombre + '\'' +
                    ", fechaInicio='" + fechaInicio + '\'' +
                    ", fechaFinal='" + fechaFinal + '\'' +
                    ", categorias=" + categorias +
                    ", temas='" + temas + '\'' +
                    '}';
        }

        public ClaseActivitats(String nombre, String fechaInicio, String fechaFinal, boolean categorias, ArrayList temas, String descripcion, ArrayList llibrerias, int inscritos)
        {
            this.nombre = nombre;
            this.fechaInicio = fechaInicio;
            this.fechaFinal = fechaFinal;
            this.categorias = categorias;
            this.temas = temas;
            this.descripcion = descripcion;
            this.llibrerias = llibrerias;
            this.inscritos = inscritos;
        }
    public ClaseActivitats(String nombre, String fechaInicio, String fechaFinal, boolean categorias, ArrayList temas, String descripcion, ArrayList llibrerias)
    {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.categorias = categorias;
        this.temas = temas;
        this.descripcion = descripcion;
        this.llibrerias = llibrerias;

    }
    public ClaseActivitats(){

    }


        public ClaseActivitats(String nombre) {
            this.nombre = nombre;
        }
        public String getNombre()
        {
            return nombre;
        }

        public String getFechaInicio()
        {
            return fechaInicio;
        }

        public String getFechaFinal()
        {
            return fechaFinal;
        }

        public boolean isCategorias()
        {
            return categorias;
        }

        public ArrayList getTemas()
        {
            return temas;
        }

        public String getDescripcion()
        {
            return descripcion;
        }

        public ArrayList getLlibrerias()
        {
            return llibrerias;
        }

        public int getInscritos()
        {
            return inscritos;
        }
    }



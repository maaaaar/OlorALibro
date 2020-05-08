package com.example.arant.olor_a_libro.Clases;

import java.util.ArrayList;

public class ClaseUsuario {


        private String nombre;
        private int edad;
        private int codigopostal;
        private String correo;
        private String contraseña;
        private int puntuacion;


    @Override
    public String toString() {
        return "ClaseUsuario{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", codigopostal=" + codigopostal +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }

    public ClaseUsuario(String nombre, int edad, int codigopostal, String correo, String contraseña)
        {
            this.nombre = nombre;
            this.edad = edad;
            this.codigopostal = codigopostal;
            this.correo = correo;
            this.contraseña = contraseña;
            this.puntuacion = 0;
        }

        public  ClaseUsuario(){}
        /*GETTERS*/

        public String getNombre()
        {
            return nombre;
        }

        public int getEdad()
        {
            return edad;
        }

        public int getCodigopostal()
        {
            return codigopostal;
        }

        public String getCorreo()
        {
            return correo;
        }

        public String getContraseña()
        {
            return contraseña;
        }

        public int getPuntuacion() {
        return puntuacion;
    }

        /*SETTERS*/

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}



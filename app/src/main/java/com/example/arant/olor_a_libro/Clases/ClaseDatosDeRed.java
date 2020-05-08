package com.example.arant.olor_a_libro.Clases;

public class ClaseDatosDeRed {

    private String proyecto;
    private String grupo;


    public ClaseDatosDeRed(String proyecto, String grupo) {
        this.proyecto = proyecto;
        this.grupo = grupo;
    }

    public String getProyecto() {
        return proyecto;
    }
    public String getGrupo() {
        return grupo;
    }

    @Override
    public String toString() {
        return "DatosRed{" +
                "proyecto='" + proyecto + '\'' +
                ", grupo='" + grupo + '\'' +
                '}';
    }
}


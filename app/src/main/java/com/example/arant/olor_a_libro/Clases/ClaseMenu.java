package com.example.arant.olor_a_libro.Clases;

public class ClaseMenu {
    int Foto;
    String TextoMenu;

    public ClaseMenu(int foto, String textoMenu) {
        this.Foto = foto;
        this.TextoMenu = textoMenu;
    }

    public int getFoto() {
        return Foto;
    }

    public String getTextoMenu() {
        return TextoMenu;
    }
}

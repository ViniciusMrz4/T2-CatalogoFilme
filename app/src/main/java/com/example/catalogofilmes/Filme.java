package com.example.catalogofilmes;

import java.io.Serializable;
public class Filme implements Serializable {
    private String titulo;
    private String sinopse;
    public Filme(String titulo, String sinopse) {
        this.titulo = titulo;
        this.sinopse = sinopse;
    }
    public String getTitulo() { return titulo; }
    public String getSinopse() { return sinopse; }
}
package com.myapp.model;

public class Genero {

    private int id;
    private String nombreGenero;

    public Genero() {
    }

    public Genero(int id, String nombreGenero) {
        this.id = id;
        this.nombreGenero = nombreGenero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    @Override
    public String toString() {
        return nombreGenero;
    }
}

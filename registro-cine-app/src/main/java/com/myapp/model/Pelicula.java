package com.myapp.model;

public class Pelicula {

    private int id;
    private String titulo;
    private int generoId;
    private String nombreGenero;
    private int duracionMinutos;
    private int clasificacionId;
    private String codigoClasificacion;
    private String director;
    private String posterPath;

    public Pelicula() {
    }

    public Pelicula(String titulo, int generoId, int duracionMinutos,
                     int clasificacionId, String director, String posterPath) {
        this.titulo = titulo;
        this.generoId = generoId;
        this.duracionMinutos = duracionMinutos;
        this.clasificacionId = clasificacionId;
        this.director = director;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getGeneroId() {
        return generoId;
    }

    public void setGeneroId(int generoId) {
        this.generoId = generoId;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public int getClasificacionId() {
        return clasificacionId;
    }

    public void setClasificacionId(int clasificacionId) {
        this.clasificacionId = clasificacionId;
    }

    public String getCodigoClasificacion() {
        return codigoClasificacion;
    }

    public void setCodigoClasificacion(String codigoClasificacion) {
        this.codigoClasificacion = codigoClasificacion;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

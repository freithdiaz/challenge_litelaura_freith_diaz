package com.alura.literalura.model;

import java.util.List;

public class AutorInfo {
    private String nombre;
    private String añoNacimiento;
    private String añoMuerte;
    private List<String> libros;

    public AutorInfo(String nombre, String añoNacimiento, String añoMuerte, List<String> libros) {
        this.nombre = nombre;
        this.añoNacimiento = añoNacimiento;
        this.añoMuerte = añoMuerte;
        this.libros = libros;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAñoNacimiento() {
        return añoNacimiento;
    }

    public String getAñoMuerte() {
        return añoMuerte;
    }

    public List<String> getLibros() {
        return libros;
    }

}

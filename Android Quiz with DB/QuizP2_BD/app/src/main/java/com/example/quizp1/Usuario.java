package com.example.quizp1;

import java.util.Comparator;

public class Usuario {

    int rank;
    String nombre;
    int puntuacion;

    public Usuario() {
    }

    public Usuario(int rank, String nombre, int puntuacion){
        this.rank=rank;
        this.nombre=nombre;
        this.puntuacion=puntuacion;
    }

    public Usuario (String nombre,String puntuacion){
        this.nombre=nombre;
        this.puntuacion=Integer.parseInt(puntuacion);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

}



package com.ldm.ejemplojuegopiratas.juego;

public class Botin {

    public static int BASURA0=0;
    public static int BASURA1=1;
    public static int BASURA2=2;
    public static int BASURA3=3;
    public static int BASURA4=4;
    public static int CONTENEDOR1=5;
    public static int CONTENEDOR2=6;
    public static int CONTENEDOR3=7;
    public static int CONTENEDOR4=8;

    public int x, y;
    public int tipo;
    public int valor;

    public Botin(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        valor = valor(tipo);
    }

    private int valor(int tipo){
        switch (tipo){
            case 0:
                return 2;
            case 1:
                return 5;
            case 2:
                return 7;
            case 3:
                return 11;
            case 4:
                return 15;
            case 5:
                return 2;
            case 6:
                return 5;
            case 7:
                return 11;
            default:
                return 17;
        }
    }
}

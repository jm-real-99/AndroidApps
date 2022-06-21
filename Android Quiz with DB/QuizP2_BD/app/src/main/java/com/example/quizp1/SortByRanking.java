package com.example.quizp1;

import java.util.Comparator;

public class SortByRanking implements Comparator<Usuario> {

    public int compare(Usuario a, Usuario b)
    {
        return b.getPuntuacion() - a.getPuntuacion() ;
    }

}

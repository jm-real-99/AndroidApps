package com.example.quizp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ComoJugar extends AppCompatActivity {

    ImageView imagen;
    Button siguiente;

    MediaPlayer sonidoClick;
    MediaPlayer sonidoAtencion;



    int numeroSecuencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_como_jugar);

        sonidoClick=MediaPlayer.create(this,R.raw.sonido_click);
        sonidoAtencion=MediaPlayer.create(this,R.raw.sonido_atencion);

        imagen = findViewById(R.id.imagenEjemplo);
        siguiente = findViewById(R.id.botonSiguiente);
        numeroSecuencia=0;

        imagen.setImageResource(R.drawable.comojugarmenu);
    }


    public void pasarimagen(View view){
        sonidoClick.start();
        numeroSecuencia++;
        if(numeroSecuencia==1){
            imagen.setImageResource(R.drawable.cj_rg);
        }
        if(numeroSecuencia==2){
            imagen.setImageResource(R.drawable.cj_check);
            siguiente.setVisibility(View.GONE);
        }

    }

    public void volverMenu(View view){
        sonidoAtencion.start();
        Intent reiniciar=new Intent(this,MainActivity.class);
        startActivity(reiniciar);
    }
}
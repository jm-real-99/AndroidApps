package com.example.quizp1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizp1.BD.DataBase;

public class MainActivity extends AppCompatActivity {
    private EditText nombre;
    private DataBase db;
    private Usuario usuario;


    MediaPlayer sonidoFallo;
    MediaPlayer sonidoInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sonidoFallo=MediaPlayer.create(this,R.raw.sonido_fallo);
        sonidoInicio = MediaPlayer.create(this, R.raw.clashroyale_inicio);
        db = new DataBase(this);
        usuario = new Usuario();

        nombre = findViewById(R.id.txt_inicioSesionName);
    }

    public void comenzarJuego(View view) {
        if(inicioSesion()){

        sonidoInicio.start();
        Intent juego = new Intent(this, Quiz.class);
        //Pasamos el usuario, que es parámetro único para en los resultados, introducir en la BdD la puntuación.
        juego.putExtra("Usuario", usuario.getNombre());
        startActivity(juego);
        }
    }

    public void comojugar(View view) {
        Intent ayuda = new Intent(this, ComoJugar.class);
        startActivity(ayuda);
    }


    private boolean inicioSesion() {
        String textNombre = nombre.getText().toString();

        if (textNombre.equals("")) {
            sonidoFallo.start();
            Toast.makeText(MainActivity.this, "Introduce un Nick", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(MainActivity.this, "Usuario correcto", Toast.LENGTH_SHORT).show();
        }

        usuario.setNombre(textNombre);


        return true;
    }
        public void irRanking(View view){
            Intent ranking = new Intent(this, Ranking.class);
            ranking.putExtra("Usuario", "");
            startActivity(ranking);
        }
    }

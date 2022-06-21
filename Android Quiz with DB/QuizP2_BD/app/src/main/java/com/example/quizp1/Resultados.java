package com.example.quizp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizp1.BD.DataBase;

public class Resultados extends AppCompatActivity {

    Bundle puntuacion;
    Bundle acertadas;
    Bundle saltadas;
    Bundle falladas;
    TextView resultado;
    TextView mostarPuntuacion;
    TextView estadisticas;
    TextView cabeceraUsuario;

    MediaPlayer sonidoCerrar;
    MediaPlayer sonidoMenu;

    Bundle correoUsuario;
    String txtcorreoUsuario;
    Usuario user;

    int puntuacionFinal;

    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        db = new DataBase(this);



        puntuacion = getIntent().getExtras();
        acertadas = getIntent().getExtras();
        saltadas = getIntent().getExtras();
        falladas = getIntent().getExtras();
        correoUsuario= getIntent().getExtras();

        //user = db.getUsuario(correoUsuario);

        resultado=findViewById(R.id.MensajePuntuacion);
        cabeceraUsuario=findViewById(R.id.CorreoUsuario);

        sonidoCerrar = MediaPlayer.create(this,R.raw.sonido_cerrar);
        sonidoMenu=MediaPlayer.create(this,R.raw.sonido_atencion);


        puntuacionFinal= puntuacion.getInt("Puntuacion");
        int pAcertadas=acertadas.getInt("Acertadas");
        int pPasadas=saltadas.getInt("Saltadas");
        int pFalladas= falladas.getInt("Falladas");


        txtcorreoUsuario=correoUsuario.getString("Usuario");
        cabeceraUsuario.setText(txtcorreoUsuario);

        mostarPuntuacion= findViewById(R.id.Puntuacion);
        mostarPuntuacion.setText(""+puntuacionFinal);

        estadisticas = findViewById(R.id.Estadisticas);
        estadisticas.setText("Preguntas acertadas: "+pAcertadas+"\nPreguntas falladas: "+pFalladas+"\nPreguntas sin contestar: "+pPasadas);
        imprimirTexto(puntuacionFinal);

        puntuacionUsuario();

    }

    private void imprimirTexto(int punt){

        if(punt<=0){
            mostarPuntuacion.setTextColor(Color.RED);
            resultado.setText("MUY MAL\nEsperaba más de ti");
        }
        else if(punt>0 && punt<=5){
            mostarPuntuacion.setTextColor(Color.parseColor("#ffad58"));
            resultado.setText("BIEN HECHO\nAunque creo que puedes mejorar");
        }
        else if(punt>5 && punt<=12){
            mostarPuntuacion.setTextColor(Color.parseColor("#a4be37"));
            resultado.setText("MUY BIEN\nNo esperaba menos de ti");
        }
        else{
            mostarPuntuacion.setTextColor(Color.parseColor("#009cff"));
            resultado.setText("EXCELENTE\nEres genial");
        }
    }

    private void puntuacionUsuario(){
        Toast.makeText(Resultados.this, "Consultamos el usuario", Toast.LENGTH_SHORT).show();
        user = db.getUsuario(txtcorreoUsuario);

        if(user == null){ //Es que no existe, lo creamos
            Toast.makeText(Resultados.this, "Insertamos el usuario", Toast.LENGTH_SHORT).show();
            user = new Usuario();
            user.setNombre(txtcorreoUsuario);
            user.setPuntuacion(puntuacionFinal);
            boolean d = db.insertOne(user);
            if(d){
                Toast.makeText(Resultados.this, "Exito", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(Resultados.this, "Error", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(Resultados.this, "Actualizamos el usuario", Toast.LENGTH_SHORT).show();
            if(user.getPuntuacion()<=puntuacionFinal){
                user.setPuntuacion(puntuacionFinal);
                //Actualizamos en la BdD la puntuación del usuario.
                int d = db.actualizar(user);
                Toast.makeText(Resultados.this, ""+d, Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void volverMenu(View view){
        sonidoMenu.start();
        Intent reiniciar=new Intent(this,MainActivity.class);
        startActivity(reiniciar);
    }

    public void cerrar(View view){
        sonidoCerrar.start();
        finishAffinity();
    }

    public void irRanking(View view){
        Intent ranking = new Intent(this,Ranking.class);
        //Cuando esté la BdD bien implementada:
        //ranking.putExtra("Usuario",user.getEmail());
        ranking.putExtra("Usuario",txtcorreoUsuario);
        startActivity(ranking);
    }

}
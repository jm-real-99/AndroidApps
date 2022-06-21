package com.example.quizp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizp1.BD.DataBase;

import java.util.List;

public class Ranking extends AppCompatActivity {

    TextView usuario1,usuario2,usuario3,punt1,punt2,punt3,usuariotuyo,punttuya,posTuya, textoPresentacion;

    Bundle correo;
    String correoUsur;
    MediaPlayer sonidoAtencion;

    //Usuario miUsuario;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        correo= getIntent().getExtras();
        correoUsur=correo.getString("Usuario");

        sonidoAtencion= MediaPlayer.create(this,R.raw.sonido_atencion);

        db = new DataBase(this);

        usuario1 = findViewById(R.id.txtPos1);
        usuario2 = findViewById(R.id.txtPos2);
        usuario3 = findViewById(R.id.txtPos3);
        punt1 = findViewById(R.id.txtPunt1);
        punt2 = findViewById(R.id.txtPunt2);
        punt3 = findViewById(R.id.txtPunt3);

        textoPresentacion=findViewById(R.id.txtViewTuyo);
        usuariotuyo=findViewById(R.id.txtPosTu);
        punttuya = findViewById(R.id.txtPuntTu);
        posTuya = findViewById(R.id.PosTu);

        //establecerAux();

        establecerRanking();

    }

    //Aquí usamos la base de datos, para sacar las posiciones del ranking. Además de la info de tu usuario, que también se mostrará.
    private void establecerRanking(){

        //Obtenemos de la base de datos los jugadores con el ranking más alto.
        List<Usuario> lista = db.getUserByRanking();
        Usuario primero= lista.get(0);
        Usuario segundo= lista.get(1);
        Usuario tercero= lista.get(2);


        usuario1.setText(primero.getNombre());
        punt1.setText(""+primero.getPuntuacion());

        usuario2.setText(segundo.getNombre());
        punt2.setText(""+segundo.getPuntuacion());

        usuario3.setText(tercero.getNombre());
        punt3.setText(""+tercero.getPuntuacion());

        //Usuario user = db.getUsuario(correoUsur);
        //usuariotuyo.setText(user.getNombre());
        //punttuya.setText(String.valueOf(user.getPuntuacion()));

        if(correoUsur.equals("")){ //Si lo llamamos desde el menú, no habremos iniciado sesión. No se mostrará.
            Toast.makeText(Ranking.this, "Usuario vacio", Toast.LENGTH_SHORT).show();
            usuariotuyo.setText("");
            punttuya.setText("");
            posTuya.setText("");
            textoPresentacion.setText("");
        }else{
            Usuario user = db.getUsuario(correoUsur);
            usuariotuyo.setText(user.getNombre());
            punttuya.setText(String.valueOf(user.getPuntuacion()));
            //Buscamos en el array nuestra posición, ese será nuestro ranking.
            int i=1;
            for(Usuario u:lista){
                if(u.getNombre().equals(user.getNombre())){
                    posTuya.setText(""+i);
                    break;
                }
                i++;
            }
        }


    }

    public void volverMenu(View view){
        sonidoAtencion.start();
        Intent reiniciar=new Intent(this,MainActivity.class);
        startActivity(reiniciar);
    }
}
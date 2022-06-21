package com.example.quizp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {


    //CREAMOS LAS VARIABLES NECESARIAS+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private final int respuestas[]= {R.id.RespuestaA,R.id.RespuestaB,R.id.RespuestaC,R.id.RespuestaD};
    private final int respuestasCorrectas[]={R.id.RespuestaC,R.id.RespuestaB,R.id.RespuestaA,R.id.RespuestaB,R.id.RespuestaA,R.id.RespuestaA};
    private String[] preguntas;
    private String[] respuestas1;
    private String[] respuestas2;
    private String[] respuestas3;
    private String[] respuestas4;
    private String[] respuestas5;
    private String[] respuestas6;
    private String[] mensaje1;
    private String[] mensaje2;
    private String[] mensaje3;
    private String[] mensaje4;
    private String[] mensaje5;
    private final String error="No has contestado a la pregunta";

    //Creamos el botón para validar la respuesta
    Button comprobar;
    Button volver;
    Button finalizar;
    //Creamos los textos que se verán en la app
    TextView pregunta;
    TextView cabecera;
    RadioGroup grupo;
    ImageView imagen;
    ImageView birdBueno;
    ImageView birdPeli;
    ImageView birdYellow;
    ImageView birdBomb;

    //CheckBox[] checkBoxes= {findViewById(R.id.checkBox1),findViewById(R.id.checkBox2),findViewById(R.id.checkBox3),findViewById(R.id.checkBox4)};
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;

    int numPregunta;
    int puntuacion;
    int respuestasAcertadas;
    int respuestasNoContestadas;
    boolean fallado=false;

    //Insertamos sonidos
    MediaPlayer sonidoAcierto;
    MediaPlayer sonidoAtencion;
    MediaPlayer sonidoFallo;
    MediaPlayer sonidoClick;


    Bundle correoUsuario;
    String usuario;
    TextView cabeceraUser;

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Creamos el Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //INICIALIZAMOS LAS VARIABLES NECESARIAS----------------------------------------------------

        /*
        //Creamos el botón para validar la respuesta
        Button comprobar=findViewById(R.id.ConfirmarRespuesta);
        //Creamos los textos que se verán en la app
        TextView pregunta= findViewById(R.id.Pregunta);
        TextView cabecera= findViewById(R.id.QuestionCabecera);
        */

        correoUsuario = getIntent().getExtras();
        usuario = correoUsuario.getString("Usuario");
        cabeceraUser=findViewById(R.id.cabeceraUser);
        cabeceraUser.setText(usuario);

        //Creamos el botones
        comprobar=findViewById(R.id.ConfirmarRespuesta);
        volver=findViewById(R.id.Volver);
        finalizar=findViewById(R.id.Finalizar);
        //Creamos los textos que se verán en la app
        pregunta= findViewById(R.id.Pregunta);
        cabecera= findViewById(R.id.QuestionCabecera);

        //Creamos checkBoxes
        cb1=findViewById(R.id.checkBox1);
        cb2=findViewById(R.id.checkBox2);
        cb3=findViewById(R.id.checkBox3);
        cb4=findViewById(R.id.checkBox4);

        //los ponemos invisibles. Ya que no los vamos a usar
        cb1.setVisibility(View.GONE);
        cb2.setVisibility(View.GONE);
        cb3.setVisibility(View.GONE);
        cb4.setVisibility(View.GONE);

        //Creamos las imágenes
        imagen=findViewById(R.id.ImgCandy);
        birdBueno = findViewById(R.id.imagegood) ;
        birdPeli = findViewById(R.id.imagefilm);
        birdYellow = findViewById(R.id.imageyellow);
        birdBomb = findViewById(R.id.imagebomb);

        //Ponemos las imagenes invisibles. Ya que no las vamos a usar
        imagen.setVisibility(View.GONE);
        birdBueno.setVisibility(View.GONE);
        birdPeli.setVisibility(View.GONE);
        birdYellow.setVisibility(View.GONE);
        birdBomb.setVisibility(View.GONE);

        //Variable contadora de puntos
        puntuacion=0;
        //Variable que nos dirá si hemos fallado. Esto nos permitirá volver a empezar
        fallado=false;
        //Variable que nos indicará en qué pregunta nos encontramos
        numPregunta=0;
        //Variables que nos ayudarán a sacar las estadísticas
        respuestasAcertadas=0;
        respuestasNoContestadas=0;

        //Obtenemos de string.xml los siguientes array de datos, que serían cada pregunta
        preguntas= getResources().getStringArray(R.array.Preguntas);

        //Lo mismo pero obtenemos las posibles respuestas en un Array
        respuestas1= getResources().getStringArray(R.array.Respuesta1);
        respuestas2= getResources().getStringArray(R.array.Respuesta2);
        respuestas3= getResources().getStringArray(R.array.Respuesta3);
        respuestas4= getResources().getStringArray(R.array.Respuesta4);
        respuestas5= getResources().getStringArray(R.array.Respuesta5);
        respuestas6= getResources().getStringArray(R.array.Respuesta6);

        //Ahora obtenemos los mensajes que saldrán dependiendo de cada respuesta
        mensaje1= getResources().getStringArray(R.array.MensajesP1);
        mensaje2= getResources().getStringArray(R.array.MensajesP2);
        mensaje3= getResources().getStringArray(R.array.MensajesP3);
        mensaje4= getResources().getStringArray(R.array.MensajesP4);
        mensaje5= getResources().getStringArray(R.array.MensajesP5);

        //Lo juntamos en un array, para hacerlo más accesible
        String[][] todasRespuestas={respuestas1,respuestas2,respuestas3,respuestas4,respuestas5,respuestas6};
        String[][] todosMensajes={mensaje1,mensaje2,mensaje3,mensaje4,mensaje5};

        //Sonidos
        sonidoAcierto=MediaPlayer.create(this,R.raw.sonido_acierto);
        sonidoAtencion=MediaPlayer.create(this,R.raw.sonido_atencion);
        sonidoFallo=MediaPlayer.create(this,R.raw.sonido_fallo);
        sonidoClick=MediaPlayer.create(this,R.raw.sonido_click);

        //------------------------------------------------------------------------------------------

        //QUÉ HARÁ EL ACTIVITY**********************************************************************

        //Ponemos en invisible el botón finalizar. Solo nos servirá su acción, así que lo accionaremos al final.
        finalizar.setVisibility(View.GONE);
        pregunta(numPregunta,todasRespuestas[numPregunta]);

        //Establecemos el RadioGroup para saber cuál de las opciones se está pulsando
        grupo = findViewById(R.id.GrupoRespuestas);

        //Realizamos una acción dependiendo de la opción seleccionada.
        comprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int seleccionada=grupo.getCheckedRadioButtonId();

                if(seleccionada==respuestas[0]){
                    Toast.makeText(Quiz.this, todosMensajes[numPregunta][0], Toast.LENGTH_SHORT).show();
                    if(seleccionada==respuestasCorrectas[numPregunta]){
                        sonidoAcierto.start();
                        puntuacion+=3;
                        respuestasAcertadas++;
                    }else{
                        sonidoFallo.start();
                        puntuacion-=2;
                        fallado=true;
                    }

                }
                else if(seleccionada==respuestas[1]){
                    Toast.makeText(Quiz.this, todosMensajes[numPregunta][1], Toast.LENGTH_SHORT).show();
                    if(seleccionada==respuestasCorrectas[numPregunta]){
                        sonidoAcierto.start();
                        puntuacion+=3;
                        respuestasAcertadas++;
                    }else{
                        sonidoFallo.start();
                        puntuacion-=2;
                        fallado=true;
                    }


                }
                else if(seleccionada==respuestas[2]){
                    Toast.makeText(Quiz.this, todosMensajes[numPregunta][2], Toast.LENGTH_SHORT).show();
                    if(seleccionada==respuestasCorrectas[numPregunta]){
                        sonidoAcierto.start();
                        puntuacion+=3;
                        respuestasAcertadas++;
                    }else{
                        sonidoFallo.start();
                        puntuacion-=2;
                        fallado=true;
                    }

                }
                else if(seleccionada==respuestas[3]){
                    Toast.makeText(Quiz.this, todosMensajes[numPregunta][3], Toast.LENGTH_SHORT).show();
                    if(seleccionada==respuestasCorrectas[numPregunta]){
                        sonidoAcierto.start();
                        puntuacion+=3;
                        respuestasAcertadas++;
                    }else{
                        sonidoFallo.start();
                        puntuacion-=2;
                        fallado=true;
                    }

                }
                else{
                    sonidoClick.start();
                    respuestasNoContestadas++;
                    fallado=true;
                    Toast.makeText(Quiz.this, error, Toast.LENGTH_SHORT).show();
                }


                numPregunta++;
                if(numPregunta==preguntas.length) {
                    finalizar.performClick();
                }
                else if(numPregunta==preguntas.length-1){
                    preguntaCheckBox();
                }
                else{
                    grupo.clearCheck();
                    pregunta(numPregunta,todasRespuestas[numPregunta]);
                }

            }


        });


        //******************************************************************************************
    }

    private void pregunta(int numP,String[] resp){
        int n=numP+1;
        pregunta.setText(preguntas[numP]);
        cabecera.setText("Pregunta "+(n)+":");

        //Establecemos las respuestas a la pregunta de los Radio Button
        for(int i=0;i<4;i++){
            RadioButton rb = findViewById(respuestas[i]);
            rb.setText(resp[i]);
        }
        if(numP==3){ //Si la preugnta contiene una imágen
            imagen.setVisibility(View.VISIBLE);
            grupo.setTranslationY(555);
        }
        else{ //Si no, entonces la ponemos en invisible
            imagen.setVisibility(View.GONE);
        }
        if(numP==4){ //Si la pregunta es la de respuestas en imagen
            grupo.setTranslationY(3);
            grupo.setVerticalScrollBarEnabled(true);
            birdBueno.setVisibility(View.VISIBLE);
            birdPeli.setVisibility(View.VISIBLE);
            birdYellow.setVisibility(View.VISIBLE);
            birdBomb.setVisibility(View.VISIBLE);

            for(int i=0;i<4;i++){
                RadioButton rb = findViewById(respuestas[i]);
                rb.setText("");
            }

        }
        //No nos preocupamos en hacer invisible las imagenes de numP==4 porque ya lo hacemos en la función preguntaCheckBox

        //Si se ha fallado la pregunta anterior, se habilita el botón para regresar.
        if(!fallado){
            volver.setVisibility(View.GONE);
        }
        else{
            volver.setVisibility(View.VISIBLE);
        }

    }

    public void terminarJuego(View view){
        Intent fin=new Intent(this,Resultados.class);
        fin.putExtra("Puntuacion",puntuacion);
        fin.putExtra("Acertadas",respuestasAcertadas);
        fin.putExtra("Saltadas",respuestasNoContestadas);
        fin.putExtra("Falladas",(preguntas.length-respuestasAcertadas));
        fin.putExtra("Usuario",usuario);
        //finishAfterTransition();
        startActivity(fin);
    }

    public void reiniciarJuego(View view){
        sonidoAtencion.start();
        Intent reiniciar=new Intent(this,MainActivity.class);
        startActivity(reiniciar);
    }

    public void preguntaCheckBox(){
        //PONEMOS EN INVISIBLE TODOS LOS ELEMENTOS ANTERIORES
        birdBueno.setVisibility(View.GONE);
        birdPeli.setVisibility(View.GONE);
        birdYellow.setVisibility(View.GONE);
        birdBomb.setVisibility(View.GONE);
        grupo.setVisibility(View.GONE);

        //PONEMOS EN VISIBLE LA PREGUNTA CHECKBOX
        cb1.setVisibility(View.VISIBLE);
        cb2.setVisibility(View.VISIBLE);
        cb3.setVisibility(View.VISIBLE);
        cb4.setVisibility(View.VISIBLE);

        //Ponemos los textos
        pregunta.setText(preguntas[5]);
        cb1.setText(respuestas6[0]);
        cb2.setText(respuestas6[1]);
        cb3.setText(respuestas6[2]);
        cb4.setText(respuestas6[3]);

        comprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si ambas respuestas están correctas
                if(cb1.isChecked() && cb4.isChecked() && !cb2.isChecked() && !cb3.isChecked()){
                    sonidoAcierto.start();
                    puntuacion+=3;
                    respuestasAcertadas++;
                    Toast.makeText(Quiz.this, "CORRECTO", Toast.LENGTH_SHORT).show();
                }
                //Respuesta no contestada. Es decir, no hay ningún checkBox seleccionado.
                else if(!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked()){
                    sonidoClick.start();
                    respuestasNoContestadas++;
                    fallado=true;
                    Toast.makeText(Quiz.this, error, Toast.LENGTH_SHORT).show();
                }
                //Si no es ninguna de las anteriores, entonces es fallo
                else{
                    sonidoFallo.start();
                    puntuacion-=2;
                    fallado=true;
                    Toast.makeText(Quiz.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

                finalizar.performClick();

            }
        });
    }

}
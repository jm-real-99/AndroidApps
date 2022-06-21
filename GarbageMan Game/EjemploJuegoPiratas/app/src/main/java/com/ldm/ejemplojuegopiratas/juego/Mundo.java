package com.ldm.ejemplojuegopiratas.juego;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13; //13
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;
    static Botin[] botinCopia= new Botin[MUNDO_ALTO]; //Nos servirá para cargar las basuras en  Pantalla juego
    static Botin[] contenedorCopia = new Botin[MUNDO_ALTO]; //Nos servirá para cargar los contenedores en  Pantalla juego
    static int restarTiempo = 0;

    static float tiempoColocar=0; //Contador del tiempo que llevamos para colocar una basura nueva

    public JollyRoger jollyroger;
    public Botin[] botin= new Botin[MUNDO_ALTO];
    public int botinPuesto = 0;
    public Botin[] contenedor = new Botin[MUNDO_ALTO];

    public boolean finalJuego = false;
    public int puntuacion = 0;
    public int cola = 0; //Tamaño de la cola
    public int tiempo=-5; //Tiempo que llevamos con la basura llena

    //Variables que nos servirán para controlar el aumento de dificultad
    boolean aumentodificultad1 = true;
    boolean aumentodificultad2 = true;
    boolean aumentodificultad3= true;
    boolean aumentodificultad4= true;
    boolean aumentodificultad5= true;
    //Numero máximo de tipo de basura que colocaremos. Aumentará junto a la dificultad
    int maxBasura = 3;
    //Tiempo maximo para colocar una nueva basura, disminuirá junto a la dificultad.
    int nRandom = 5;


    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Mundo() {
        jollyroger = new JollyRoger();
        //Primero colocamos los contenedores, que van a estar siempre en la misma posicion
        colocarContenedores();
        colocarBotin();

        botinCopia = botin;
        contenedorCopia= contenedor;
    }

    private void colocarContenedores(){
        int y=MUNDO_ALTO-2;
        for(int i=0;i<MUNDO_ALTO;i++){
            if(i<4){
                contenedor[i]= new Botin(0,y, 5+i);
                y -=(int)MUNDO_ALTO/4;
            }

            botin[i] = new Botin(-10,i,0);
        }
    }

    private void colocarBotin() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        Tripulacion parte = jollyroger.partes.get(0);
        campos[parte.x][parte.y] = true;

        for(int i = 0;i<MUNDO_ALTO;i++){
            if(i==0 || i==1)
                campos[9][i]= true;
            if(botin[i].x>0){
                campos[9][botin[i].y] = true;
            }
        }

        //Siempre van a aparecer a la derecha de la pantalla
        int botinX = 9;
        int botinY = random.nextInt(MUNDO_ALTO);
        while (true) {
            if (campos[botinX][botinY] == false)
                break;
                botinY += 1;
                if (botinY >= MUNDO_ALTO) {
                    botinY = 2;
                }

        }
        botin[botinY] = new Botin(botinX, botinY, random.nextInt(maxBasura));
        botinPuesto++;
    }

    public void update(float deltaTime) {
        if (finalJuego)
            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            tiempoColocar += tick;

            jollyroger.avance();
            if (jollyroger.comprobarChoque()) {
                finalJuego = true;
                return;
            }


            Tripulacion head = jollyroger.partes.get(0);
            for(int i=0;i<MUNDO_ALTO;i++){
                if (head.x == botin[i].x && head.y == botin[i].y) {
                    cola += botin[i].valor;
                    botin[i].x = -10;
                    jollyroger.abordaje(botin[i].valor);
                    botinPuesto--;
                }
                if(i<4){
                    //Tiramos la basura en el contenedor correspondiente
                    if(head.x == contenedor[i].x && head.y == contenedor[i].y){
                        puntuacion += contenedor[i].valor;
                        cola -= contenedor[i].valor;
                        if(cola>=0)
                            jollyroger.tirarbasura(contenedor[i].valor);
                    }
                }


            }

            //SI la cola es menor que 0 significa que hemos tirado mas basura de la que debemos
            //SI la cola es mayor que 21, es que hemos cogido más basura de la que podemos cargar
            //Si el tiempo es 0, es que hemos acumulado demasiada basura.
            //EN cualquiera de los casos perdemos
            if (cola<0 ||cola >21 || tiempo==0) {
                finalJuego = true;
                return;
            }

            //Si tenemos el num maximo de basuras, llevamos la cuenta del tiempo
            if(botinPuesto==MUNDO_ALTO-3){
                restarTiempo++;
                //Restamos cada segundo, porque el tick se cuenta cada 0,5 s, por lo tanto se suma 1 cada medio segundo.
                if(tiempo>0 && restarTiempo%2==0)
                    tiempo--;
                //Si el tiempo es negativo, es que tenemos que empezar la cuenta.
                else if(tiempo<0)
                    tiempo=30;

                if(restarTiempo==100) //Restauramos la variable a un valor 0 para asegurarnos de que no se produzca desbordamiento
                    restarTiempo=0;
            }
            else{
                //Si no tenemos el num maximo de basuras, ponemos un valor negativo, para diferenciarlo de que lleve la cuenta.
                tiempo=-2;
            }

            //Aumentamos la dificultad dependiendo de la puntuacion
            if(puntuacion>21 && aumentodificultad1){
                maxBasura++;
                aumentodificultad1=false;
            }
            if(puntuacion>30 && aumentodificultad2){
                nRandom--;
                aumentodificultad2=false;
            }
            if(puntuacion>41 && aumentodificultad3){
                maxBasura++;
                aumentodificultad3=false;
            }
            if(puntuacion>51 && aumentodificultad4){
                nRandom--;
                aumentodificultad4=false;
            }
            if(puntuacion>75 && aumentodificultad5){
                nRandom--;
                aumentodificultad5=false;
            }


            //Tiempo para colocar botín
            if(tiempoColocar>=nRandom && botinPuesto<MUNDO_ALTO-3){
                tiempoColocar=0;
                colocarBotin();
            }


        }
    }

}


package com.ldm.ejemplojuegopiratas.juego;

import java.util.Random;

public class Mundo {
    static final int MUNDO_ANCHO = 10;
    static final int MUNDO_ALTO = 13;
    static final int INCREMENTO_PUNTUACION = 10;
    static final float TICK_INICIAL = 0.5f;
    static final float TICK_DECREMENTO = 0.05f;

    public JollyRoger jollyroger;
    public Botin botin;
    public boolean finalJuego = false;
    public int puntuacion = 0;

    public Botin[] veneno = new Botin[3];
    public int nVenenos=0;
    public int[] vidaVeneno={0,0,0};

    public Botin[] muro = new Botin[5];
    public int nMuros=0;
    public int vidaMuro=0;

    boolean campos[][] = new boolean[MUNDO_ANCHO][MUNDO_ALTO];
    Random random = new Random();
    float tiempoTick = 0;
    static float tick = TICK_INICIAL;

    public Boolean comiendo=false;

    public Mundo() {
        jollyroger = new JollyRoger();
        colocarBotin();
    }

    private void colocarBotin() {
        for (int x = 0; x < MUNDO_ANCHO; x++) {
            for (int y = 0; y < MUNDO_ALTO; y++) {
                campos[x][y] = false;
            }
        }

        int len = jollyroger.partes.size();
        for (int i = 0; i < len; i++) {
            Tripulacion parte = jollyroger.partes.get(i);
            campos[parte.x][parte.y] = true;
        }

        int botinX = random.nextInt(MUNDO_ANCHO);
        int botinY = random.nextInt(MUNDO_ALTO);
        while (true) {
            if (campos[botinX][botinY] == false)
                break;
            botinX += 1;
            if (botinX >= MUNDO_ANCHO) {
                botinX = 0;
                botinY += 1;
                if (botinY >= MUNDO_ALTO) {
                    botinY = 0;
                }
            }
        }


        if(nVenenos<3){
            int tipoGalleta = random.nextInt(6);

            if(tipoGalleta>3) {

                botin = new Botin(botinX, botinY,3);

                if (botin.tipo == 3) {
                    //Entonces la galleta es un veneno.
                    veneno[nVenenos] = botin; //Nos indicamos que es un veneno
                    vidaVeneno[nVenenos] = 0;
                    nVenenos++;

                    do {
                        botinX = random.nextInt(MUNDO_ANCHO);
                        botinY = random.nextInt(MUNDO_ALTO);
                    } while (galletaEnVeneno(botinX, botinY)); //Evitamos que la galleta se ponga encima del veneno.
                }
            }
        }

        botin= new Botin(botinX, botinY, random.nextInt(3));

        //También vamos a colocar unos muros, en caso de que la puntuación sea superior a 50 y no haya muros ya puestos. Para aumentar la dificultad.
        if(nMuros==0 && puntuacion>50){
            colocarMuros();
        }

    }

    private boolean galletaEnVeneno(int x,int y){
        for(int i=0;i<nVenenos;i++){
            if((x==veneno[i].x && y== veneno[i].y) && campos[x][y]){
                return true;
            }
        }
        return false;
    }

    private void eliminarVeneno(int pos){
        if(pos==nVenenos-1){ //Pueden ser 0,1 o 2. En este caso sería el último. Solo nos lo quitamos del medio.
            veneno[pos].x=1000;
            veneno[pos].y=1000;
            vidaVeneno[pos]=0;
            nVenenos--;
        }
        if(pos==nVenenos-2){ //En este caso solo podrían ser el 0 o el 1. Si es este caso, entonces tendríamos que quitar esta posición y mover 1 posición las siguientes.
            veneno[pos].x=veneno[pos+1].x;
            veneno[pos].y=veneno[pos+1].y;
            vidaVeneno[pos]=vidaVeneno[pos+1];
            veneno[pos+1].x=1000;
            veneno[pos+1].y=1000;
            vidaVeneno[pos+1]=0;
            nVenenos--;
        }
        if(pos==nVenenos-3){ //En este caso solo podría ser el 0, y tenemos todos los venenos puestos. Habría que sustituir el 0 y mover 1 posición los siguientes.
            veneno[pos].x=veneno[pos+1].x;
            veneno[pos].y=veneno[pos+1].y;
            vidaVeneno[pos]=vidaVeneno[pos+1];
            veneno[pos+1].x=veneno[pos+2].x;
            veneno[pos+1].y=veneno[pos+2].y;
            vidaVeneno[pos+1]=vidaVeneno[pos+2];
            veneno[pos+2].x=1000;
            veneno[pos+2].y=1000;
            vidaVeneno[pos+2]=0;
            nVenenos--;
        }
    }

    //Se encargará de poner un muro de forma aleatoria y tamaño aleatorio hasta 5.
    private void colocarMuros(){
        //Colocamos el primer muro, en una posición aleatoria
        int botinX;
        int botinY;



        do{
            botinX = random.nextInt(MUNDO_ANCHO);
            botinY = random.nextInt(MUNDO_ALTO);
        }while(muroLibre(botinX,botinY));
        //Entonces estamos en una posición libre, lo añadimos
        nMuros++;
        muro[0] = new Botin(botinX, botinY,4);

        /*Ahora construiremos aleatoriamente el muro. Con un int random de 0 a 4, haremos lo siguiente:
        0:Paramos de crear el muro
        1:Sumamos un muro a la izquierda
        2:Sumamos un muro arriba
        3:Sumamos un muro a izquierda
        4:Sumamos un muro abajo
         */
        int ran;
        for(int i=1;i<5;i++){

            ran = random.nextInt(5);

            if(ran==0){
                break;
            }
            else if(ran==1){

                botinX=(botinX+1)%MUNDO_ANCHO;

                //Si la posición donde queremos añadir el muro está ocupada, paramos la creación del muro
                if(muroLibre(botinX,botinY)){
                    break;
                }
                nMuros++;
                muro[i]=new Botin(botinX, botinY,4);
            }
            else if(ran==2){

                botinY=(botinY+1)%MUNDO_ALTO;
                if(muroLibre(botinX,botinY)){
                    break;
                }
                nMuros++;
                muro[i]=new Botin(botinX, botinY,4);
            }
            else if(ran==3){

                botinX=(botinX-1)%MUNDO_ANCHO;
                if(muroLibre(botinX,botinY)){
                    break;
                }
                nMuros++;
                muro[i]=new Botin(botinX, botinY,4);
            }
            else if(ran==4){

                botinY=(botinY-1)%MUNDO_ALTO;
                if(muroLibre(botinX,botinY)){
                    break;
                }
                nMuros++;
                muro[i]=new Botin(botinX, botinY,4);
            }
        }
        vidaMuro=0;
    }

    private void eliminarMuros(){
        nMuros=0;
    }

    private boolean muroLibre(int x, int y){
        if(x==botin.x && y==botin.y && campos[x][y]) {
            return true;
        }
        return false;
    }
    public void update(float deltaTime) {
        if (finalJuego)

            return;

        tiempoTick += deltaTime;

        while (tiempoTick > tick) {
            tiempoTick -= tick;
            jollyroger.avance();
            if (jollyroger.comprobarChoque()) {
                finalJuego = true;
                return;
            }

            Tripulacion head = jollyroger.partes.get(0);

            for(int i=0;i<nVenenos;i++){
                if(vidaVeneno[i]>=50){
                    eliminarVeneno(i);
                }

                if (head.x == veneno[i].x && head.y == veneno[i].y){
                    boolean perdido=jollyroger.envenenado();
                    if(perdido){
                        finalJuego = true;
                        return;
                    }
                    eliminarVeneno(i);
                    puntuacion-=30;
                }
                vidaVeneno[i]++;

            }

            //TAmbién comprobamos que no hayamos chocado con un muro.
            for(int i=0;i<nMuros;i++){
                if(head.x==muro[i].x && head.y==muro[i].y){
                    finalJuego=true;
                    return;
                }
            }
            if(vidaMuro>=50){
                eliminarMuros();
            }
            vidaMuro++;


            if (head.x == botin.x && head.y == botin.y) {
                comiendo=true;
                puntuacion += INCREMENTO_PUNTUACION;
                jollyroger.abordaje();
                if (jollyroger.partes.size() == MUNDO_ANCHO * MUNDO_ALTO) {
                    finalJuego = true;
                    return;
                } else {
                    colocarBotin();
                }

                if (puntuacion % 100 == 0 && tick - TICK_DECREMENTO > 0) {
                    tick -= TICK_DECREMENTO;
                }
            }
            else{
                comiendo=false;
            }
        }
    }
}


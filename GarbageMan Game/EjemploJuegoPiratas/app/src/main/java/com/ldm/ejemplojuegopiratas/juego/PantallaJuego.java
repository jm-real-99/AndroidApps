package com.ldm.ejemplojuegopiratas.juego;

import java.util.List;

import android.graphics.Color;

import com.ldm.ejemplojuegopiratas.Juego;
import com.ldm.ejemplojuegopiratas.Graficos;
import com.ldm.ejemplojuegopiratas.Input.TouchEvent;
import com.ldm.ejemplojuegopiratas.Pixmap;
import com.ldm.ejemplojuegopiratas.Pantalla;

public class PantallaJuego extends Pantalla {
    enum EstadoJuego {
        Preparado,
        Ejecutandose,
        Pausado,
        FinJuego
    }

    EstadoJuego estado = EstadoJuego.Preparado;
    Mundo mundo;
    int antiguaPuntuacion = 0;
    String puntuacion = "0";
    int antiguaCola = 0;
    String cola = "0";
    int antiguoTiempo=-3;
    String tiempo="0";

    //Animaciones la caminar, llevamos la cuenta con el double animacion para saber en que numero vamos.
    double animacion=0;
    Pixmap[] arriba = {Assets.arriba1,Assets.arriba2,Assets.arriba3,Assets.arriba4,Assets.arriba3,Assets.arriba2};
    Pixmap[] abajo = {Assets.abajo1,Assets.abajo2,Assets.abajo3,Assets.abajo4,Assets.abajo3,Assets.abajo2};
    Pixmap[] izq = {Assets.izq1,Assets.izq2,Assets.izq3,Assets.izq4,Assets.izq3,Assets.izq2};
    Pixmap[] der = {Assets.der1,Assets.der2,Assets.der3,Assets.der4,Assets.der3,Assets.der2};



    public PantallaJuego(Juego juego) {
        super(juego);
        mundo = new Mundo();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = juego.getInput().getTouchEvents();
        juego.getInput().getKeyEvents();

        if(estado == EstadoJuego.Preparado)
            updateReady(touchEvents);
        if(estado == EstadoJuego.Ejecutandose)
            updateRunning(touchEvents, deltaTime);
        if(estado == EstadoJuego.Pausado)
            updatePaused(touchEvents);
        if(estado == EstadoJuego.FinJuego)
            updateGameOver(touchEvents);

    }

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            estado = EstadoJuego.Ejecutandose;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    estado = EstadoJuego.Pausado;
                    return;
                }
            }
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(event.x < 64 && event.y > 416) {
                    mundo.jollyroger.girarIzquierda();
                }
                if(event.x > 256 && event.y > 416) {
                    mundo.jollyroger.girarDerecha();
                }
            }
        }

        mundo.update(deltaTime);
        if(mundo.finalJuego) {
            if(Configuraciones.sonidoHabilitado)
                Assets.derrota.play(1);
            estado = EstadoJuego.FinJuego;
        }
        if(antiguaPuntuacion != mundo.puntuacion) {
            antiguaPuntuacion = mundo.puntuacion;
            puntuacion = "" + antiguaPuntuacion;
            if(Configuraciones.sonidoHabilitado)
                Assets.contenedorSon.play(1);
        }

        //Cargamos el tamaño de la cola en la IU
        if(antiguaCola != mundo.cola){
            if(Configuraciones.sonidoHabilitado && antiguaCola<mundo.cola )
                Assets.basuraSon.play(1);
            antiguaCola = mundo.cola;
            cola = ""+antiguaCola;

        }

        //En caso de que haya una cuenta atrás, cargamos el tiempo en la IU
        if(mundo.tiempo>0){
            if(mundo.tiempo%2==0 && antiguoTiempo>mundo.tiempo ){
                if(Configuraciones.sonidoHabilitado){
                    Assets.reloj1.play(1);
                }
            }
            else if(mundo.tiempo%2==1 && antiguoTiempo>mundo.tiempo){
                if(Configuraciones.sonidoHabilitado){
                    Assets.reloj2.play(1);
                }
            }
            antiguoTiempo = mundo.tiempo;
            tiempo = ""+mundo.tiempo;

        }
        else if(mundo.tiempo<0){
            tiempo="";
            antiguoTiempo=-3;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        estado = EstadoJuego.Ejecutandose;
                        return;
                    }
                    if(event.y > 148 && event.y < 196) {
                        if(Configuraciones.sonidoHabilitado)
                            Assets.pulsar.play(1);
                        juego.setScreen(new MainMenuScreen(juego));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
                    if(Configuraciones.sonidoHabilitado)
                        Assets.pulsar.play(1);
                    juego.setScreen(new MainMenuScreen(juego));
                    return;
                }
            }
        }
    }


    @Override
    public void present(float deltaTime) {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.fondo, 0, 0);
        drawWorld(mundo);
        if(estado == EstadoJuego.Preparado)
            drawReadyUI();
        if(estado == EstadoJuego.Ejecutandose)
            drawRunningUI();
        if(estado == EstadoJuego.Pausado)
            drawPausedUI();
        if(estado == EstadoJuego.FinJuego)
            drawGameOverUI();

        //IMPRIMIMOS LA PUNTUACION QUE LLEVAMOS
        drawText(g, puntuacion, ((g.getWidth() - puntuacion.length()*20)/4)+30 , g.getHeight() - 42);
        //Imprimimos el tamaño de la cola que tenemos
        drawText(g, cola, ((g.getWidth()*2 - cola.length()*20)/4)+30 , g.getHeight() - 42);
        //Si hay cuenta atrás la mostamos
        drawText(g, tiempo, ((g.getWidth() - tiempo.length()*20)/2) , 20 );



    }

    private void drawWorld(Mundo mundo) {
        Graficos g = juego.getGraphics();
        JollyRoger jollyroger = mundo.jollyroger;
        Tripulacion head = jollyroger.partes.get(0);

        //MODIFICAR QUE RECOJA MÁS DE UN BOTIN
        Botin[] botin = Mundo.botinCopia;
        Botin[] contenedor = Mundo.contenedorCopia;

        //DIbujamos las basuras que correspondan y los contenedores
        for(int i=0;i<Mundo.MUNDO_ALTO;i++){
            //DIBUJAMOS LAS BASURAS
            Pixmap stainPixmap = null;
            Pixmap contePixmap= null;

            if(botin[i].x>0){
                if(botin[i].tipo== Botin.BASURA0)
                    stainPixmap = Assets.basura0;
                if(botin[i].tipo == Botin.BASURA1)
                    stainPixmap = Assets.basura1;
                if(botin[i].tipo == Botin.BASURA2)
                    stainPixmap = Assets.basura2;
                if(botin[i].tipo == Botin.BASURA3)
                    stainPixmap = Assets.basura3;
                if(botin[i].tipo == Botin.BASURA4)
                    stainPixmap = Assets.basura4;

                int xb = botin[i].x * 32;
                int yb = botin[i].y * 32;
                g.drawPixmap(stainPixmap, xb, yb);
            }

            //DIBUJAMOS CONTENEDORES
            if(i<4){
                if(contenedor[i].tipo == Botin.CONTENEDOR1)
                    contePixmap = Assets.contenedor1;
                if(contenedor[i].tipo == Botin.CONTENEDOR2)
                    contePixmap = Assets.contenedor2;
                if(contenedor[i].tipo == Botin.CONTENEDOR3)
                    contePixmap = Assets.contenedor3;
                if(contenedor[i].tipo == Botin.CONTENEDOR4)
                    contePixmap = Assets.contenedor4;

                int xc = contenedor[i].x * 32;
                int yc = contenedor[i].y * 32;

                g.drawPixmap(contePixmap, xc, yc);
            }

        }

        /*
        int x = botin.x * 32;
        int y = botin.y * 32;
        g.drawPixmap(stainPixmap, x, y);

        Pixmap contenedor = null;

         */



        //DIbujamos las basuras que cargamos
        int len = jollyroger.partes.size();
        for(int i = 1; i < len; i++) {
            Tripulacion part = jollyroger.partes.get(i);
            int xp = part.x * 32;
            int yp = part.y * 32;
            g.drawPixmap(Assets.bolsa, xp, yp);
        }

        //Dibujamos nuestro basurero con la animación de caminar.
        animacion = (animacion+0.25)%6;
        int i = (int) animacion;
        Pixmap headPixmap = null;
        if(jollyroger.direccion == JollyRoger.ARRIBA)
            headPixmap = arriba[i];
        if(jollyroger.direccion == JollyRoger.IZQUIERDA)
            headPixmap = izq[i];
        if(jollyroger.direccion == JollyRoger.ABAJO)
            headPixmap = abajo[i];
        if(jollyroger.direccion == JollyRoger.DERECHA)
            headPixmap = der[i];
        int x = head.x * 32 + 16;
        int y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    private void drawReadyUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.preparado, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.botones, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.botones, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.botones, 256, 416, 0, 64, 64, 64);
    }

    private void drawPausedUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.menupausa, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graficos g = juego.getGraphics();

        g.drawPixmap(Assets.finjuego, 62, 100);
        g.drawPixmap(Assets.botones, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Graficos g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numeros, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    @Override
    public void pause() {
        if(estado == EstadoJuego.Ejecutandose)
            estado = EstadoJuego.Pausado;

        if(mundo.finalJuego) {
            Configuraciones.addScore(mundo.puntuacion);
            Configuraciones.save(juego.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
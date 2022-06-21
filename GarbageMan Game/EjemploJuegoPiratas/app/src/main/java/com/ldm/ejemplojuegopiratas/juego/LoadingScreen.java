package com.ldm.ejemplojuegopiratas.juego;

import com.ldm.ejemplojuegopiratas.Juego;
import com.ldm.ejemplojuegopiratas.Graficos;
import com.ldm.ejemplojuegopiratas.Pantalla;
import com.ldm.ejemplojuegopiratas.Graficos.PixmapFormat;

public class LoadingScreen extends Pantalla{
    public LoadingScreen(Juego juego) {
        super(juego);
    }

    @Override
    public void update(float deltaTime) {
        Graficos g = juego.getGraphics();
        Assets.fondo = g.newPixmap("fondo.png", PixmapFormat.RGB565);
        Assets.fondoAyuda = g.newPixmap("fondoAyuda.png", PixmapFormat.RGB565);
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.ayuda4 = g.newPixmap("ayuda4.png", PixmapFormat.ARGB4444);
        Assets.ayuda5 = g.newPixmap("ayuda5.png", PixmapFormat.ARGB4444);
        Assets.ayuda6 = g.newPixmap("ayuda6.png", PixmapFormat.ARGB4444);
        Assets.ayuda7 = g.newPixmap("ayuda7.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.ogg");
        Assets.basuraSon = juego.getAudio().nuevoSonido("basura.ogg");
        Assets.contenedorSon = juego.getAudio().nuevoSonido("contenedor.ogg");
        Assets.reloj1 = juego.getAudio().nuevoSonido("reloj1.ogg");
        Assets.reloj2 = juego.getAudio().nuevoSonido("reloj2.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");

        Assets.abajo1 = g.newPixmap("abajo (1).png",PixmapFormat.ARGB4444);
        Assets.abajo2 = g.newPixmap("abajo (2).png",PixmapFormat.ARGB4444);
        Assets.abajo3 = g.newPixmap("abajo (3).png",PixmapFormat.ARGB4444);
        Assets.abajo4 = g.newPixmap("abajo (4).png",PixmapFormat.ARGB4444);

        Assets.arriba1 = g.newPixmap("arriba (1).png",PixmapFormat.ARGB4444);
        Assets.arriba2 = g.newPixmap("arriba (2).png",PixmapFormat.ARGB4444);
        Assets.arriba3 = g.newPixmap("arriba (3).png",PixmapFormat.ARGB4444);
        Assets.arriba4 = g.newPixmap("arriba (4).png",PixmapFormat.ARGB4444);

        Assets.der1 = g.newPixmap("der (1).png",PixmapFormat.ARGB4444);
        Assets.der2 = g.newPixmap("der (2).png",PixmapFormat.ARGB4444);
        Assets.der3 = g.newPixmap("der (3).png",PixmapFormat.ARGB4444);
        Assets.der4 = g.newPixmap("der (4).png",PixmapFormat.ARGB4444);

        Assets.izq1 = g.newPixmap("izq (1).png",PixmapFormat.ARGB4444);
        Assets.izq2 = g.newPixmap("izq (2).png",PixmapFormat.ARGB4444);
        Assets.izq3 = g.newPixmap("izq (3).png",PixmapFormat.ARGB4444);
        Assets.izq4 = g.newPixmap("izq (4).png",PixmapFormat.ARGB4444);

        Assets.contenedor1 = g.newPixmap("contenedor1.png",PixmapFormat.ARGB4444);
        Assets.contenedor2 = g.newPixmap("contenedor2.png",PixmapFormat.ARGB4444);
        Assets.contenedor3 = g.newPixmap("contenedor3.png",PixmapFormat.ARGB4444);
        Assets.contenedor4 = g.newPixmap("contenedor4.png",PixmapFormat.ARGB4444);

        Assets.basura0 = g.newPixmap("basura0.png",PixmapFormat.ARGB4444);
        Assets.basura1 = g.newPixmap("basura1.png",PixmapFormat.ARGB4444);
        Assets.basura2 = g.newPixmap("basura2.png",PixmapFormat.ARGB4444);
        Assets.basura3 = g.newPixmap("basura3.png",PixmapFormat.ARGB4444);
        Assets.basura4 = g.newPixmap("basura4.png",PixmapFormat.ARGB4444);

        Assets.bolsa = g.newPixmap("bolsa.png",PixmapFormat.ARGB4444);



        Configuraciones.cargar(juego.getFileIO());
        juego.setScreen(new MainMenuScreen(juego));
    }

    @Override
    public void present(float deltaTime) {

    }

    @Override
    public void pause() {

    }


    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
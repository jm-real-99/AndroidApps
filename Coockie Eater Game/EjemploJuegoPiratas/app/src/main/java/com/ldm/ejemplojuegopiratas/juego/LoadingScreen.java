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
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.menuprincipal = g.newPixmap("menuprincipal.png", PixmapFormat.ARGB4444);
        Assets.botones = g.newPixmap("botones.png", PixmapFormat.ARGB4444);
        Assets.ayuda1 = g.newPixmap("ayuda1.png", PixmapFormat.ARGB4444);
        Assets.ayuda2 = g.newPixmap("ayuda2.png", PixmapFormat.ARGB4444);
        Assets.ayuda3 = g.newPixmap("ayuda3.png", PixmapFormat.ARGB4444);
        Assets.ayuda4 = g.newPixmap("ayuda4.png", PixmapFormat.ARGB4444);
        Assets.ayuda5 = g.newPixmap("ayuda5.png", PixmapFormat.ARGB4444);
        Assets.numeros = g.newPixmap("numeros.png", PixmapFormat.ARGB4444);
        Assets.preparado = g.newPixmap("preparado.png", PixmapFormat.ARGB4444);
        Assets.menupausa = g.newPixmap("menupausa.png", PixmapFormat.ARGB4444);
        Assets.finjuego = g.newPixmap("finjuego.png", PixmapFormat.ARGB4444);
        Assets.barcoarriba = g.newPixmap("mg_arriba.png", PixmapFormat.ARGB4444);
        Assets.barcoizquierda = g.newPixmap("mg_izquierda.png", PixmapFormat.ARGB4444);
        Assets.barcoabajo = g.newPixmap("mg_abajo.png", PixmapFormat.ARGB4444);
        Assets.barcoderecha = g.newPixmap("mg_derecha.png", PixmapFormat.ARGB4444);

        Assets.comiendoaariba = g.newPixmap("mgc_arriba.png", PixmapFormat.ARGB4444);
        Assets.comiendoizquierda = g.newPixmap("mgc_izquierda.png", PixmapFormat.ARGB4444);
        Assets.comiendoabajo = g.newPixmap("mgc_abajo.png", PixmapFormat.ARGB4444);
        Assets.comiendoderecha = g.newPixmap("mgc_derecha.png", PixmapFormat.ARGB4444);


        Assets.tripulacion = g.newPixmap("tripulacion.png", PixmapFormat.ARGB4444);
        Assets.botin1 = g.newPixmap("botin1.png", PixmapFormat.ARGB4444);
        Assets.botin2 = g.newPixmap("botin2.png", PixmapFormat.ARGB4444);
        Assets.botin3 = g.newPixmap("botin3.png", PixmapFormat.ARGB4444);
        Assets.botin4 = g.newPixmap("botin4.png", PixmapFormat.ARGB4444);
        Assets.muro = g.newPixmap("muro.png", PixmapFormat.ARGB4444);
        Assets.pulsar = juego.getAudio().nuevoSonido("pulsar.ogg");
        Assets.ataque = juego.getAudio().nuevoSonido("ataque.ogg");
        Assets.derrota = juego.getAudio().nuevoSonido("derrota.ogg");
        Assets.veneno = juego.getAudio().nuevoSonido("veneno.ogg");


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
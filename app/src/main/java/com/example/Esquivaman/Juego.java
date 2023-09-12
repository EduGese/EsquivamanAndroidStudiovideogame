package com.example.Esquivaman;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class Juego extends AppCompatActivity {

    private VistaJuego vj;
    private MediaPlayer cancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       
        cancion= MediaPlayer.create(Juego.this,R.raw.musica);
        cancion.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.juego);
        vj = findViewById(R.id.VistaJuego);
        vj.setPadre(this);
    }

   
    @Override
    public void onPause(){
        super.onPause();
        cancion.release();

    }

}

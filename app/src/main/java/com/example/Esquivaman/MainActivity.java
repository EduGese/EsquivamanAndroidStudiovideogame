package com.example.Esquivaman;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button jugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         jugar=(Button)findViewById(R.id.jugar);
         jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarjuego();
            }
        });
    }

    public void lanzarjuego(){
        Intent i =new Intent(MainActivity.this,Juego.class);
        startActivityForResult(i, 1234);
    }

    public void lanzarGameOver(int puntuacion){
        Intent i = new Intent(MainActivity.this, GameOver.class);
        i.putExtra("puntuacion2", puntuacion);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234 && resultCode==RESULT_OK && data!=null){
            int puntuacion = data.getIntExtra("puntuacion", 0);
            lanzarGameOver(puntuacion);
        }
    }
}
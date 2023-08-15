package com.example.Esquivaman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    private TextView tv_score;
    private int points;
    private Button bt_jugardenuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        tv_score=findViewById(R.id.score);
       points=getIntent().getIntExtra("puntuacion2", 0);
        bt_jugardenuevo = findViewById(R.id.bt_jugardenuevo);
        tv_score.setText("Puntuación: "+points+" segundos");

        bt_jugardenuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


}
package com.example.Esquivaman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

public class VistaJuego extends View {

    private Activity padre;
    private long primerAhora;
    private List<Graficos>lavadoras;
    private int numLavadoras=4;
    private int puntuacion;
    private Graficos esquivaman;

   
    float movedY=0;



    // Thread encargado de procesar el juego
    private ThreadJuego thread = new ThreadJuego();
    //Cada cuanto queremos procesar cambio(ms)
    private static int periodoActualizacion =50;
    //Cuando se realizó el último proceso
    private long ultimoPrceso=0;



    /public void setPadre(Activity padre) {
        this.padre = padre;
    }

    public VistaJuego(Context context, AttributeSet attrs) {
        super(context, attrs);

        Drawable drawableLavadora, drawableEsquivaman;
        drawableLavadora= AppCompatResources.getDrawable(context, R.drawable.lavadora1);
        drawableEsquivaman= AppCompatResources.getDrawable(context,R.drawable.esquivaman);

        
        esquivaman= new Graficos(this,drawableEsquivaman);
        lavadoras = new ArrayList<Graficos>();
       
        puntuacion=0;
        for (int i = 0; i < numLavadoras; i++) {
            Graficos lavadora =new Graficos(this, drawableLavadora);
            lavadora.setIncY(Math.random()*10-5);
            lavadora.setIncX(Math.random()*5-10);
            lavadora.setAngulo((int)(Math.random()*178+91));
            lavadora.setRotacion((int)(Math.random()*10-4)); 
            lavadoras.add(lavadora);}
    }

    @Override protected void onSizeChanged(int ancho,int alto,int ancho_anter,int alto_anter) {
        super.onSizeChanged(ancho,alto,ancho_anter,alto_anter);
       
        esquivaman.setCenX(200);
        esquivaman.setCenY(alto/2);

        for (int i=0;i<lavadoras.size();i++) {

            lavadoras.get(i).setCenX(ancho);//Coloca las lavadoras a la derecha
            lavadoras.get(i).setCenY((int) (Math.random() * alto));//Posiciona la lavadora en una posicion aleatoria del eje Y
            if(separarLavadoras(i,lavadoras)==-1){
                i--;
            }
        }
        thread.start();
    }

    @Override synchronized protected void onDraw (Canvas canvas){//Dibuja los objetos
        super.onDraw(canvas);
        for(Graficos lavadora: lavadoras){
            lavadora.dibujarGraficos(canvas);
        }
        esquivaman.dibujarGraficos(canvas);
    }

    @Override public boolean onTouchEvent (MotionEvent event) {//Metodo que hace que el usuario controle a esquivamen con los dedos
        super.onTouchEvent(event);

        float yDown = event.getY();

        if(event.getAction()==MotionEvent.ACTION_MOVE){

            
            float distancey = yDown-movedY;
            esquivaman.setCenY(esquivaman.getCenY()+(int)distancey);
        }
        movedY=yDown;
        return true;
    }

     synchronized protected void actualizaFisica(){
        //Para calcular los segundos que consigue el usuario (puntuación)
        if(ultimoPrceso==0){
            primerAhora = System.currentTimeMillis();
        }
        long ahora = System.currentTimeMillis();

        //Salir si el periodo de proceso no se ha cumplido
        if(ultimoPrceso+ periodoActualizacion >ahora){

            return;
        }

        
        
        double factorMov= (ahora-ultimoPrceso)/ periodoActualizacion;
        ultimoPrceso=ahora; 

        
        esquivaman.incrementaPos(factorMov);
        for (Graficos lavadora :lavadoras){
            lavadora.incrementaPos(factorMov);
            ultimoPrceso=System.currentTimeMillis();
            puntuacion = (int)(ultimoPrceso-primerAhora)/1000;
            lavadora.setAngulo((int)(Math.random()*360));

            if (puntuacion>10){
                lavadora.incrementaPos(factorMov*1.2);
                lavadora.setAngulo((int)(Math.random()*360));
            }
            if (puntuacion>20){
                lavadora.incrementaPos(factorMov*1.8);
                lavadora.setAngulo((int)(Math.random()*360));
            }
        }
        
        for ( int i=0;i<lavadoras.size();i++){
            if(esquivaman.verificaColision(lavadoras.get(i))){//Cuando colisiona
                   Intent data = new Intent();
                   ultimoPrceso= System.currentTimeMillis();
                   data.putExtra("puntuacion", puntuacion);
                   padre.setResult(Activity.RESULT_OK, data);
                   padre.finish();
            }
        }
    }

    public int separarLavadoras(int indice, List<Graficos> lista){
        for (int i = 0; i < lista.size(); i++) {
            if(indice==i){
            }else{
                if(lista.get(indice).distancia(lista.get(i))<lista.get(indice).getRadioColision()){
                    return -1;
                }
            }
        }

        return 1;
    }

     class ThreadJuego extends Thread{//Se crea un segundo hilo de ejecucion
        @Override
        public  void run(){
            while (true){
                actualizaFisica();
            }
        }
    }
}

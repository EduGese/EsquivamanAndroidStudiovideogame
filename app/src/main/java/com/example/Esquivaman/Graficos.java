package com.example.Esquivaman;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

public class Graficos {
    private Drawable drawable;//Imagen que dibujaremos
    private int cenX,cenY;//Posicion en el centro del gráfico
    private int ancho,alto;//DImesiones de la imagen
    private double incX,incY;//Velocidad de desplazamiento
    private double angulo,rotacion;//Ángulo y velocidad rotación
    private int radioColision;//Para determinar colisión
    private int xAnterior,yAnterior;//Posición anterior
    private int radioInval;//Radio usado en ivalidate()
    private View view;//Usada en view.invalidate()


    public Graficos(View view, Drawable drawable){
        this.view= view;
        this.drawable=drawable;
        ancho=drawable.getIntrinsicWidth();
        alto=drawable.getIntrinsicHeight();
        radioColision=(alto+ancho)/6;
        radioInval=(int)Math.hypot(ancho/2,alto/2);
    }
    public void dibujarGraficos(Canvas canvas){
        int x= (int)(cenX-ancho/2);
        int y= (int)(cenY-alto/2);
        drawable.setBounds(x,y,x+ancho,y+alto);
        canvas.save();
        canvas.rotate((float)angulo,cenX,cenY);
        drawable.draw(canvas);
        canvas.restore();
        xAnterior=cenX;
        yAnterior=cenY;

    }
    public void incrementaPos(double factor){
        cenX+=incX*factor;
        cenY+=incY*factor;
        angulo+=rotacion*factor;
        if (cenX<0)
            cenX=view.getWidth();
        if (cenX>view.getWidth())
            cenX=0;
        if (cenY<0)
            cenY=view.getHeight();
        if (cenY>view.getHeight())
            cenY=0;
        view.postInvalidate(cenX-radioInval,cenY-radioInval,cenX+radioInval,cenY+radioInval);
        view.postInvalidate(xAnterior-radioInval,yAnterior-radioInval,xAnterior+radioInval,yAnterior+radioInval);
    }
    public double distancia(Graficos g){
        return Math.hypot(cenX-g.cenX,cenY-g.cenY);
    }

    public boolean verificaColision(Graficos g){
        return (distancia(g)<(radioColision+g.radioColision));
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public int getCenX() {
        return cenX;
    }

    public void setCenX(int cenX) {
        this.cenX = cenX;
    }

    public int getCenY() {
        return cenY;
    }

    public void setCenY(int cenY) {
        this.cenY = cenY;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public double getIncX() {
        return incX;
    }

    public void setIncX(double incX) {
        this.incX = incX;
    }

    public double getIncY() {
        return incY;
    }

    public void setIncY(double incY) {
        this.incY = incY;
    }

    public double getAngulo() {
        return angulo;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public double getRotacion() {
        return rotacion;
    }

    public void setRotacion(double rotacion) {
        this.rotacion = rotacion;
    }

    public int getRadioColision() {
        return radioColision;
    }

    public void setRadioColision(int radioColision) {
        this.radioColision = radioColision;
    }

    public int getxAnterior() {
        return xAnterior;
    }

    public void setxAnterior(int xAnterior) {
        this.xAnterior = xAnterior;
    }

    public int getyAnterior() {
        return yAnterior;
    }

    public void setyAnterior(int yAnterior) {
        this.yAnterior = yAnterior;
    }

    public int getRadioInval() {
        return radioInval;
    }

    public void setRadioInval(int radioInval) {
        this.radioInval = radioInval;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}

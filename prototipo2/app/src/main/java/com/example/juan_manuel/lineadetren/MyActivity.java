package com.example.juan_manuel.lineadetren;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;


public class MyActivity extends Activity{

    public ImageView imagen;

    int listaIds [] = {R.id.inicio,R.id.linea, R.id.medio,R.id.linea1 ,R.id.medio1,R.id.linea2 ,R.id.medio2,R.id.linea3 ,R.id.medio3,
            R.id.linea4 ,R.id.medio4, R.id.linea5 ,R.id.medio5,R.id.linea6 ,R.id.meta};
    int checkIds [] = {R.drawable.check_inicio,R.drawable.check_linea, R.drawable.check_medio, R.drawable.check_meta };

    static int contador =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        imagen = (ImageView) findViewById(listaIds[contador]);

    }

    public boolean onTouchEvent(MotionEvent event){
        // contador == listaIds.length es para que no haga el evento cuando ya llegó al final
        if(event.getAction()==MotionEvent.ACTION_DOWN && contador < listaIds.length){

            if(contador == 0){
                imagen.setImageResource(R.drawable.check_inicio);
                imagen= (ImageView)findViewById(listaIds[contador+1]);
                contador += 1;

            }

            else if(contador != 0){

                //para el caso ultimo
                if(contador == listaIds.length-2){
                    imagen.setImageResource(checkIds[1]);
                    imagen = (ImageView) findViewById(listaIds[contador +1]);

                    imagen.setImageResource(checkIds[3]);
                    imagen = (ImageView) findViewById(listaIds[contador + 1]);
                    contador += 2;

                }

                else {
                    imagen.setImageResource(checkIds[1]);
                    imagen = (ImageView) findViewById(listaIds[contador + 1]);

                    imagen.setImageResource(checkIds[2]);
                    imagen = (ImageView) findViewById(listaIds[contador + 2]);

                    contador += 2;
                }
            }
        }
        return false;
    }

}



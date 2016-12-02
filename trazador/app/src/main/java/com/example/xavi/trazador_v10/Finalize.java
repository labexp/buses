package com.example.xavi.trazador_v10;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class Finalize extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton guardar = (FloatingActionButton) findViewById(R.id.btn_guardar);
        FloatingActionButton cancelar = (FloatingActionButton) findViewById(R.id.btn_cancelar);



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.state = 0;
                Toast.makeText(getBaseContext(), "Fin de ruta", Toast.LENGTH_SHORT).show();
                MainActivity.btnInicio.setBackgroundResource(R.drawable.btn_iniciar);

                MainActivity.trace.finished();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                clearMapMarks();
                finish();

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.state = 0;
                Toast.makeText(getBaseContext(), "Fin de ruta", Toast.LENGTH_SHORT).show();
                MainActivity.btnInicio.setBackgroundResource(R.drawable.btn_iniciar);
                clearMapMarks();
                MainActivity.trace.discarded();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    private void clearMapMarks() {
        for (int x = 0; x< MainActivity.points.size() ; x++ ){
            MainActivity.map.getOverlays().remove(MainActivity.points.get(x));
        }
        MainActivity.points.clear();
    }

}

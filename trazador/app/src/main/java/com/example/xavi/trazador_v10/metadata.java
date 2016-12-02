package com.example.xavi.trazador_v10;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class metadata extends AppCompatActivity {

    EditText Codigo;
    EditText Nombre;
    EditText Costo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metadata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton btnGuardar;
        FloatingActionButton btnCancelar;
        Codigo = (EditText) findViewById(R.id.editText);
        Nombre = (EditText) findViewById(R.id.editText2);
        Costo = (EditText) findViewById(R.id.editText3);

        btnGuardar = (FloatingActionButton) findViewById(R.id.btn_guardar);
        btnCancelar = (FloatingActionButton) findViewById(R.id.btn_cancelar);

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                String textCodigo = Codigo.getText().toString();
                String textNombre = Nombre.getText().toString();
                String textCosto = Costo.getText().toString();
                MainActivity.trace.setMetadata(textCodigo, textNombre, textCosto);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();


            }

        });

    }

}

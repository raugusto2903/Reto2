package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Categorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

    }
    public void gotoPrincipal(View view){
        Intent intent=new Intent(Categorias.this,Principal.class);
        startActivity(intent);
    }
    public void gotoReciclaVidrio(View view){
        Intent intent=new Intent(Categorias.this,Estadistica.class);
        intent.putExtra("mensaje", "Vidrio");
        startActivity(intent);
    }
    public void gotoReciclaPlastico(View view){
        Intent intent=new Intent(Categorias.this,Estadistica.class);
        intent.putExtra("mensaje", "Plastico");
        startActivity(intent);
    }
    public void gotoReciclaPapel(View view){
        Intent intent=new Intent(Categorias.this,Estadistica.class);
        intent.putExtra("mensaje", "Papel");
        startActivity(intent);
    }

}
package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    Button consume,statistics,recommendations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        consume=findViewById(R.id.btConsumo);
        statistics=findViewById(R.id.btEstadisticas);
        recommendations=findViewById(R.id.btConsejos);

        //Recibir los datos enviados en un Intent desde otro Activity
        Intent receive= getIntent();
        String idUser= receive.getStringExtra("idUser");

        Intent consumeView= new Intent(getApplicationContext(),
                Categorias.class);
        consumeView.putExtra("idUser",idUser);

        Intent statistics_view= new Intent(getApplicationContext(),
                Estadistica.class);
        statistics_view.putExtra("idUser",idUser);

        Intent recommendations_view= new Intent(getApplicationContext(),
                Consejos.class);

        consume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(consumeView);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(statistics_view);
            }
        });

        recommendations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(recommendations_view);
            }
        });






    }


    public void gotologin(View view){
        Intent intent=new Intent(Principal.this,MainActivity.class);
        startActivity(intent);
    }

    public void gotoCategorias(View view){
        Intent intent=new Intent(Principal.this,Categorias.class);
        startActivity(intent);
    }

    public void gotoEstadistica(View view){
        Intent intent=new Intent(Principal.this,Estadistica.class);
        intent.putExtra("mensaje", "Vidrio");
        startActivity(intent);
    }

    public void gotoConsejos(View view){
        Intent intent=new Intent(Principal.this,Consejos.class);
        startActivity(intent);
    }
}
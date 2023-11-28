package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reto1.R;
import com.example.reto1.models.VidrioM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Plastico extends AppCompatActivity {
    EditText quantity,price;
    Spinner month;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plastico);
        quantity=findViewById(R.id.etPlasticoQuantity);
        price=findViewById(R.id.etPricePlastico);
        month=findViewById(R.id.spinnerMonthPLastico);
        register=findViewById(R.id.btRegisterPLastico);
        Intent receive= getIntent();
        String idUser= receive.getStringExtra("idUser");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText().toString().isEmpty() ||
                        price.getText().toString().isEmpty() ||
                        month.getSelectedItem().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Todos los campos deben diligenciarse",Toast.LENGTH_LONG).show();
                }else{
                    int quantityVidrio= Integer.parseInt(quantity.getText().toString());
                    int priceVidrio= Integer.parseInt(price.getText().toString());
                    String monthVidrio= month.getSelectedItem().toString();
                    String serial= idUser+monthVidrio;
                    VidrioM consumeVidrio = new VidrioM(serial,quantityVidrio,priceVidrio,monthVidrio,idUser);
                    registerVidrio(consumeVidrio);
                    Toast.makeText(getApplicationContext(),"Registro exitoso",
                            Toast.LENGTH_LONG).show();
                    cleanView();
                }
            }
        });
    }
    public void registerVidrio(VidrioM consume){

        //Definir la dirección y nombre del archivo
        File waterFile= new File(getFilesDir(),"water.txt");

        try {
            FileWriter writer= new FileWriter(waterFile,true);
            BufferedWriter bufferedWriter= new BufferedWriter(writer);
            bufferedWriter.write(
                    consume.getSERIAL()+","+
                            consume.getQuantity()+","+
                            consume.getPrice()+","+
                            consume.getMonth()+","+
                            consume.getIdUser()
            );
            bufferedWriter.newLine();
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void cleanView(){
        quantity.setText("");
        price.setText("");
        month.setSelection(0);
    }
}
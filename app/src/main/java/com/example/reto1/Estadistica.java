package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Estadistica extends AppCompatActivity {

    String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    String[] arraycategorias = {"Vidrio", "Plastico", "Papel"};
    List<TipoMaterial> tipoMaterials = new ArrayList<TipoMaterial>();

    TextView mes, cantidad, valor;
    Button enviar, promediar, menor, mayor;
    TextView totalValue;
    TableLayout table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String prueba = intent.getStringExtra("mensaje");
        Log.d("Reto1", "prueba  = " + prueba);
        setContentView(R.layout.activity_estadistica);
        RadioGroup recicle = findViewById(R.id.recicle);
        RadioButton radioButtonV = findViewById(R.id.rbVidrio);
        RadioButton radioButtonPl = findViewById(R.id.rbPlastico);
        RadioButton radioButtonP = findViewById(R.id.rbPapel);
        mes = findViewById(R.id.tvMes);
        cantidad = findViewById(R.id.tvCantidad);
        valor = findViewById(R.id.tvValor);
        mes.requestFocus();
        cantidad.requestFocus();
        valor.requestFocus();

        enviar = findViewById(R.id.btEnviar);

        for (int i = 0; i < arraycategorias.length; i++) {
            TipoMaterial aux = new TipoMaterial(arraycategorias[i]);
            if (prueba.equals(arraycategorias[i])) {
                aux.setActivo(true);
                if (i == 0) {
                    radioButtonV.setChecked(true);
                    radioButtonPl.setChecked(false);
                    radioButtonP.setChecked(false);
                }
                if (i == 1) {
                    radioButtonV.setChecked(false);
                    radioButtonPl.setChecked(true);
                    radioButtonP.setChecked(false);
                }
                if (i == 2) {
                    radioButtonV.setChecked(false);
                    radioButtonPl.setChecked(true);
                    radioButtonP.setChecked(true);
                }
            }

            Log.d("Reto1", "activo  = " + aux.isActivo());
            tipoMaterials.add(aux);
            radioButtonV.setEnabled(false);
            radioButtonPl.setEnabled(false);
            radioButtonP.setEnabled(false);
        }


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mes = mes.getText().toString();
                String precio = cantidad.getText().toString();
                String cantidad = valor.getText().toString();
                int selectedRadioButtonId = recicle.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedText = selectedRadioButton.getText().toString();
                if (Mes.isEmpty() || precio.isEmpty() || cantidad.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Todos los campos deben diligenciarse", Toast.LENGTH_LONG).show();
                } else {
                    //tipoMaterials
                    double valorNew = Double.parseDouble(precio);
                    double cantidadNew = Double.parseDouble(cantidad);
                    boolean bandValidaMes = false;
                    boolean bandNoRepite = false;
                    Log.d("Reto1", "prueba  = " + Mes);
                    Log.d("Reto1", "prueba  = " + valorNew);
                    Log.d("Reto1", "prueba  = " + cantidadNew);
                    Log.d("Reto1", "radiobuton  = " + selectedText);
                    for (TipoMaterial index : tipoMaterials) {
                        if (index.getNombreTipo().equals(selectedText)) {
                            for (String mesAux : meses) {
                                if (Mes.equals(mesAux)) {
                                    bandValidaMes = true;
                                }
                            }
                            if (bandValidaMes) {
                                for(MesCantidadValor indexMes:index.listaMesesCantidad){
                                    if(indexMes.getNombre().equals(Mes)){
                                        bandNoRepite = true;
                                    }
                                }
                                if(!bandNoRepite){
                                    MesCantidadValor aux = new MesCantidadValor();
                                    aux.setNombre(Mes);
                                    aux.setCantidad(cantidadNew);
                                    aux.setValor(valorNew);
                                    index.listaMesesCantidad.add(aux);
                                    llenarTabla(tipoMaterials);
                                    limpiar();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),
                                            "el mes ya esta digitado", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),
                                        "debe digitar cualquier mes como estos Enero, Febrero, Marzo, Abril, Mayo, Junio,\n" +
                                                "Julio, Agosto, Septiembre, Octubre, Noviembre, Diciembre", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }


            }
        });

    }

    public void gotoCategorias(View view) {
        Intent intent = new Intent(Estadistica.this, Categorias.class);
        startActivity(intent);
    }

    public void llenarTabla(List<TipoMaterial> tipoMaterials){
        borrarFilas();
        TableLayout  tableLayout = findViewById(R.id.tableProducts);
        RadioGroup recicle = findViewById(R.id.recicle);
        int selectedRadioButtonId = recicle.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String selectedText = selectedRadioButton.getText().toString();
        for(TipoMaterial index:tipoMaterials){
            if(index.isActivo() && index.getNombreTipo().equals(selectedText)){
                for(MesCantidadValor index2:index.listaMesesCantidad){
                    double valortotal= index2.getCantidad()*index2.getValor();

                    TableRow fila= new TableRow(this);
                    TextView celda1= new TextView(this);
                    celda1.setText(index2.getNombre());
                    celda1.setWidth(110);
                    celda1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    fila.addView(celda1);

                    TextView celda2= new TextView(this);
                    celda2.setText(index2.getCantidad()+"");
                    celda2.setWidth(85);
                    celda2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    fila.addView(celda2);

                    TextView celda3=new TextView(this);
                    celda3.setText(index2.getValor()+"");
                    celda3.setWidth(80);
                    celda3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    fila.addView(celda3);

                    TextView celda4=new TextView(this);
                    celda4.setText(valortotal+"");
                    celda4.setWidth(85);
                    celda4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    fila.addView(celda4);
                    tableLayout.addView(fila);
                }
            }
        }
    }

    public void limpiar(){
        mes.setText("");
        cantidad.setText("");
        valor.setText("");
    }

    private void borrarFilas() {
        // Obtén el número total de filas en la tabla
        TableLayout  tableLayout = findViewById(R.id.tableProducts);
        int numRows = tableLayout.getChildCount();

        // Si hay más de una fila (excluyendo la primera fila de títulos)
        if (numRows > 1) {
            // Comienza desde la última fila y elimina cada fila excepto la primera
            for (int i = numRows - 1; i > 0; i--) {
                tableLayout.removeViewAt(i);
            }
        }
    }

    public void promedio (View v) {
        RadioGroup recicle = findViewById(R.id.recicle);
        int selectedRadioButtonId = recicle.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String selectedText = selectedRadioButton.getText().toString();
        double promedio=0;
        for(TipoMaterial index: tipoMaterials){
            if(index.getNombreTipo().equals(selectedText)){
                double acum=0;
                for(MesCantidadValor index2: index.listaMesesCantidad){
                    double valortotal=index2.getCantidad() * index2.getValor();
                    acum += valortotal;
                }
                promedio = acum /index.listaMesesCantidad.size();
            }
        }
        TextView textView1;
        textView1 = findViewById(R.id.tvPromedio);
        textView1.setText(""+promedio);
    }

    public void buscarMenor (View v) {
        RadioGroup recicle = findViewById(R.id.recicle);
        int selectedRadioButtonId = recicle.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String selectedText = selectedRadioButton.getText().toString();
        String mesMenor="";
        double menor=1000;
        for(TipoMaterial index: tipoMaterials){
            if(index.getNombreTipo().equals(selectedText)){

                for(MesCantidadValor index2: index.listaMesesCantidad){
                    if(menor > index2.getValor()){
                        menor=index2.getValor();
                        mesMenor=index2.getNombre();
                    }
                }
            }
        }
        TextView textView1;
        textView1 = findViewById(R.id.tvMenor);
        textView1.setText(""+menor);
    }

    public void buscarMayor (View v) {
        RadioGroup recicle = findViewById(R.id.recicle);
        int selectedRadioButtonId = recicle.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String selectedText = selectedRadioButton.getText().toString();
        String mesMayor="";
        double mayor=0;
        for(TipoMaterial index: tipoMaterials){
            if(index.getNombreTipo().equals(selectedText)){

                for(MesCantidadValor index2: index.listaMesesCantidad){
                    if(mayor < index2.getValor()){
                        mayor=index2.getValor();
                        mesMayor=index2.getNombre();
                    }
                }
            }
        }
        TextView textView1;
        textView1 = findViewById(R.id.tvMayor);
        textView1.setText(""+mayor);
    }


}
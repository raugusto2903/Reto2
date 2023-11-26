package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto1.models.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button home;
    TextView register;

    EditText user,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home=findViewById(R.id.btEntrar);
        register=findViewById(R.id.btPassw);
        user=findViewById(R.id.etEmail);
        password=findViewById(R.id.etPassword);
        Intent home_activity=new Intent(getApplicationContext(),
                Principal.class);
        Intent register_activity= new Intent(getApplicationContext(),
                Registro.class);

        File fileUser= new File(getFilesDir(),"user.txt");

        ArrayList<User> users= listUser(fileUser);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(register_activity);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state= false;
                if(user.getText().toString().isEmpty() ||
                        password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Ambos campos deben estar diligenciados",Toast.LENGTH_LONG).show();
                }else{
                    String userLogin= user.getText().toString();
                    for (User i:users){
                        if (i.getName().equals(userLogin) ||
                                i.getEmail().equals(userLogin) ||
                                i.getPhone().equals(userLogin)){
                            state=true;
                            if (i.getPassword().equals(password.getText().toString())){
                                //Para enviar datos entre activitys se usan los intent por medio del metodo putExtra
                                //en el cuál le adicionamos una clave, y una valor a enviar.
                                home_activity.putExtra("idUser",i.getId());
                                startActivity(home_activity);
                                break;
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "La contraseña es incorrecta",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    if (!state){
                        Toast.makeText(getApplicationContext(),
                                "El usuario no esta registrado",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }


    public ArrayList<User> listUser(File data){
        ArrayList<User> list= new ArrayList<>();

        try {
            //Para leer datos de un archivo plano se usa el FileReader
            FileReader reader= new FileReader(data);
            BufferedReader bufferedReader= new BufferedReader(reader);
            String user;

            while ((user=bufferedReader.readLine())!=null){
                String[] userData= user.split(",");
                String id= userData[0];
                String name= userData[1];
                String email=userData[2];
                String phone= userData[3];
                String password= userData[4];

                User userObject= new User(id,name,email,phone,password);
                list.add(userObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }


}
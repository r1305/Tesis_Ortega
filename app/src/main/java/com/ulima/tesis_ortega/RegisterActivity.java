package com.ulima.tesis_ortega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    Button signin,clean;
    EditText nombre,apellido,correo,psw,fecha,peso,altura;
    Spinner sexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signin=(Button)findViewById(R.id.sign_btn);
        clean=(Button)findViewById(R.id.sign_limpiar);
        nombre=(EditText)findViewById(R.id.sign_nombre);
        apellido=(EditText)findViewById(R.id.sign_apellido);
        correo=(EditText)findViewById(R.id.sign_correo);
        psw=(EditText)findViewById(R.id.sign_psw);
        fecha=(EditText)findViewById(R.id.sign_fec_nac);
        peso=(EditText)findViewById(R.id.sign_peso);
        altura=(EditText)findViewById(R.id.sign_altura);
        sexo=(Spinner)findViewById(R.id.sign_sexo);

        List<String> gender=new ArrayList<>();
        gender.add("Masculino");
        gender.add("Femenino");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gender);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(dataAdapter);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                RegisterActivity.this.finish();
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre.setText("");
                apellido.setText("");
                correo.setText("");
                psw.setText("");
                fecha.setText("");
                peso.setText("");
                altura.setText("");
            }
        });
    }
}

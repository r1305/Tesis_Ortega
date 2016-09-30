package com.ulima.tesis_ortega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText user,clave;
    Button login,registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=(EditText)findViewById(R.id.login_usuario);
        clave=(EditText)findViewById(R.id.login_clave);
        login=(Button)findViewById(R.id.btn_login);
        registro=(Button)findViewById(R.id.btn_sign_up);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,Main2Activity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });

    }
}

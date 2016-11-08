package com.ulima.tesis_ortega;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ulima.tesis_ortega.Utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button signin,clean;
    EditText nombre,apellido,correo,psw,fecha,peso,altura;
    Spinner sexo;
    SessionManager session;
    ProgressDialog pDialog;
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
//        fecha=(EditText)findViewById(R.id.sign_fec_nac);
        peso=(EditText)findViewById(R.id.sign_peso);
        altura=(EditText)findViewById(R.id.sign_altura);
//        sexo=(Spinner)findViewById(R.id.sign_sexo);

//        TextWatcher tw=new TextWatcher() {
//            private String current = "";
//            private String ddmmyyyy = "DDMMYYYY";
//            private Calendar cal = Calendar.getInstance();
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().equals(current)) {
//                    String clean = s.toString().replaceAll("[^\\d.]", "");
//                    String cleanC = current.replaceAll("[^\\d.]", "");
//
//                    int cl = clean.length();
//                    int sel = cl;
//                    for (int i = 2; i <= cl && i < 6; i += 2) {
//                        sel++;
//                    }
//                    //Fix for pressing delete next to a forward slash
//                    if (clean.equals(cleanC)) sel--;
//
//                    if (clean.length() < 8){
//                        clean = clean + ddmmyyyy.substring(clean.length());
//                    }else{
//                        //This part makes sure that when we finish entering numbers
//                        //the date is correct, fixing it otherwise
//                        int day  = Integer.parseInt(clean.substring(0,2));
//                        int mon  = Integer.parseInt(clean.substring(2,4));
//                        int year = Integer.parseInt(clean.substring(4,8));
//
//                        if(mon > 12) mon = 12;
//                        cal.set(Calendar.MONTH, mon-1);
//                        year = (year<1900)?1900:(year>2100)?2100:year;
//                        cal.set(Calendar.YEAR, year);
//                        // ^ first set year for the line below to work correctly
//                        //with leap years - otherwise, date e.g. 29/02/2012
//                        //would be automatically corrected to 28/02/2012
//
//                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
//                        clean = String.format("%02d%02d%02d",day, mon, year);
//                    }
//
//                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
//                            clean.substring(2, 4),
//                            clean.substring(4, 8));
//
//                    sel = sel < 0 ? 0 : sel;
//                    current = clean;
//                    fecha.setText(current);
//                    fecha.setSelection(sel < current.length() ? sel : current.length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        };
//        fecha.addTextChangedListener(tw);

        session=new SessionManager(this);

//        List<String> gender=new ArrayList<>();
//        gender.add("Masculino");
//        gender.add("Femenino");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, gender);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sexo.setAdapter(dataAdapter);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(i);
//                RegisterActivity.this.finish();
                pDialog = new ProgressDialog(RegisterActivity.this);
                String message = "Buscando...";

                SpannableString ss2 = new SpannableString(message);
                ss2.setSpan(new RelativeSizeSpan(1f), 0, ss2.length(), 0);
                ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

                pDialog.setMessage(ss2);

                pDialog.setCancelable(true);
                pDialog.show();
                registrar(nombre.getText().toString()+" "+apellido.getText().toString(),
                        correo.getText().toString(),
                        psw.getText().toString(),
                        peso.getText().toString(),altura.getText().toString());
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

    public void registrar(final String nombre,final String email,final String psw,final String peso,final String alt) {
        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://tesis-service.herokuapp.com/Registrar";
        String url2="http://192.168.1.14:8080/Tesis_Ortega/Registrar";

        // Request a string response from the provided URL.

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (response.equals("fail")) {
                            Toast.makeText(RegisterActivity.this, "¡Hubo un error al registrarse!", Toast.LENGTH_SHORT).show();
                        } else {
                            //session.createLoginSession(response);
                            session.createLoginSession(email);
                            Intent i = new Intent(RegisterActivity.this, Main2Activity.class);
                            startActivity(i);
                            RegisterActivity.this.finishAffinity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("correo", email);
                params.put("psw", psw);
                params.put("nombre",nombre);
                params.put("peso",peso);
                params.put("alt",alt);

                return params;
            }
        };
        queue.add(postRequest);
    }
}

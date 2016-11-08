package com.ulima.tesis_ortega;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ulima.tesis_ortega.Utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText user,clave;
    Button login,registro;
    CallbackManager callbackManager;
    SessionManager session;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        session=new SessionManager(this);
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
                pDialog = new ProgressDialog(LoginActivity.this);
                String message = "Buscando...";

                SpannableString ss2 = new SpannableString(message);
                ss2.setSpan(new RelativeSizeSpan(1f), 0, ss2.length(), 0);
                ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

                pDialog.setMessage(ss2);

                pDialog.setCancelable(true);
                pDialog.show();
                login(user.getText().toString(),clave.getText().toString());
            }
        });

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile","email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(LoginActivity.this, "Inicio Correcto", Toast.LENGTH_SHORT).show();
                System.out.println(loginResult);
                getFbData();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                System.out.println(error);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //getFbData();
    }

    private void getFbData() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            //System.out.println(object);
                            //System.out.println(response);
                            validar(object.getString("email"));
                            //Toast.makeText(LoginActivity.this, object.getString("email"), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public void validar(final String u) {
        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://tesis-ortega.herokuapp.com/validar";
        String url2="http://192.168.1.14:8080/Tesis_Ortega/Validar";
        String url3="http://54.227.36.192:8080/Tesis_Ortega/validar";

        // Request a string response from the provided URL.

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //System.out.println("***** "+response+" ****");
                        if (response.equalsIgnoreCase("fail")) {

                            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(i);
                            LoginActivity.this.finishAffinity();
                        }else{

                            Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                            session.createLoginSession(u);
                            startActivity(i);
                            LoginActivity.this.finishAffinity();
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
                params.put("correo", u);

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void login(final String u, final String p) {
        final RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://tesis-ortega.herokuapp.com/login";
        String url2="http://192.168.1.14:8080/Tesis_Ortega/login";

        // Request a string response from the provided URL.

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (response.equals("fail")) {
                            pDialog.hide();
                            Toast.makeText(LoginActivity.this, "¡Usuario o contraseña incorrecta!", Toast.LENGTH_SHORT).show();
                        } else {
                            //session.createLoginSession(response);
                            pDialog.hide();
                            System.out.println(response);
                            session.createLoginSession(response);
                            Intent i = new Intent(LoginActivity.this, Main2Activity.class);
                            startActivity(i);
                            LoginActivity.this.finishAffinity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        pDialog.hide();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("correo", u);
                params.put("psw", p);

                return params;
            }
        };
        queue.add(postRequest);
    }
}

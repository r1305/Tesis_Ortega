package com.ulima.tesis_ortega;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.ulima.tesis_ortega.Utils.SessionManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    TextView nivel,edad,puntos,porcentaje;
    EditText peso,talla,imc;
    private ProgressBar progressBar;
    ProgressDialog pDialog;
    String correo,level;
    SessionManager session;
    CircleImageView img;


    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//
//        args.putString("correo", args.getBundle("correo").toString());
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            System.out.println(getArguments().getString("correo"));
//            correo = getArguments().getString("correo");
//        }
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        correo = user.get(SessionManager.KEY_EMAIL);
        level=user.get(SessionManager.KEY_NIVEL);
        getDatos(correo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        pDialog = new ProgressDialog(getActivity());
        String message = "Cargando...";

        SpannableString ss2 = new SpannableString(message);
        ss2.setSpan(new RelativeSizeSpan(1f), 0, ss2.length(), 0);
        ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

        pDialog.setMessage(ss2);

        pDialog.setCancelable(true);
        pDialog.show();

        porcentaje=(TextView)v.findViewById(R.id.porcentaje);
        puntos=(TextView)v.findViewById(R.id.puntos);
//        edad=(TextView)v.findViewById(R.id.edad);
        img=(CircleImageView)v.findViewById(R.id.profile_img);
        nivel=(TextView)v.findViewById(R.id.nivel);
        peso=(EditText)v.findViewById(R.id.profile_peso);
        talla=(EditText)v.findViewById(R.id.profile_talla);
        imc=(EditText)v.findViewById(R.id.profile_imc);

        progressBar=(ProgressBar)v.findViewById(R.id.progreso);
        progressBar.setMax(100);


        peso.setTextColor(Color.BLACK);
        talla.setTextColor(Color.BLACK);
        imc.setTextColor(Color.BLACK);
        nivel.setTextColor(Color.BLACK);
//        edad.setTextColor(Color.BLACK);
        puntos.setTextColor(Color.BLACK);
        porcentaje.setTextColor(Color.BLACK);
        peso.setEnabled(false);
        talla.setEnabled(false);
        imc.setEnabled(false);

        return v;
    }

    public void getDatos(final String correo) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://tesis-ortega.herokuapp.com/getDatos";
        String url2 = "http://192.168.1.14:8080/Tesis_Ortega/getDatos";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println("***** " + response);
                        JSONParser jp = new JSONParser();
                        JSONObject obj;
                        try {
                            obj = (JSONObject) jp.parse(response);
                            System.out.println(obj);
                            Picasso.with(getActivity()).load(obj.get("img").toString()).into(img);
                            nivel.setText("Nivel: "+obj.get("nivel").toString());
                            peso.setText(obj.get("peso").toString()+" kg");
                            talla.setText(obj.get("alt").toString()+" cm");
                            puntos.setText("Puntos: "+obj.get("puntaje").toString());
                            float max=Integer.parseInt(obj.get("max").toString());
                            float p=Integer.parseInt(obj.get("puntaje").toString());
                            int perc= Math.round((p/max)*100);
                            progressBar.setProgress(perc);
                            porcentaje.setText(perc+"%");
                            imc.setText(obj.get("imc").toString());
                            pDialog.dismiss();
                            valNivel(correo,obj.get("nivel").toString());

                        } catch (ParseException e) {
                            //Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println("PerfilError: "+e.toString());
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("correo", correo);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void valNivel(final String correo,final String nivel) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://tesis-ortega.herokuapp.com/ValidarNivel";
        String url2 = "http://192.168.1.14:8080/Tesis_Ortega/ValidarNivel";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println("***** " + response);
                        
                        if(response.equals("fail")){
                            Toast.makeText(getActivity(), "Sigue sumando actividades para subir de nivel", Toast.LENGTH_SHORT).show();
                            createDialog().show();
                        }else{
                            createDialog().show();
                            Toast.makeText(getActivity(), "Felicidades!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        //pDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("correo", correo);
                params.put("nivel",nivel);
                return params;
            }
        };
        queue.add(postRequest);
    }

    public AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setView(R.layout.layout_win);

        return builder.create();
    }

}

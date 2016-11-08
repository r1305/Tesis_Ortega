package com.ulima.tesis_ortega;

import android.content.Intent;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.POWER_SERVICE;


public class RetoDetailFragment extends Fragment {

    private String mParam1;
    TextView chrono,act,tiempo,desc;
    Button btn_start;int mili;
    ImageView img;
    SessionManager session;
    String correo,idAct;


    public RetoDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RetoDetailFragment newInstance(String param1) {
        RetoDetailFragment fragment = new RetoDetailFragment();
        Bundle args = new Bundle();
        args.putString("datos", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("datos");
            System.out.println(mParam1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_reto_detail, container, false);
        act=(TextView)v.findViewById(R.id.detail_act);
        tiempo=(TextView)v.findViewById(R.id.detail_tiempo);
        btn_start=(Button)v.findViewById(R.id.start);
        chrono=(TextView)v.findViewById(R.id.chronos);
        img=(ImageView)v.findViewById(R.id.detail_img);
        desc=(TextView)v.findViewById(R.id.detail_desc);

        session = new SessionManager(getActivity());
        session.checkLogin();
        if (session.isLoggedIn()) {
            HashMap<String, String> user = session.getUserDetails();
            correo = user.get(SessionManager.KEY_EMAIL);
        }
        JSONParser p=new JSONParser();
        JSONObject o;
        try{
            o=(JSONObject)p.parse(mParam1);
            //System.out.println(o);
            idAct=o.get("id").toString();
            act.setText(o.get("nombre").toString());
            tiempo.setText(o.get("tiempo").toString()+" minuto(s)");

            desc.setText(o.get("descripcion").toString());
            Picasso.with(getActivity()).load(o.get("imagen").toString()).into(img);
            String time=o.get("tiempo").toString();
            mili=Integer.parseInt(time)*60*1000;
            int t=Integer.parseInt(time);
            if(t==1){
                tiempo.setText(o.get("tiempo").toString()+" minuto");
            }else{
                tiempo.setText(o.get("tiempo").toString()+" minutos");
            }
        }catch (Exception e){
            //System.out.println(o);
            //System.out.println(e);
        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(mili, 1000) {

                    public void onTick(long millisUntilFinished) {
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                        if(seconds<10){
                            chrono.setText(minutes+":0"+seconds);
                        }else {
                            chrono.setText(minutes + ":" + seconds);
                        }
                    }

                    public void onFinish() {
                        btn_start.setEnabled(false);
                        chrono.setText("Bien hecho!");
                        update(correo,idAct);
                    }
                }.start();
            }
        });

        return v;
    }

    public void update(final String u, final String id) {
        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://tesis-ortega.herokuapp.com/ActualizarPuntaje";
        String url2="http://192.168.1.14:8080/Tesis_Ortega/ActualizarPuntaje";

        // Request a string response from the provided URL.

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (response.equals("true")) {
                            Toast.makeText(getActivity(), "¡Actividad realizada!", Toast.LENGTH_SHORT).show();
                        } else {
                            //session.createLoginSession(response);
                            Toast.makeText(getActivity(), "¡Ocurrió un error!", Toast.LENGTH_SHORT).show();
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
                params.put("idAct", id);

                return params;
            }
        };
        queue.add(postRequest);
    }


}

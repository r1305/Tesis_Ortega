package com.ulima.tesis_ortega;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;


public class RetoDetailFragment extends Fragment {

    private String mParam1;
    TextView chrono, act, tiempo, desc;
    Button btn_start;
    int mili;
    ImageView img;
    SessionManager session;
    String correo, idAct;
    int puntos;
    int tipo;
    private LocationManager locationManager;
    Location loc1 = new Location("");


    Location loc2 = new Location("");
    Location loc=new Location("");


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
        View v = inflater.inflate(R.layout.fragment_reto_detail, container, false);
        act = (TextView) v.findViewById(R.id.detail_act);
        tiempo = (TextView) v.findViewById(R.id.detail_tiempo);
        btn_start = (Button) v.findViewById(R.id.start);
        chrono = (TextView) v.findViewById(R.id.chronos);
        img = (ImageView) v.findViewById(R.id.detail_img);
        desc = (TextView) v.findViewById(R.id.detail_desc);


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return v;
            }
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

//                Toast.makeText(getActivity(), "Ubicando...", Toast.LENGTH_LONG).show();
                loc.setLatitude(location.getLatitude());
                loc.setLongitude(location.getLongitude());
                System.out.println("loc2: "+loc.getLatitude()+"-"+loc.getLongitude());

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Toast.makeText(getActivity(), "Active su GPS porfavor", Toast.LENGTH_SHORT).show();

            }
        });



        session = new SessionManager(getActivity());
        session.checkLogin();
        if (session.isLoggedIn()) {
            HashMap<String, String> user = session.getUserDetails();
            correo = user.get(SessionManager.KEY_EMAIL);
        }
        JSONParser p = new JSONParser();
        JSONObject o;
        try {
            o = (JSONObject) p.parse(mParam1);
            //System.out.println(o);
            puntos = Integer.parseInt(o.get("punt").toString());
            String time = o.get("tiempo").toString();
            int t = Integer.parseInt(time);
            idAct = o.get("id").toString();
            act.setText(o.get("nombre").toString());
            tipo=Integer.parseInt(o.get("tipo").toString());

            if (t == 1) {
                tiempo.setText(o.get("tiempo").toString() + " minuto");
            } else {
                tiempo.setText(o.get("tiempo").toString() + " minutos");
            }
            //tiempo.setText(o.get("tiempo").toString()+" minuto(s)");


            desc.setText(o.get("descripcion").toString());
            Picasso.with(getActivity()).load(o.get("imagen").toString()).into(img);

            mili = Integer.parseInt(time) * 60 * 1000;


        } catch (ParseException e) {
            //System.out.println(o);
            //System.out.println(e);
            System.out.println(e);
        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(mili, 1000) {

                    public void onTick(long millisUntilFinished) {
                        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        int seconds = (int) (millisUntilFinished / 1000) % 60;
                        int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                        if (seconds < 10) {
                            chrono.setText(minutes + ":0" + seconds);
                        } else {
                            chrono.setText(minutes + ":" + seconds);
                        }

                        loc1.setLatitude(loc.getLatitude());
                        loc1.setLongitude(loc.getLongitude());
                    }

                    public void onFinish() {
                        loc2.setLatitude(loc.getLatitude());
                        loc2.setLongitude(loc.getLongitude());
                        btn_start.setEnabled(false);
                        float distanceInMeters = loc1.distanceTo(loc2);
                        if(tipo==1 && distanceInMeters==0){
                            Toast.makeText(getActivity(), "Actividad no realizada \n " +
                                    "Corre durante el tiempo indicado", Toast.LENGTH_SHORT).show();
                            btn_start.setEnabled(true);
                            chrono.setText("Vuelve a intentarlo");
                        }else{
                            chrono.setText("Bien hecho!");
                            update(correo, idAct);
                            createDialog(String.valueOf(puntos)).show();
                        }
                    }
                }.start();
            }
        });

        return v;
    }

    public void update(final String u, final String id) {
        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://tesis-ortega.herokuapp.com/ActualizarPuntaje";
        String url2 = "http://192.168.1.14:8080/Tesis_Ortega/ActualizarPuntaje";

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

    public AlertDialog createDialog(String puntos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("¡Felicitaciones!\n¡Has ganado " + puntos + " puntos!")
//                .setMessage("¡Has alcanzado un nuevo nivel!")
                .setView(R.layout.layout_puntos)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }


}

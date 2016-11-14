package com.ulima.tesis_ortega;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ulima.tesis_ortega.Utils.SessionManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrofeosFragment extends Fragment{

    RecyclerView trof;
    TrofeosRecycleAdapter adapter;
    List<JSONObject> l=new ArrayList<>();
    ProgressDialog pDialog;
    SessionManager session;
    String correo;

    public TrofeosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TrofeosFragment newInstance() {
        TrofeosFragment fragment = new TrofeosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_trofeos, container, false);

//        try {
//            JSONObject o = new JSONObject();
//            o.put("nombre", "ACTIVE TRAINING");
//            JSONObject o1 = new JSONObject();
//            o1.put("nombre", "TRANSFORMATION");
//            JSONObject o2 = new JSONObject();
//            o2.put("nombre", "MAXIMUM RESISTENCE");
//            JSONObject o3 = new JSONObject();
//            o3.put("nombre", " WELLNESS MOVE");
//            l.add(o);
//            l.add(o1);
//            l.add(o2);
//            l.add(o3);
//
//        }catch(Exception e){
//            System.out.println(e);
//        }
        session = new SessionManager(getActivity());
        HashMap<String,String> user=session.getUserDetails();
        correo=user.get(SessionManager.KEY_EMAIL);
        System.out.println(correo);
        pDialog = new ProgressDialog(getActivity());
        String message = "Buscando...";

        SpannableString ss2 = new SpannableString(message);
        ss2.setSpan(new RelativeSizeSpan(1f), 0, ss2.length(), 0);
        ss2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss2.length(), 0);

        pDialog.setMessage(ss2);

        pDialog.setCancelable(true);
        pDialog.show();
        getTrofeos(correo);
        trof=(RecyclerView)v.findViewById(R.id.recycler_view_trofe);
        trof.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new TrofeosRecycleAdapter(l);

        trof.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }

    public void getTrofeos(final String u) {
        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://tesis-ortega.herokuapp.com/getTrofeos";
        String url2 = "http://192.168.1.14:8080/Tesis_Ortega/getTrofeos";

        // Request a string response from the provided URL.

        final StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println("***** " + response);
                        JSONParser jp = new JSONParser();
                        JSONObject obj;
                        try {
                            obj = (JSONObject) jp.parse(response);
                            JSONArray ja = (JSONArray) obj.get("trofeos");
                            l.clear();
                            for(int i=0;i<ja.size();i++){
                                l.add((JSONObject)ja.get(i));
                            }
                            adapter.notifyDataSetChanged();
                            pDialog.dismiss();

                        } catch (Exception e) {
                            Toast.makeText(getActivity(),"Intente luego", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            pDialog.dismiss();
                        }
//                        Toast.makeText(getActivity(), "¡Has ganado " + response + " trofeo(s)!", Toast.LENGTH_SHORT).show();
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

    // TODO: Rename method, update argument and hook method into UI event

}

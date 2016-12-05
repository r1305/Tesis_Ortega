package com.ulima.tesis_ortega;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import com.android.volley.DefaultRetryPolicy;
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
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RetosFragment extends Fragment {



    RecyclerView reto;
    RetosRecyclerAdapter adapter;
    List<JSONObject> l=new ArrayList<>();
    ProgressDialog pDialog;
    SessionManager session;
    String correo;

    public RetosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RetosFragment newInstance() {
        RetosFragment fragment = new RetosFragment();
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
        View v=inflater.inflate(R.layout.fragment_retos, container, false);
        reto=(RecyclerView)v.findViewById(R.id.recycler_view_reto);
        reto.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        getRetos(correo);
        adapter=new RetosRecyclerAdapter(l);

        reto.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject o=(JSONObject)view.getTag();
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();

                Fragment details=RetoDetailFragment.newInstance(o.toString());
                ft.replace(R.id.frame,details);
                //ft.attach(details);
                //ft.addToBackStack("ActiFit");
                ft.commit();
                //fm.executePendingTransactions();
                //ft.replace(R.id.frame,details);
                //ft.commit();
            }
        });
        return v;
    }

    public void getRetos(final String cor) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://tesis-ortega.herokuapp.com/GetRetos";
        String url2 = "http://192.168.1.14:8080/Tesis_Ortega/GetRetos";
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
                            JSONArray ja = (JSONArray) obj.get("act");
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        getRetos(correo);
                        Log.d("Error.Response", error.toString());
                        //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("correo", cor);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                15,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }


}

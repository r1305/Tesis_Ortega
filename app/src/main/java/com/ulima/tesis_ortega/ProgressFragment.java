package com.ulima.tesis_ortega;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ulima.tesis_ortega.Utils.SessionManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;


public class ProgressFragment extends Fragment {

    GraphView graph;
    LineGraphSeries<DataPoint> series;
    SessionManager session;


    public ProgressFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    /*public static ProgressFragment newInstance(String param1, String param2) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    public static ProgressFragment newInstance() {
        ProgressFragment fragment = new ProgressFragment();
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
        View v = inflater.inflate(R.layout.fragment_progress, container, false);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        String correo = user.get(SessionManager.KEY_EMAIL);
        progress(correo);
        graph = (GraphView) v.findViewById(R.id.graph);

        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        return v;
    }

    public void progress(final String cor) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://tesis-ortega.herokuapp.com/Progreso";
        String url2 = "http://192.168.1.14:8080/Tesis_Ortega/Progreso";
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
                            JSONArray ja = (JSONArray) obj.get("progreso");

                            series = new LineGraphSeries<>();
                            DataPoint dataPoint;
                            for (int i = 0; i < ja.size(); i++) {


                                dataPoint=new DataPoint(Integer.parseInt((String) ((JSONObject) ja.get(i)).get("dia")),
                                        (long) ((JSONObject) ja.get(i)).get("cant"));
                                series.appendData(dataPoint,false,30);
                            }
                            graph.addSeries(series);

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Intente luego", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        progress(cor);
                        //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("correo", cor);
                return params;
            }
        };
        queue.add(postRequest);
    }

}

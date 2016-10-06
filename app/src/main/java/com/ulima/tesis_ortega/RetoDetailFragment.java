package com.ulima.tesis_ortega;

import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class RetoDetailFragment extends Fragment {

    private String mParam1;
    TextView chrono,act,tiempo;
    Button btn_start;int mili;


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

        try{
            JSONParser p=new JSONParser();
            JSONObject o=(JSONObject)p.parse(mParam1);
            act.setText(o.get("actividad").toString());
            tiempo.setText(o.get("tiempo").toString());
            String time=o.get("tiempo").toString().substring(0,2);
            mili=Integer.parseInt(time)*60*1000;


        }catch (Exception e){

        }
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(mili, 1000) {

                    public void onTick(long millisUntilFinished) {
                        int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                        if(seconds<10){
                            chrono.setText("Time remaining: " + minutes+": 0"+seconds);
                        }else {
                            chrono.setText("Time remaining: " + minutes + ":" + seconds);
                        }
                    }

                    public void onFinish() {
                        chrono.setText("done!");
                    }
                }.start();
            }
        });

        return v;
    }
}

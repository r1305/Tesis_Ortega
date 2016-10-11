package com.ulima.tesis_ortega;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    TextView nivel;
    EditText peso,talla,imc;
    private ProgressBar progressBar;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        nivel=(TextView)v.findViewById(R.id.nivel);
        peso=(EditText)v.findViewById(R.id.profile_peso);
        talla=(EditText)v.findViewById(R.id.profile_talla);
        imc=(EditText)v.findViewById(R.id.profile_imc);
        progressBar=(ProgressBar)v.findViewById(R.id.progreso);
        progressBar.setMax(100);
        progressBar.setProgress(75);

        peso.setTextColor(Color.BLACK);
        talla.setTextColor(Color.BLACK);
        imc.setTextColor(Color.BLACK);
        nivel.setTextColor(Color.BLACK);
        peso.setEnabled(false);
        talla.setEnabled(false);
        imc.setEnabled(false);


        nivel.setText(" 5 ");
        peso.setText("65 kg");
        talla.setText("178 cm");
        imc.setText("20");



        return v;
    }

}

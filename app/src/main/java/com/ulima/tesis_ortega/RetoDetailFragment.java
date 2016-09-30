package com.ulima.tesis_ortega;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class RetoDetailFragment extends Fragment {

    private String mParam1;


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
        return inflater.inflate(R.layout.fragment_reto_detail, container, false);
    }
}

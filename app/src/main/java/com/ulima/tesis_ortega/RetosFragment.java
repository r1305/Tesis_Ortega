package com.ulima.tesis_ortega;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RetosFragment extends Fragment {



    RecyclerView reto;
    RetosRecyclerAdapter adapter;
    List<JSONObject> l=new ArrayList<>();

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
        l.clear();
        reto=(RecyclerView)v.findViewById(R.id.recycler_view_reto);
        reto.setLayoutManager(new LinearLayoutManager(getActivity()));
        JSONObject o=new JSONObject();
        o.put("actividad","Correr");
        o.put("tiempo","10min");
        l.add(o);
        JSONObject o1=new JSONObject();
        o1.put("actividad","Pesas");
        o1.put("tiempo","3 x 10");
        l.add(o1);
        JSONObject o2=new JSONObject();
        o2.put("actividad","Correr");
        o2.put("tiempo","1 Km");
        l.add(o2);
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
                ft.commit();
                //ft.replace(R.id.frame,details);
                //ft.commit();
            }
        });
        return v;
    }


}

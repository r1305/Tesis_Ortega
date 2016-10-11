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
        o.put("actividad","Caminar");
        o.put("tiempo","30min");
        o.put("foto","http://res.cloudinary.com/dsdrbqoex/image/upload/v1476147191/caminar_me9odf.jpg");
        o.put("desc","Cuando uno camina transporta el peso de su cuerpo a una distancia determinada. Se convierte en un ejercicio de entrenamiento cuando se hace a suficiente velocidad y fuerza");
        l.add(o);
        JSONObject o1=new JSONObject();
        o1.put("actividad","Subir las escaleras");
        o1.put("tiempo","15min");
        o1.put("foto","http://res.cloudinary.com/dsdrbqoex/image/upload/v1476147190/escaleras_kdn8qq.jpg");
        o1.put("desc","Subir escaleras es un muy buen ejercicio. Se recomienda subir 3 tramos sin detenerse en 30 segundos escalones por vez si es posible. Hacer esto 9 ó 10 veces por día es un ");
        l.add(o1);
        JSONObject o2=new JSONObject();
        o2.put("actividad","Trotar");
        o2.put("tiempo","30min");
        o2.put("foto","http://res.cloudinary.com/dsdrbqoex/image/upload/v1476147191/trote_b7ulqd.jpg");
        o2.put("desc","El trote se refiere a un tipo de carrera lenta que mejora aptitudes físicas");
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
                fm.executePendingTransactions();
                //ft.replace(R.id.frame,details);
                //ft.commit();
            }
        });
        return v;
    }


}

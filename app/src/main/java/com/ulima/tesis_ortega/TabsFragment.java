package com.ulima.tesis_ortega;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "";
    private FragmentTabHost tabHost;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static Bundle args;

    public TabsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TabsFragment newInstance() {
        TabsFragment fragment = new TabsFragment();
//        args = new Bundle();
//        args.putString("correo", correo);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString("correo");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tabs, container, false);
        tabHost = (FragmentTabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getFragmentManager(), android.R.id.tabcontent);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Perfil"),
                ProfileFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Retos"),
                RetosFragment.class, null);
        //tabHost.setCurrentTab(0);
        return v;
    }


}

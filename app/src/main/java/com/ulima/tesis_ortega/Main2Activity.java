package com.ulima.tesis_ortega;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;
import com.ulima.tesis_ortega.Utils.SessionManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    Toolbar toolbar;
    NavigationView nav;
    TextView txt_nav;
    CircleImageView img;
    SessionManager session;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        if (session.isLoggedIn()) {
            HashMap<String, String> user = session.getUserDetails();
            String correo = user.get(SessionManager.KEY_EMAIL);
            getDatos(correo);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment tabs = TabsFragment.newInstance();
            ft.replace(R.id.frame, tabs);
            ft.commit();
            //fm.executePendingTransactions();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav = (NavigationView) findViewById(R.id.navigation);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        View v = nav.getHeaderView(0);
        txt_nav = (TextView) v.findViewById(R.id.txt_nav_header);
        img=(CircleImageView)v.findViewById(R.id.img_profile_nav);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.animate();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
                if (dl.isDrawerOpen(GravityCompat.START)) {
                    dl.closeDrawers();
                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.openDrawer,
                R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        dl.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        nav.setNavigationItemSelectedListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft;
        switch (item.getItemId()) {
            case R.id.logout:
                session.logoutUser();
//                Intent i = new Intent(Main2Activity.this, LoginActivity.class);
//                startActivity(i);
//                Main2Activity.this.finish();
                return true;
            case R.id.trofeos:
                //Fragment reco=RetosFragment.newInstance();
                //FragmentTransaction ft=fm.beginTransaction();
                //ft.replace(R.id.frame,reco);
                toolbar.setTitle("Trofeos");
                //ft.commit();
                dl.closeDrawers();
                Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.principal:
                Fragment details = ProfileFragment.newInstance();
                ft = fm.beginTransaction();

                ft.replace(R.id.frame, details);
                //ft.addToBackStack(null);
                ft.commit();
                //fm.executePendingTransactions();
                toolbar.setTitle("Inicio");
                dl.closeDrawers();
                //Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.retos:
                Fragment retos = RetosFragment.newInstance();
                ft = fm.beginTransaction();

                ft.replace(R.id.frame, retos);
                //ft.commit();
                //ft.addToBackStack(null);
                ft.commit();
                //fm.executePendingTransactions();
//                ft.remove(details);
                toolbar.setTitle("Inicio");
                dl.closeDrawers();
                //Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.progress:
                Fragment progess = ProgressFragment.newInstance();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, progess);
                toolbar.setTitle("Estadisticas");
                //ft.addToBackStack(null);
                ft.commit();
                //fm.executePendingTransactions();
                dl.closeDrawers();
                //Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.ranking:
                Fragment rank = RankingFragment.newInstance();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, rank);
                toolbar.setTitle("Ranking");
                //ft.addToBackStack(null);
                ft.commit();
                //fm.executePendingTransactions();
                dl.closeDrawers();
                //Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    public void getDatos(final String correo) {

        RequestQueue queue = Volley.newRequestQueue(Main2Activity.this);
        String url = "https://tesis-ortega.herokuapp.com/getDatos";
        String url2 = "http://192.168.1.14:8080/Tesis_Ortega/getDatos";
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
                            System.out.println(obj);
                            txt_nav.setText(obj.get("nombre").toString());
                            Picasso.with(Main2Activity.this).load(obj.get("img").toString()).into(img);
                            //pDialog.dismiss();

                        } catch (Exception e) {
                            //Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println("MainFragment Error: " + e.toString());
                            e.printStackTrace();
                            //pDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        //pDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("correo", correo);
                return params;
            }
        };
        queue.add(postRequest);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main2 Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

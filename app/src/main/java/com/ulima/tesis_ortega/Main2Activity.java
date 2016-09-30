package com.ulima.tesis_ortega;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    Toolbar toolbar;
    NavigationView nav;
    TextView txt_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        nav=(NavigationView)findViewById(R.id.navigation);
        dl=(DrawerLayout)findViewById(R.id.drawer_layout);
        View v=nav.getHeaderView(0);
        txt_nav=(TextView)v.findViewById(R.id.txt_nav_header);

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

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,dl,toolbar,R.string.openDrawer,
                R.string.closeDrawer){
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch(item.getItemId()){
            case R.id.logout:
                //session.logoutUser();
                //LoginManager.getInstance().logOut();
                return true;
            case R.id.retos:
                Fragment reco=RetosFragment.newInstance();
                ft.replace(R.id.frame,reco);
                toolbar.setTitle("Retos");
                ft.commit();
                dl.closeDrawers();
                Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.principal:
                //Fragment perfil=ProfileFragment.newInstance(datos);
                //ft.replace(R.id.frame,perfil);
                toolbar.setTitle("Perfil");
                //ft.commit();
                dl.closeDrawers();
                Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.estad:
                //Fragment libros=ActivitiesFragment.newInstance();
                //ft.replace(R.id.frame,libros);
                toolbar.setTitle("Estadisticas");
                //ft.commit();
                dl.closeDrawers();
                Toast.makeText(Main2Activity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}

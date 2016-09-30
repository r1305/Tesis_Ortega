package com.ulima.tesis_ortega;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);

        TextView textview = new TextView(this);
        textview.setText("This is Android tab");
        setContentView(textview);
    }
}

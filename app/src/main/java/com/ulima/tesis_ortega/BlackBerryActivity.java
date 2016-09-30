package com.ulima.tesis_ortega;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BlackBerryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_berry_activity);

        TextView textview = new TextView(this);
        textview.setText("This is BlackBerry tab");
        setContentView(textview);
    }
}

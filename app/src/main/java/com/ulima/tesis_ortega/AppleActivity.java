package com.ulima.tesis_ortega;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AppleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple);

        TextView textview = new TextView(this);
        textview.setText("This is Apple tab");
        setContentView(textview);
    }
}

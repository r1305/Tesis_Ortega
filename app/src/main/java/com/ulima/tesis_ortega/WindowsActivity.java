package com.ulima.tesis_ortega;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WindowsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windows);

        TextView textview = new TextView(this);
        textview.setText("This is Windows mobile tab");
        setContentView(textview);
    }
}

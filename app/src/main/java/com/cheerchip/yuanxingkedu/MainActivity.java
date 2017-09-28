package com.cheerchip.yuanxingkedu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToneView tone = ((ToneView) findViewById(R.id.tone));
        tone.setOnDegreeChangeLisener(new ToneView.OnDegreeChangeLisener() {
            @Override
            public void onDegreeChangeLisener(float degree) {
                Log.e( "onDegreeChangeLisener: ",degree+"" );
            }
        });
    }
}

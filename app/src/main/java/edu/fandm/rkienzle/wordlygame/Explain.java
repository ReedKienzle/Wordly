package edu.fandm.rkienzle.wordlygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Explain extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        //getSupportActionBar().hide();
    }

    public void letsgo(View v) {
        finish();
    }
}

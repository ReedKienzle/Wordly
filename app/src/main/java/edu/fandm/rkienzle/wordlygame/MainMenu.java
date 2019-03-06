package edu.fandm.rkienzle.wordlygame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainMenu extends AppCompatActivity {
    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        t = (TextView) findViewById(R.id.Choose);
    }
    public void GoEasy(View v)
    {
        Intent intent = new Intent(this, Launch.class);
        startActivity(intent);
    }
    public void GoNormal(View v)
    {
        Intent intent = new Intent(this, Launch.class);
        startActivity(intent);
    }
    public void GoHard(View v)
    {
        Intent intent = new Intent(this, Launch.class);
        startActivity(intent);
    }
    public void GoCustom(View v)
    {
        Intent intent = new Intent(this, Launch.class);
        startActivity(intent);
    }
}

package edu.fandm.rkienzle.wordlygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
    public void GoEasy(View v)
    {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
    public void GoNormal(View v)
    {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
    public void GoHard(View v)
    {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
    public void GoCustom(View v)
    {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
}

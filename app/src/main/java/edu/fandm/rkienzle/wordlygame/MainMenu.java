package edu.fandm.rkienzle.wordlygame;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainMenu extends AppCompatActivity {
    TextView t;
    String difficulty;
    MediaPlayer mysong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        t = (TextView) findViewById(R.id.Choose);
        mysong = MediaPlayer.create(this,R.raw.music);
        mysong.start();
        mysong.setLooping(true);
    }
    public void showExplain(View v){
        startActivity(new Intent(this, Explain.class));
    }

    public void GoEasy(View v) {
        difficulty = "e";
        Intent intent = new Intent(this, Launch.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    public void GoNormal(View v) {
        difficulty = "m";
        Intent intent = new Intent(this, Launch.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    public void GoHard(View v) {
        difficulty = "h";
        Intent intent = new Intent(this, Launch.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    public void GoCustom(View v) {
        difficulty = "c";
        Intent intent = new Intent(this, Launch.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}
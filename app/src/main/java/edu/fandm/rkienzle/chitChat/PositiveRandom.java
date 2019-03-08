package edu.fandm.rkienzle.chitChat;

//import android.support.v7.widget.ActivityChooserView.ActivityChooserViewAdapter;
import android.util.Log;

import java.util.Random;

public class PositiveRandom {
    public static final String TAG = "enovak.PositiveRandom";
    /* renamed from: r */
    private static Random f9r;

    public static int nextInt(int bound) {
        if (f9r == null) {
            f9r = new Random();
        }
        Log.d(TAG, "Bound: " + bound);
        bound = Math.abs(bound);

        return (f9r.nextInt(bound - 1) + 1);// & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }                                              // I'll be honest here. Not really sure what this line above here does

    public static int nextInt(int min, int max) {
        if (f9r == null) {
            f9r = new Random();
        }
        return (f9r.nextInt(max - min) + min);// & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }
}
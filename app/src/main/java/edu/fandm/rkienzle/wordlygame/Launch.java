package edu.fandm.rkienzle.wordlygame;

import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Editable;
        import android.text.InputFilter;
        import android.text.InputFilter.LengthFilter;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.View;
        import android.view.animation.AnimationUtils;
        import android.widget.EditText;
        import android.widget.Toast;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.LinkedList;

public class Launch extends AppCompatActivity {
    private static final String TAG = "enovak.Launch";
    private Context ctx;
    private EditText endET;
    private SharedPreferences prefs;
    private EditText startET;
    private WordGraph wg;

    /* renamed from: edu.fandm.enovak.wordly.Launch$1 */
    class C03091 implements TextWatcher {
        C03091() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            int len = s.length();
            Launch.this.endET.setFilters(new InputFilter[]{new LengthFilter(len)});
            if (s.length() < Launch.this.endET.getText().length()) {
                Launch.this.endET.setText(Launch.this.endET.getText().subSequence(0, s.length()));
            }
        }
    }

    class FindSolutionTask extends AsyncTask<String, Void, ArrayList<String>> {
        private String end;
        private String start;
        private View tv;

        FindSolutionTask() {
        }

        protected void onPreExecute() {
            this.tv = Launch.this.findViewById(R.id.tv_loading);
            this.tv.setVisibility(View.VISIBLE);
            this.tv.setAnimation(AnimationUtils.loadAnimation(Launch.this.ctx, R.anim.blink));
            this.tv.animate();
        }

        protected ArrayList<String> doInBackground(String... params) {
            this.start = params[0];
            this.end = params[1];
            try {
                WordGraph wg = Launch.this.buildGraph(this.start.length());
            }
            catch (IOException ioe) {Log.d(TAG, "IOException in doInBackground");}
            HashMap<String, String> map = new HashMap();
            HashMap<String, Boolean> marked = new HashMap();
            LinkedList<String> q = new LinkedList();
            String cur = this.start;
            q.add(cur);
            while (!cur.equals(this.end)) {
                if (q.isEmpty()) {
                    return null;
                }
                cur = (String) q.remove();
                marked.put(cur, Boolean.valueOf(true));
                ArrayList<String> neighbors = wg.getNeighbors(cur);
                for (int i = 0; i < neighbors.size(); i++) {
                    if (!map.containsKey((String) neighbors.get(i))) {
                        map.put(neighbors.get(i), cur);
                        q.add(neighbors.get(i));
                    }
                }
            }
            ArrayList<String> soln = new ArrayList(2);
            for (String cur2 = this.end; !cur2.equals(this.start); cur2 = (String) map.get(cur2)) {
                soln.add(0, cur2);
            }
            soln.add(0, this.start);
            return soln;
        }

        protected void onPostExecute(ArrayList<String> solution) {
            this.tv.clearAnimation();
            this.tv.setVisibility(View.INVISIBLE);
            if (solution == null) {
                StringBuilder tmp = new StringBuilder();
                tmp.append("no possible solution from '");
                tmp.append(this.start);
                tmp.append("' to '");
                tmp.append(this.end);
                tmp.append("'");
                Toast.makeText(Launch.this.ctx, tmp.toString(), Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(Launch.this.ctx, Game.class);
            i.putExtra("start_word", this.start);
            i.putExtra("end_word", this.end);
            i.putStringArrayListExtra("solution", solution);
            Launch.this.startActivity(i);
        }
    }

    class GenPuzzleTask extends AsyncTask<Integer, Void, String[]> {
        private View tv;

        GenPuzzleTask() {
        }

        protected void onPreExecute() {
            this.tv = Launch.this.findViewById(R.id.tv_loading);
            this.tv.setVisibility(View.VISIBLE);
            this.tv.setAnimation(AnimationUtils.loadAnimation(Launch.this.ctx, R.anim.blink));
            this.tv.animate();
        }

        private String genSequence(String start, int SEQ_LEN) throws IllegalStateException {
            HashMap<String, Boolean> marked = new HashMap();
            marked.put(start, Boolean.valueOf(true));
            int hops = 0;
            LinkedList<String> curRound = new LinkedList();
            curRound.push(start);
            LinkedList<String> nextRound = new LinkedList();
            while (hops < SEQ_LEN) {
                Iterator it = curRound.iterator();
                while (it.hasNext()) {
                    Iterator it2 = Launch.this.wg.getNeighbors((String) it.next()).iterator();
                    while (it2.hasNext()) {
                        String n = (String) it2.next();
                        if (!marked.containsKey(n)) {
                            marked.put(n, Boolean.valueOf(true));
                            nextRound.add(n);
                        }
                    }
                }
                if (nextRound.size() != 0) {
                    curRound = nextRound;
                    nextRound = new LinkedList();
                    hops++;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot find a sequence of len: ");
                    stringBuilder.append(SEQ_LEN);
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
            return (String) curRound.get(PositiveRandom.nextInt(curRound.size()));
        }

        protected String[] doInBackground(Integer... params) {
            try {
                WordGraph wg = Launch.this.buildGraph(params[0].intValue());
            }
            catch (IOException ioe) {Log.d(TAG, "IOException in doInBackground");}
            String[] startAndEnd = new String[2];
            while (startAndEnd[1] == null) {
                startAndEnd[0] = wg.getRandomWord();
                try {
                    startAndEnd[1] = genSequence(startAndEnd[0], 3);
                } catch (IllegalStateException ise) {
                    Log.d(Launch.TAG, ise.getMessage());
                    String str = Launch.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Giving up on start word: ");
                    stringBuilder.append(startAndEnd[0]);
                    Log.d(str, stringBuilder.toString());
                    startAndEnd[1] = null;
                }
            }
            return startAndEnd;
        }

        protected void onPostExecute(String[] result) {
            this.tv.clearAnimation();
            this.tv.setVisibility(View.INVISIBLE);
            String s = result[0];
            String e = result[1];
            Launch.this.startET.setText(s);
            Launch.this.endET.setText(e);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        this.ctx = getApplicationContext();
        this.startET = (EditText) findViewById(R.id.et_start);
        this.endET = (EditText) findViewById(R.id.et_end);
        this.prefs = getSharedPreferences(BuildConfig.APPLICATION_ID, 0);
        this.startET.addTextChangedListener(new C03091());
        if (savedInstanceState == null) {
            genGame(null);
        }
    }

    public void startGame(View v)
    {
        String s = this.startET.getText().toString();
        String e = this.endET.getText().toString();
        if (s.length() == this.endET.getText().toString().length()) {
            if (s.length() != 0) {
                new FindSolutionTask().execute(new String[]{s, e});
                return;
            }
        }
        Toast.makeText(this, "Starting word and ending word must be the same length!", Toast.LENGTH_SHORT).show();
    }

    public void genGame(View v) {
        new GenPuzzleTask().execute(new Integer[]{Integer.valueOf(4)});
    }

    protected void onResume() {
        super.onResume();
        findViewById(R.id.tv_loading).setVisibility(View.INVISIBLE);
        //only shows explain page on the first run of the app.
        if (this.prefs.getBoolean("firstrun", true)) {
            this.prefs.edit().putBoolean("firstrun", false).commit();
            startActivity(new Intent(this.ctx, Explain.class));
        }
    }

    private WordGraph buildGraph(int len) throws IOException {
        if (this.wg != null && this.wg.getRandomWord().length() == len) {
            return this.wg;
        }
        this.wg = new WordGraph();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = getAssets().open("words_simple.txt");
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = BuildConfig.FLAVOR;
            while (true) {
                String readLine = input.readLine();
                line = readLine;
                if (readLine == null) {
                    break;
                }
                line = line.replace("\n", BuildConfig.FLAVOR).toLowerCase();
                if (line.length() == len && line.matches("[a-zA-Z]+")) {
                    this.wg.addWord(line);
                }
            }
            try {
                isr.close();
                if (fIn != null) {
                    fIn.close();
                }
                input.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (IOException ioe2) {
            ioe2.printStackTrace();
            if (isr != null) {
                isr.close();
            }
            if (fIn != null) {
                fIn.close();
            }
            if (input != null) {
                input.close();
            }
        } catch (Throwable th) {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ioe3) {
                    ioe3.printStackTrace();
                }
            }
            if (fIn != null) {
                fIn.close();
            }
            if (input != null) {
                input.close();
            }
        }
        return this.wg;
    }
}

package com.fydp.myoralvillage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;


public class Game2Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        createBtnGenerate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createBtnGenerate() {
        Button button = (Button) findViewById(R.id.btn_generate);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                generateFinger(v);


            }
        });
    }

    public void generateFinger(View v) {
        Random r = new Random();
        int n=r.nextInt(10)+1;

        String filename = "fingers"+n;
        int id = getResources().getIdentifier(filename, "drawable", getPackageName());

        ImageView iv = (ImageView) findViewById(R.id.img_hands);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        iv.requestLayout();
        iv.getLayoutParams().height = (int)(screenHeight*0.5);
        iv.getLayoutParams().width = (int)(screenWidth*0.5);
        iv.setImageResource(id);
        iv.setVisibility(View.VISIBLE);

        generateAnswers(v, n);
    }

    public void generateAnswers(View v, int correctAnswer) {

    }
}

package com.fydp.myoralvillage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class GamePlaceValueActivity extends ActionBarActivity {
    public int difficultyLevel = 1;
    public int correctAnswer=0;
    //1: beginner (tens, hundreds, or thousands)
    //2: intermediate (tens, hundreds, and thousands)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_place_value);
        startNewRoundGamePlaceValue();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_place_value, menu);
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

    public void resetGame(View v) {
        startNewRoundGamePlaceValue();
    }

    public void startNewRoundGamePlaceValue () {
        correctAnswer = 0;

        for (int i = 1; i <= 10; i++) {
            String imgView_name = "img_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);
            iv.setVisibility(View.INVISIBLE);
        }

        generateRepresentation();
    }

    public void generateRepresentation() {
        if(difficultyLevel==1){
            //maximum 9 images (90, 900, 9000)
            Random randomPlaceValue = new Random();
            //int pv=randomPlaceValue.nextInt(3)+1;
            int pv = 1;
            if (pv==1){
                Random r = new Random();
                correctAnswer = (r.nextInt(9)+1)*10;
            }
            int numRepresentationImages=getNumRepresentationImages();
            segmentRepresentation(numRepresentationImages);
        } else {
            //maximum 27 images (9990)- we'll deal with this later
        }
    }

    public int getNumRepresentationImages() {
        return (int)correctAnswer/10;
    }
    public void segmentRepresentation(int n) {
        for (int i = 1; i <= n; i++) {

            int img_id = getResources().getIdentifier("gameplacevalue_representation10", "drawable", getPackageName());
            String imgView_name = "img_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int screenHeight = metrics.heightPixels;
            int screenWidth = metrics.widthPixels;

            iv.requestLayout();
            iv.getLayoutParams().height = (int)(screenHeight*0.2);
            iv.getLayoutParams().width = (int)(screenWidth/(n+1));
            iv.setImageResource(img_id);
            iv.setTag("gameplacevalue_representation10");
            iv.setVisibility(View.VISIBLE);
        }
    }
}

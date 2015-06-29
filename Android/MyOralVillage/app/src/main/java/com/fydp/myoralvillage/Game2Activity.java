package com.fydp.myoralvillage;

import android.graphics.drawable.Drawable;
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

    public int correctAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        startNewRoundGame2();
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

    public void startNewRoundGame2() {
        generateFinger();
    }

    public void generateFinger() {
        Random r = new Random();
        correctAnswer=r.nextInt(10)+1;

        String filename = "game2_fingers"+correctAnswer;
        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());

        displayFinger(img_id);
    }

    public void displayFinger(int img_id) {
        ImageView iv = (ImageView) findViewById(R.id.img_hands);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        iv.requestLayout();
        iv.getLayoutParams().height = (int)(screenHeight*0.5);
        iv.getLayoutParams().width = (int)(screenWidth*0.5);
        iv.setImageResource(img_id);
        iv.setVisibility(View.VISIBLE);

        generateAnswers();
    }

    public void generateAnswers() {
        Random r = new Random();
        int wrongAnswer1 = -1;
        int wrongAnswer2 = -1;
        do {
            wrongAnswer1 = r.nextInt(10) + 1;
        } while(wrongAnswer1==correctAnswer);
        do {
            wrongAnswer2 = r.nextInt(10) + 1;
        } while(wrongAnswer2==correctAnswer || wrongAnswer2==wrongAnswer1);

        String[] filenames = new String[3];
        filenames[0] = "game2_answer"+wrongAnswer1;
        filenames[1] = "game2_answer"+wrongAnswer2;
        filenames[2] = "game2_answer"+correctAnswer;

        int[] takenPositions = {-1,-1,-1};
        displayAnswers(filenames, takenPositions);

    }

    public void displayAnswers(String[] filenames, int[] takenPositions) {
        for (int i = 0; i < filenames.length; i++) {

            Random answerR = new Random();
            int answerPosition = -1;
            if (i==0) {
                answerPosition = answerR.nextInt(3);
            } else {
                do {
                    answerPosition = answerR.nextInt(3);
                } while (answerPosition==takenPositions[0]||answerPosition==takenPositions[1]);
            }
            takenPositions[i]=answerPosition;

            int img_id = getResources().getIdentifier(filenames[i], "drawable", getPackageName());
            String imgView_name = "img_answer"+answerPosition;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int screenHeight = metrics.heightPixels;
            int screenWidth = metrics.widthPixels;

            iv.requestLayout();
            iv.getLayoutParams().height = (int)(screenHeight*0.2);
            iv.getLayoutParams().width = (int)(screenWidth*0.3);
            iv.setImageResource(img_id);
            iv.setTag(filenames[i]);
            iv.setVisibility(View.VISIBLE);
        }
    }

    public void checkAnswer(View v) {
        ImageView iv = (ImageView) findViewById(v.getId());
        String thisImage = (iv.getTag()).toString();
        int imgFileNum = Integer.parseInt((thisImage.toString()).substring(12));

        if (imgFileNum==correctAnswer) {
            startNewRoundGame2();
        }
    }
}

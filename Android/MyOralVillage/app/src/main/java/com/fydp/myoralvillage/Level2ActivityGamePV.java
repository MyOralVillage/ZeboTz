package com.fydp.myoralvillage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class Level2ActivityGamePV extends ActionBarActivity {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;

    public int correctAnswer=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_gamepv);

        if(!userHasViewedDemo) {
            startDemo();
        }
        startNewRoundGamePlaceValue();
    }

    public void startDemo() {
        //go to demo activity (should be a separate activity)
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
    }

    public int getNumRepresentationImages() {
        return (int)correctAnswer/10;
    }
    public void segmentRepresentation(int n) {
        for (int i = 1; i <= n; i++) {

            int img_id = getResources().getIdentifier("game2_tens", "drawable", getPackageName());
            String imgView_name = "img_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);



            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int screenHeight = metrics.heightPixels;
            int screenWidth = metrics.widthPixels;

            Drawable dBle = ContextCompat.getDrawable(this,R.drawable.game2_tens);
            Bitmap bMap = ((BitmapDrawable) dBle).getBitmap();

            //Bitmap bMap = BitmapFactory.decodeResource(getResources(),R.drawable.game2_tens);
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, (int)(screenWidth*0.9/n), (int)(screenHeight*0.2), false);

            iv.requestLayout();
            iv.getLayoutParams().height = (int)(screenHeight*0.2);
            iv.getLayoutParams().width = (int)(screenWidth*0.9/n);
            //iv.setImageResource(img_id);
            iv.setImageBitmap(bMapScaled);
            iv.setTag("game2_tens");
            iv.setVisibility(View.VISIBLE);
        }

        generateAnswers();
    }

    public void generateAnswers() {
        Random r = new Random();
        int wrongAnswer1 = -1;
        int wrongAnswer2 = -1;
        do {
            wrongAnswer1 = r.nextInt(9) + 1;
        } while(wrongAnswer1==(int)correctAnswer/10);
        do {
            wrongAnswer2 = r.nextInt(9) + 1;
        } while(wrongAnswer2==(int)correctAnswer/10 || wrongAnswer2==wrongAnswer1);

        String[] filenames = new String[3];
        filenames[0] = "game2_answer"+wrongAnswer1;
        filenames[1] = "game2_answer"+wrongAnswer2;
        filenames[2] = "game2_answer"+(int)correctAnswer/10;

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

        if ((int)imgFileNum*10==correctAnswer) {
            startNewRoundGamePlaceValue();
        }
    }
}
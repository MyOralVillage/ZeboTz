package com.fydp.myoralvillage;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Level1ActivityGameDualCoding extends AppCompatActivity {
    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    public int correctAnswer;
    public boolean correctOnFirstTry = true;
    public int numCorrect = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gamedualcoding);
        if(!userHasViewedDemo){
            startDemo();
        }
        startGame();
    }

    public void startDemo() {
        //function call to go to this activity's demo (a separate activity)
        Intent intent = new Intent(this, Level1ActivityDemoDualCoding.class);
        startActivity(intent);
    }

    public void startGame() {
        //Game code in here + game method calls
        startNewRound();
    }

    public void startNewRound() {
        correctOnFirstTry = true;
        generateQuestion();
    }

    public void generateQuestion() {
        Random r = new Random();
        correctAnswer=r.nextInt(10);

        String filename = "game1_dualcoding_"+correctAnswer;
        int img_id = getResources().getIdentifier(filename, "drawable", getPackageName());

        displayQuestion(img_id);
    }

    public void displayQuestion(int img_id) {
        ImageView iv = (ImageView) findViewById(R.id.img_question);

        iv.requestLayout();
        iv.setImageResource(img_id);
        iv.setVisibility(View.VISIBLE);

        generateAnswers();
    }

    public void generateAnswers() {
        Random r = new Random();
        int wrongAnswer1 = -1;
        int wrongAnswer2 = -1;
        do {
            wrongAnswer1 = r.nextInt(10);
        } while(wrongAnswer1==correctAnswer);
        do {
            wrongAnswer2 = r.nextInt(10);
        } while(wrongAnswer2==correctAnswer || wrongAnswer2==wrongAnswer1);

        String[] filenames = new String[3];
        filenames[0] = "game1_qa_answer"+wrongAnswer1;
        filenames[1] = "game1_qa_answer"+wrongAnswer2;
        filenames[2] = "game1_qa_answer"+correctAnswer;

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

            iv.requestLayout();
            iv.setImageResource(img_id);
            iv.setTag(filenames[i]);
            iv.setVisibility(View.VISIBLE);
            iv.setClickable(true);
            iv.setAlpha((float) 1.0);
        }
    }

    public void checkAnswer(final View v) {
        ImageView iv = (ImageView) findViewById(v.getId());
        String thisImage = (iv.getTag()).toString();
        int imgFileNum = Integer.parseInt((thisImage.toString()).substring(15));

        if (imgFileNum==correctAnswer) {
            if(correctOnFirstTry==true) {
                numCorrect++;
                TextView tv = (TextView) findViewById(R.id.score);
                tv.setText(String.valueOf(numCorrect));
            }
            v.setClickable(false);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.game1_qa_positive_click));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(numCorrect==10) {
                        finish();
                    } else {
                        startNewRound();
                    }
                }
            }, 3050);
        } else {
            v.setAlpha((float)0.5);
            v.setClickable(false);
            correctOnFirstTry = false;
        }
    }
}

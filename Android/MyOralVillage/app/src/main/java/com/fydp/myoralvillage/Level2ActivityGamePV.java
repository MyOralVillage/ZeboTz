package com.fydp.myoralvillage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;


public class Level2ActivityGamePV extends ActionBarActivity {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    //getValue for level from text file, set it here
    //right now, because there are no users, we'll set this to 0
    //(user starts from easiest level)
    public int difficultyLevel = 0;
    //public int[][][] problems = {{{10,1,100},{2,200,20},{500,50,5},{900,9,90},{60,600,6},{7,70,700},{300,30,3},{80,8,800},{4,400,40},{1,9,7},{9,4,6},{7,8,9},{10,90,70},{90,40,60},{70,80,90},{100,900,700},{900,400,600},{700,800,900},{30,5,600},{700,9,10}},{{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1}}};
    public int[][][] problems = {{{57,91,23},{519,567,20},{210,50,5},{91,9,90},{21,600,6},{372,70,700},{918,30,3},{80,8,800},{4,400,40},{1,9,7},{9,4,6},{7,8,9},{10,90,70},{90,40,60},{70,80,90},{100,900,700},{900,400,600},{700,800,900},{30,5,600},{700,9,10}},{{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1}}};


    public int correctAnswer=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_gamepv);

        if(!userHasViewedDemo) {
            startDemo();
        }
        startNewRound();
    }

    public void startDemo() {
        //go to demo activity (should be a separate activity)
    }

    public void resetGame(View v) {
        startNewRound();
    }

    public void startNewRound () {
        correctAnswer = 0;
        clearLinearLayouts();
        clearImageViews();
        Random rand = new Random();
        int problemNumber = rand.nextInt(20);
        generateRepresentation(problemNumber);
    }

    public void clearLinearLayouts() {
        int llOnes = getResources().getIdentifier("ones_layout", "id", getPackageName());
        LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
        LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
        onesLLParams.weight = 0f;
        ll_ones.setLayoutParams(onesLLParams);

        int llTens = getResources().getIdentifier("tens_layout", "id", getPackageName());
        LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
        LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
        tensLLParams.weight = 0f;
        ll_tens.setLayoutParams(tensLLParams);

        int llHundreds = getResources().getIdentifier("hundreds_layout", "id", getPackageName());
        LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
        LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
        hundredsLLParams.weight = 0f;
        ll_hundreds.setLayoutParams(hundredsLLParams);
    }

    public void clearImageViews() {
        for (int i = 1; i <= 10; i++) {
            String imgView_name_ones = "ones_representation"+i;
            int res_id_ones = getResources().getIdentifier(imgView_name_ones, "id", getPackageName());
            ImageView iv_ones = (ImageView) findViewById(res_id_ones);
            iv_ones.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams onesParams = (LinearLayout.LayoutParams) iv_ones.getLayoutParams();
            onesParams.weight = 0f;
            iv_ones.setLayoutParams(onesParams);

            String imgView_name_tens = "tens_representation"+i;
            int res_id_tens = getResources().getIdentifier(imgView_name_tens, "id", getPackageName());
            ImageView iv_tens = (ImageView) findViewById(res_id_tens);
            iv_tens.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams tensParams = (LinearLayout.LayoutParams) iv_tens.getLayoutParams();
            tensParams.weight = 0f;
            iv_tens.setLayoutParams(tensParams);

            String imgView_name_hundreds = "hundreds_representation"+i;
            int res_id_hundreds = getResources().getIdentifier(imgView_name_hundreds, "id", getPackageName());
            ImageView iv_hundreds = (ImageView) findViewById(res_id_hundreds);
            iv_hundreds.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams hundredsParams = (LinearLayout.LayoutParams) iv_hundreds.getLayoutParams();
            hundredsParams.weight = 0f;
            iv_hundreds.setLayoutParams(hundredsParams);
        }
    }
    public void generateRepresentation(int problemNumber) {
        //maximum 9 images (90, 900, 9000)
        //int pv=randomPlaceValue.nextInt(3)+1;
        correctAnswer = problems[difficultyLevel][problemNumber][0];
        int[] representation = getRepresentation();
        drawRepresentation(representation);
        generateAnswers(problems[difficultyLevel][problemNumber]);
    }

    public int[] getRepresentation(){
        int ones = correctAnswer%10;
        int tens = ((correctAnswer%100)-(correctAnswer%10))/10;
        int hundreds = (correctAnswer-(correctAnswer%100))/100;
        int[] representation = {hundreds,tens,ones};
        return representation;
    }

    public void drawRepresentation(int[] representation) {
        int numPVs = 0;
        if(representation[2]==0) {
            int llOnes = getResources().getIdentifier("ones_layout", "id", getPackageName());
            LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
            LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
            onesLLParams.weight = 0f;
            ll_ones.setLayoutParams(onesLLParams);
        } else {
            numPVs++;
        }
        if(representation[1]==0) {
            int llTens = getResources().getIdentifier("tens_layout", "id", getPackageName());
            LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
            LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
            tensLLParams.weight = 0f;
            ll_tens.setLayoutParams(tensLLParams);
        } else {
            numPVs++;
        }
        if(representation[0]==0) {
            int llHundreds = getResources().getIdentifier("hundreds_layout", "id", getPackageName());
            LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
            LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
            hundredsLLParams.weight = 0f;
            ll_hundreds.setLayoutParams(hundredsLLParams);
        } else {
            numPVs++;
        }

        if(representation[2]>0) {
            int llOnes = getResources().getIdentifier("ones_layout", "id", getPackageName());
            LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
            LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
            onesLLParams.weight = (float) (1.0/(double)numPVs);
            ll_ones.setLayoutParams(onesLLParams);
        }
        if(representation[1]>0) {
            int llTens = getResources().getIdentifier("tens_layout", "id", getPackageName());
            LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
            LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
            tensLLParams.weight = (float) (1.0/(double)numPVs);
            ll_tens.setLayoutParams(tensLLParams);
        }
        if(representation[0]>0) {
            int llHundreds = getResources().getIdentifier("hundreds_layout", "id", getPackageName());
            LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
            LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
            hundredsLLParams.weight = (float) (1.0/(double)numPVs);
            ll_hundreds.setLayoutParams(hundredsLLParams);
        }

        for (int i = 1; i <= representation[2]; i++) {
            String imgView_name = "ones_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            Drawable dBle = ContextCompat.getDrawable(this,R.drawable.game2_ones);
            Bitmap bMap = ((BitmapDrawable) dBle).getBitmap();

            iv.requestLayout();
            iv.setImageBitmap(bMap);
            iv.setTag("game2_ones");
            LinearLayout.LayoutParams onesParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
            onesParams.weight = (float)(1.0/(double)representation[2]);
            iv.setLayoutParams(onesParams);
            iv.setVisibility(View.VISIBLE);
        }

        for (int i = 1; i <= representation[1]; i++) {
            String imgView_name = "tens_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            Drawable dBle = ContextCompat.getDrawable(this,R.drawable.game2_tens);
            Bitmap bMap = ((BitmapDrawable) dBle).getBitmap();

            iv.requestLayout();
            iv.setImageBitmap(bMap);
            iv.setTag("game2_tens");
            LinearLayout.LayoutParams tensParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
            tensParams.weight = (float)(1.0/(double)representation[1]);
            iv.setLayoutParams(tensParams);
            iv.setVisibility(View.VISIBLE);
        }

        for (int i = 1; i <= representation[0]; i++) {
            String imgView_name = "hundreds_representation"+i;
            int res_id = getResources().getIdentifier(imgView_name, "id", getPackageName());
            ImageView iv = (ImageView) findViewById(res_id);

            Drawable dBle = ContextCompat.getDrawable(this,R.drawable.game2_hundreds);
            Bitmap bMap = ((BitmapDrawable) dBle).getBitmap();

            iv.requestLayout();
            iv.setImageBitmap(bMap);
            iv.setTag("game2_hundreds");
            LinearLayout.LayoutParams hundredsParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
            hundredsParams.weight = (float)(1.0/(double)representation[0]);
            iv.setLayoutParams(hundredsParams);
            iv.setVisibility(View.VISIBLE);
        }
    }

    public void generateAnswers(int[] problemNumber) {
        int wrongAnswer1 = problemNumber[1];
        int wrongAnswer2 = problemNumber[2];

        String[] filenames = new String[3];
        filenames[0] = "game2_answer"+wrongAnswer1;
        filenames[1] = "game2_answer"+wrongAnswer2;
        filenames[2] = "game2_answer"+correctAnswer;

        int[] takenPositions = {-1,-1,-1};
        displayAnswers(problemNumber, takenPositions);

    }

    public void displayAnswers(int[] answers, int[] takenPositions) {
        for (int i = 0; i < answers.length; i++) {

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

            String tvName = "tv_answer"+answerPosition;
            int resourceId = getResources().getIdentifier(tvName, "id", getPackageName());
            TextView tv = (TextView) findViewById(resourceId);
            tv.setText(String.valueOf(answers[i]));
            tv.setVisibility(tv.VISIBLE);
        }
    }

    public void checkAnswer(View v) {

        TextView tv = (TextView) findViewById(v.getId());
        int thisNumber = Integer.parseInt(tv.getText().toString());

        if (thisNumber==correctAnswer) {
            startNewRound();
        }
    }
}

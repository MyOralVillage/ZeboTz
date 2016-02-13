package com.fydp.myoralvillage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Level2ActivityGamePV extends ActionBarActivity {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    //getValue for level from text file, set it here
    //right now, because there are no users, we'll set this to 0
    //(user starts from easiest level)
    public int difficultyLevel = 2;
    //public int[][][] problems = {{{10,1,100},{2,200,20},{500,50,5},{900,9,90},{60,600,6},{7,70,700},{300,30,3},{80,8,800},{4,400,40},{1,9,7},{9,4,6},{7,8,9},{10,90,70},{90,40,60},{70,80,90},{100,900,700},{900,400,600},{700,800,900},{30,5,600},{700,9,10}},{{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1}}};
    //public int[][][] problems = {{{57,91,23},{519,567,20},{210,50,5},{91,9,90},{21,600,6},{372,70,700},{918,30,3},{80,8,800},{4,400,40},{1,9,7},{9,4,6},{7,8,9},{10,90,70},{90,40,60},{70,80,90},{100,900,700},{900,400,600},{700,800,900},{30,5,600},{700,9,10}},{{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1}}};
    public Integer[][][] problems = {{{10,1,100},{2,200,20},{500,50,5},{900,9,90},{60,600,6},{7,70,700},{300,30,3},{80,8,800},{4,400,40},{1,9,7},{9,4,6},{7,8,9},{10,90,70},{90,40,60},{70,80,90},{100,900,700},{900,400,600},{700,800,900},{30,5,600},{700,9,10}},{{25,20,5},{82,80,2},{79,70,9},{21,11,31},{53,63,43},{84,74,94},{66,65,67},{34,35,33},{98,99,97},{81,18,19},{12,21,23},{75,57,58},{79,97,78},{43,42,44},{49,94,48},{66,56,67},{22,33,11},{71,17,72},{25,52,26},{69,96,68},},{{852,20,5},{860,80,2},{130,70,9},{230,11,31},{231,63,43},{134,74,94},{334,65,67},{330,35,33},{452,99,97},{450,18,19},{980,21,23},{981,57,58},{79,97,78},{43,42,44},{49,94,48},{66,56,67},{22,33,11},{71,17,72},{25,52,26},{69,96,68},},{{25,20,5},{82,80,2},{79,70,9},{21,11,31},{53,63,43},{84,74,94},{66,65,67},{34,35,33},{98,99,97},{81,18,19},{12,21,23},{75,57,58},{79,97,78},{43,42,44},{49,94,48},{66,56,67},{22,33,11},{71,17,72},{25,52,26},{69,96,68}}};
    public int problemNumber = -1;
    public List<List<Integer>> questions = new ArrayList<>();

    public int correctAnswer=0;
    public boolean correctOnFirstTry = true;
    public int numCorrect = 0;
    public List<Integer> correctList = new ArrayList<>();

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

    public void startNewRound () {
        correctAnswer = 0;
        correctOnFirstTry = true;
        clearLinearLayouts();
        clearImageViews();

        if(questions.size()==0) {
            questions = generateQuestions(problems[difficultyLevel]);
        }
        do {
            Random rand = new Random();
            int randInt = questions.size();
            problemNumber = rand.nextInt(randInt);
        } while (questions.get(problemNumber)==null);
        generateRepresentation();
    }

    public void clearLinearLayouts() {
        int llOnes = getResources().getIdentifier("ones_layout_odd", "id", getPackageName());
        LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
        LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
        onesLLParams.weight = 0f;
        ll_ones.setLayoutParams(onesLLParams);

        int llOnesEven = getResources().getIdentifier("ones_layout_even", "id", getPackageName());
        LinearLayout ll_onesEven = (LinearLayout) findViewById(llOnesEven);
        LinearLayout.LayoutParams onesLLParamsEven = (LinearLayout.LayoutParams) ll_onesEven.getLayoutParams();
        onesLLParamsEven.weight = 0f;
        ll_onesEven.setLayoutParams(onesLLParamsEven);

        int llTens = getResources().getIdentifier("tens_layout_odd", "id", getPackageName());
        LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
        LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
        tensLLParams.weight = 0f;
        ll_tens.setLayoutParams(tensLLParams);

        int llTensEven = getResources().getIdentifier("tens_layout_even", "id", getPackageName());
        LinearLayout ll_tensEven = (LinearLayout) findViewById(llTensEven);
        LinearLayout.LayoutParams tensLLParamsEven = (LinearLayout.LayoutParams) ll_tensEven.getLayoutParams();
        tensLLParamsEven.weight = 0f;
        ll_tensEven.setLayoutParams(tensLLParamsEven);

        int llHundreds = getResources().getIdentifier("hundreds_layout_odd", "id", getPackageName());
        LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
        LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
        hundredsLLParams.weight = 0f;
        ll_hundreds.setLayoutParams(hundredsLLParams);

        int llHundredsEven = getResources().getIdentifier("hundreds_layout_even", "id", getPackageName());
        LinearLayout ll_hundredsEven = (LinearLayout) findViewById(llHundredsEven);
        LinearLayout.LayoutParams hundredsLLParamsEven = (LinearLayout.LayoutParams) ll_hundredsEven.getLayoutParams();
        hundredsLLParamsEven.weight = 0f;
        ll_hundredsEven.setLayoutParams(hundredsLLParamsEven);
    }

    public void clearImageViews() {
        for (int i = 1; i <= 10; i++) {
            String imgView_name_ones = "ones_representation"+i;
            int res_id_ones = getResources().getIdentifier(imgView_name_ones, "id", getPackageName());
            ImageView iv_ones = (ImageView) findViewById(res_id_ones);
            iv_ones.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams onesParams = (LinearLayout.LayoutParams) iv_ones.getLayoutParams();
            onesParams.weight = 0f;
            onesParams.gravity = Gravity.CENTER;
            iv_ones.setLayoutParams(onesParams);

            String imgView_name_tens = "tens_representation"+i;
            int res_id_tens = getResources().getIdentifier(imgView_name_tens, "id", getPackageName());
            ImageView iv_tens = (ImageView) findViewById(res_id_tens);
            iv_tens.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams tensParams = (LinearLayout.LayoutParams) iv_tens.getLayoutParams();
            tensParams.weight = 0f;
            tensParams.gravity = Gravity.CENTER;
            iv_tens.setLayoutParams(tensParams);

            String imgView_name_hundreds = "hundreds_representation"+i;
            int res_id_hundreds = getResources().getIdentifier(imgView_name_hundreds, "id", getPackageName());
            ImageView iv_hundreds = (ImageView) findViewById(res_id_hundreds);
            iv_hundreds.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams hundredsParams = (LinearLayout.LayoutParams) iv_hundreds.getLayoutParams();
            hundredsParams.weight = 0f;
            hundredsParams.gravity = Gravity.CENTER;
            iv_hundreds.setLayoutParams(hundredsParams);
        }
    }
    public void generateRepresentation() {
        //maximum 9 images (90, 900, 9000)
        //int pv=randomPlaceValue.nextInt(3)+1;
        correctAnswer = questions.get(problemNumber).get(0);
        int[] representation = getRepresentation();
        drawRepresentation(representation);

        //questions = generateQuestions(problems[difficultyLevel][problemNumber]);
        generateAnswers(questions.get(problemNumber));
    }

    public List<List<Integer>> generateQuestions(Integer[][] a) {
        List<List<Integer>> c = new ArrayList<>(a.length);
        for(int i = 0; i < a.length; i++) {
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < a[i].length; j++) {
                temp.add(a[i][j]);
            }
            c.add(temp);
        }
        return c;
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
            int llOnes = getResources().getIdentifier("ones_layout_odd", "id", getPackageName());
            LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
            LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
            onesLLParams.weight = 0f;
            ll_ones.setLayoutParams(onesLLParams);

            int llOnesEven = getResources().getIdentifier("ones_layout_even", "id", getPackageName());
            LinearLayout ll_onesEven = (LinearLayout) findViewById(llOnesEven);
            LinearLayout.LayoutParams onesLLParamsEven = (LinearLayout.LayoutParams) ll_onesEven.getLayoutParams();
            onesLLParamsEven.weight = 0f;
            ll_onesEven.setLayoutParams(onesLLParamsEven);
        } else {
            numPVs++;
        }
        if(representation[1]==0) {
            int llTens = getResources().getIdentifier("tens_layout_odd", "id", getPackageName());
            LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
            LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
            tensLLParams.weight = 0f;
            ll_tens.setLayoutParams(tensLLParams);

            int llTensEven = getResources().getIdentifier("tens_layout_even", "id", getPackageName());
            LinearLayout ll_tensEven = (LinearLayout) findViewById(llTensEven);
            LinearLayout.LayoutParams tensLLParamsEven = (LinearLayout.LayoutParams) ll_tensEven.getLayoutParams();
            tensLLParamsEven.weight = 0f;
            ll_tensEven.setLayoutParams(tensLLParamsEven);
        } else {
            numPVs++;
        }
        if(representation[0]==0) {
            int llHundreds = getResources().getIdentifier("hundreds_layout_odd", "id", getPackageName());
            LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
            LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
            hundredsLLParams.weight = 0f;
            ll_hundreds.setLayoutParams(hundredsLLParams);

            int llHundredsEven = getResources().getIdentifier("hundreds_layout_even", "id", getPackageName());
            LinearLayout ll_hundredsEven = (LinearLayout) findViewById(llHundredsEven);
            LinearLayout.LayoutParams hundredsLLParamsEven = (LinearLayout.LayoutParams) ll_hundredsEven.getLayoutParams();
            hundredsLLParamsEven.weight = 0f;
            ll_hundreds.setLayoutParams(hundredsLLParamsEven);
        } else {
            numPVs++;
        }

        if(representation[2]>0) {
            if(representation[2]>1) {
                int llOnes = getResources().getIdentifier("ones_layout_odd", "id", getPackageName());
                LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
                LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
                onesLLParams.weight = (float) ((1.0/(double)numPVs)/2.0);
                ll_ones.setLayoutParams(onesLLParams);

                int llOnesEven = getResources().getIdentifier("ones_layout_even", "id", getPackageName());
                LinearLayout ll_onesEven = (LinearLayout) findViewById(llOnesEven);
                LinearLayout.LayoutParams onesLLParamsEven = (LinearLayout.LayoutParams) ll_onesEven.getLayoutParams();
                onesLLParamsEven.weight = (float) ((1.0/(double)numPVs)/2.0);
                ll_onesEven.setLayoutParams(onesLLParamsEven);
            } else {
                int llOnes = getResources().getIdentifier("ones_layout_odd", "id", getPackageName());
                LinearLayout ll_ones = (LinearLayout) findViewById(llOnes);
                LinearLayout.LayoutParams onesLLParams = (LinearLayout.LayoutParams) ll_ones.getLayoutParams();
                onesLLParams.weight = (float) (1.0/(double)numPVs);
                ll_ones.setLayoutParams(onesLLParams);
            }
        }
        if(representation[1]>0) {
            if(representation[1]>1) {
                int llTens = getResources().getIdentifier("tens_layout_odd", "id", getPackageName());
                LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
                LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
                tensLLParams.weight = (float) ((1.0/(double)numPVs)/2.0);
                ll_tens.setLayoutParams(tensLLParams);

                int llTensEven = getResources().getIdentifier("tens_layout_even", "id", getPackageName());
                LinearLayout ll_tensEven = (LinearLayout) findViewById(llTensEven);
                LinearLayout.LayoutParams tensLLParamsEven = (LinearLayout.LayoutParams) ll_tensEven.getLayoutParams();
                tensLLParamsEven.weight = (float) ((1.0/(double)numPVs)/2.0);
                ll_tensEven.setLayoutParams(tensLLParamsEven);
            } else {
                int llTens = getResources().getIdentifier("tens_layout_odd", "id", getPackageName());
                LinearLayout ll_tens = (LinearLayout) findViewById(llTens);
                LinearLayout.LayoutParams tensLLParams = (LinearLayout.LayoutParams) ll_tens.getLayoutParams();
                tensLLParams.weight = (float) (1.0/(double)numPVs);
                ll_tens.setLayoutParams(tensLLParams);
            }
        }
        if(representation[0]>0) {
            if(representation[0]>1) {
                int llHundreds = getResources().getIdentifier("hundreds_layout_odd", "id", getPackageName());
                LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
                LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
                hundredsLLParams.weight = (float) ((1.0/(double)numPVs)/2.0);
                ll_hundreds.setLayoutParams(hundredsLLParams);

                int llHundredsEven = getResources().getIdentifier("hundreds_layout_even", "id", getPackageName());
                LinearLayout ll_hundredsEven = (LinearLayout) findViewById(llHundredsEven);
                LinearLayout.LayoutParams hundredsLLParamsEven = (LinearLayout.LayoutParams) ll_hundredsEven.getLayoutParams();
                hundredsLLParamsEven.weight = (float) ((1.0/(double)numPVs)/2.0);
                ll_hundredsEven.setLayoutParams(hundredsLLParamsEven);
            } else {
                int llHundreds = getResources().getIdentifier("hundreds_layout_odd", "id", getPackageName());
                LinearLayout ll_hundreds = (LinearLayout) findViewById(llHundreds);
                LinearLayout.LayoutParams hundredsLLParams = (LinearLayout.LayoutParams) ll_hundreds.getLayoutParams();
                hundredsLLParams.weight = (float) (1.0 / (double) numPVs);
                ll_hundreds.setLayoutParams(hundredsLLParams);
            }
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
            if(representation[2]>1){
                if(i%2==1 || i==1) {
                    onesParams.gravity = Gravity.RIGHT;
                    onesParams.rightMargin = 0;
                } else {
                    onesParams.gravity = Gravity.LEFT;
                    onesParams.leftMargin = 0;
                }
            } else {
                onesParams.gravity = Gravity.CENTER;
            }
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
            if(representation[1]>1){
                if(i%2==1 || i==1) {
                    tensParams.gravity = Gravity.RIGHT;
                } else {
                    tensParams.gravity = Gravity.LEFT;
                }
            } else {
                tensParams.gravity = Gravity.CENTER;
            }
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
            if(representation[0]>1){
                if(i%2==1 || i==1) {
                    hundredsParams.gravity = Gravity.RIGHT;
                } else {
                    hundredsParams.gravity = Gravity.LEFT;
                }
            } else {
                hundredsParams.gravity = Gravity.CENTER;
            }
            iv.setLayoutParams(hundredsParams);
            iv.setVisibility(View.VISIBLE);
        }
    }

    public void generateAnswers(List<Integer> answers) {
        int wrongAnswer1 = answers.get(1);
        int wrongAnswer2 = answers.get(2);

        String[] filenames = new String[3];
        filenames[0] = "game2_answer"+wrongAnswer1;
        filenames[1] = "game2_answer"+wrongAnswer2;
        filenames[2] = "game2_answer"+correctAnswer;

        int[] takenPositions = {-1,-1,-1};
        displayAnswers(answers, takenPositions);

    }

    public void displayAnswers(List<Integer> answers, int[] takenPositions) {
        for (int i = 0; i < answers.size(); i++) {

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
            tv.setText(String.valueOf(answers.get(i)));
            tv.setVisibility(tv.VISIBLE);
            tv.setClickable(true);
        }
    }

    public void checkAnswer(View v) {

        TextView tv = (TextView) findViewById(v.getId());
        int thisNumber = Integer.parseInt(tv.getText().toString());

        if (thisNumber==correctAnswer) {
            if(correctOnFirstTry) {
                if(!correctList.contains(problemNumber)) {
                    numCorrect++;
                }
                correctList.add(problemNumber);
                questions.set(problemNumber,null);
            }

            TextView tvScore = (TextView) findViewById(R.id.score);
            tvScore.setText(String.valueOf(numCorrect));

            v.setClickable(false);
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.game1_qa_positive_click));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(numCorrect>=15 && difficultyLevel < 1) {
                        difficultyLevel++;
                        numCorrect=0;
                        correctList.clear();
                        questions.clear();
                        startNewRound();
                    } else if (numCorrect>=15 && difficultyLevel >= 1) {
                        finish();
                    } else {
                        startNewRound();
                    }
                }
            }, 3050);

        } else {
            correctOnFirstTry = false;
        }
    }
}

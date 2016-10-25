package com.fydp.myoralvillage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by paulj on 2016-10-22.
 *
 * This is the superclass implementing most of the functionlity for the PV (Placement Value) game
 *
 * The subclass contains the currency specific details
 */

public abstract class Level3ActivityGamePV extends GenericActivityGame {

     /*
    * Each individual test has the resource representing what is being bought, the answer the user
    * is expected to input, and the number of each currency unit that is being displayed
    *
    * Rather than rely on the user to do the math, this inputs the actual value of the item
    * and calculates the correct change. Seems safer
    */

    public class Individual_Test {
        int bought_item;
        float cost_item;
        Individual_Test(int ques, float cost)
        {
            bought_item = ques;
            cost_item = cost;
        }
    }


    Individual_Test[] tests;

    PerCurrency[] currencies;

    String format_string; // used to print the currency

    public ImageView item, imageSandbox;
    public int qNum;
    public double totalCash;
    public TextView cashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gamepv);
        Intent intent = getIntent();
        getExtras(intent);
        userHasViewedDemo = thisUser.demosViewed[7];
        if (!userHasViewedDemo) {
            startDemo();
            thisUser.demosViewed[7] = true; //TODO Change from constant to enum
        }

        cashView = (TextView) findViewById(R.id.cashView);

             //views to drop onto
        imageSandbox = (ImageView) findViewById(R.id.imageSandbox);

        //set drag listeners
        imageSandbox.setOnDragListener(new Level3ActivityGamePV.ChoiceDragListener());

        //setup question
        item = (ImageView) findViewById(R.id.item);

    }

    abstract void startDemo();  //

    public void setQuestion(int qNum) {
        correctOnFirstTry = true;
        scoringNumAttempts = 0;
        scoringCorrect = "error";
        scoringSelectedAnswer = "error";
        scoringQuestion = "error";
        scoringAnswers[0] = "error";
        scoringAnswers[1] = "error";
        scoringAnswers[2] = "error";

        item.setImageResource(tests[qNum].bought_item);
        resetBoard();
    }

    public void resetBoard() {
        for(int i = 0; i < currencies.length; i++) {
            currencies[i].snap.setBackground(null);
            currencies[i].num =0;
            currencies[i].numView.setText("0");
        }
        totalCash = 0;
        cashView.setText(String.format(locale,format_string, totalCash));
    }

    public void checkAnswerPV(View v) {

        scoringAnswers[0] = "selectCash";
        scoringAnswers[1] = "selectCash";
        scoringAnswers[2] = "selectCash";

        scoringNumAttempts++;
        scoringQuestion = String.valueOf(tests[qNum].cost_item);
        scoringSelectedAnswer = String.valueOf(totalCash);

        if (totalCash == tests[qNum].cost_item) {
            scoringCorrect = "correct";
            if (correctOnFirstTry) {
                numCorrect++;
                String score_name = "starb" + numCorrect;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv = (ImageView) findViewById(R.id.score);
                tv.setImageResource(score_id);
            }
            writeToScore("level3placevalue.txt");
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            ++qNum;
           /*try {
               Thread.sleep(2000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }*/
            if (qNum < tests.length) {
                setQuestion(qNum);
            } else {
                thisUser.activityProgress[7] = true;
                onBackPressed();
            }
        } else {
            scoringCorrect = "incorrect";
            correctOnFirstTry = false;
            writeToScore("level3placevalue.txt");
            resetBoard();
        }
    }

       @Override
    public void onBackPressed() {
        if (!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        backButtonPressed = true;

        Intent intent = createIntent(Level3Activity.class);
        startActivity(intent);
        finish();
    }


    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the drag process
     */
    @SuppressLint("NewApi")
    public class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.VISIBLE);
                    TextView cashView = (TextView) findViewById(R.id.cashView);

                    ImageView dropped = (ImageView) view;
                    String droppedId = dropped.getResources().getResourceName(dropped.getId());
                    //String boxId = imageBox1.getResources().getResourceName(imageBox1.getId());
                    System.out.println(droppedId);

                    for(int i = 0; i < currencies.length; i++) {
                        if (droppedId.equals(currencies[i].drawable_name)) {
                            currencies[i].snap.setBackgroundResource(currencies[i].drawable_id);
                            ++currencies[i].num;
                            totalCash = totalCash + currencies[i].value;
                            cashView.setText(String.format(locale,format_string, totalCash));
                            currencies[i].numView.setText(String.valueOf(currencies[i].num));
                        }
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}

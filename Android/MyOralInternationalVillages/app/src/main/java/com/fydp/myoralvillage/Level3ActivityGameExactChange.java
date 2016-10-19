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
 * Created by paulj on 2016-10-16.
 *
 * This is the superclass incorporating the vast majority of the code for the Exact Change games
 *
 * This code makes the assumption that there are exactly 5 different currency units in any test. These may be coins or bills:/d
 *
 * Some currencies have decimal points, some do not.
 *
 * I suspect that what I really should be doing is to create the base layout out of a single xml file and then to explicitly add the images for
 * each countries currencies. That may be my next step. But, since I don't really know how to do that yet for the moment I'll just copy almost all of the xml file and change just the image parts.
 *
 * Hmm. Possible kludgy work around would be to create most of the image and then set the Drawable. Wow, that may work.
 *
 */

public class Level3ActivityGameExactChange extends GenericActivityGame {


    //text views being dragged and dropped onto

    public ImageView item, imageSandbox;
    int qNum;
    double totalCash;
    public TextView cashView;


   /*
    * Each individual test has the resource representing what is being bought, the answer the user
    * is expected to input, and the number of each currency unit that is being displayed
    *
    * Rather than rely on the user to do the math, this inputs the actual value of the item
    * and calculates the correct change. Seems safer
    */

    public class Individual_Test {
        int bought_item;
        float change;
        int[] numPaid;
        Individual_Test(int ques, float cost_item, int[] num_cur) //amt0, int amt1, int amt2, int amt3, int amt4)
        {
            bought_item = ques;

            float paid = 0;
          //  float fred = currencies[0].value* amt0;
            numPaid = new int[5];
            for(int i = 0; i < currencies.length;i++) {
                numPaid[i] = num_cur[i];
                paid += numPaid[i] * currencies[i].value;
            }
            change = paid - cost_item;
            if(paid <= 0) throw new AssertionError("paid should be > 0");
            if(change <0) throw new AssertionError("Change should be >=");
        }
    }

    Individual_Test[] tests;

    /*
     * Lets change all the sequential code into array lookups shall we?
     *
     * So, I need a class that has the appropriate information for each currency
     *
     * There is quite a bit of data here and maybe it should be split a little.
     *
     * Certainly names should be improved.
     */


    public class PerCurrency {
        TextView numView;  // I think used to hold the number of this bill
        TextView paidView; // I think used to hold the
        ImageView paid;    // The actual drawable. Maybe?
        ImageView bill; // Image ?? Note, includes coins despite the name
        ImageView snap;
        int num;    // Number transferred
        String drawable_name;
        /*
         * The following are currency specific and so NOT set in the constructor
         */
        int drawable_id;
        float value;


        PerCurrency(int n_num, int n_paid_view, int n_paid, int n_bill, int n_snap,
                    String nam, int drawable_id, float value) {
            numView = (TextView) findViewById(n_num);
            paidView = (TextView) findViewById(n_paid_view);
            paid = (ImageView) findViewById(n_paid);
            bill =  (ImageView) findViewById(n_bill);
            snap = (ImageView) findViewById(n_snap);
            num = 0;
            drawable_name = nam;
            bill.setImageDrawable(getDrawable(drawable_id));
            bill.setOnTouchListener(new Level3ActivityGameExactChange.ChoiceTouchListener());
        }

    }

    PerCurrency[] currencies;

    @Override
    protected void onStart() {

        setQuestion(qNum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gameexactchange);
        Intent intent = getIntent();
        getExtras(intent);

        if (!userHasViewedDemo) {
            startDemo();
        }
        cashView = (TextView) findViewById(R.id.cashView);

        //views to drop onto
        imageSandbox = (ImageView) findViewById(R.id.imageSandbox);

        //set drag listeners
        imageSandbox.setOnDragListener(new Level3ActivityGameExactChange.ChoiceDragListener());

        //setup question
        item = (ImageView) findViewById(R.id.item);

    }


    public void startDemo() {
        //method call to DemoActivity (separate activity)
        Intent intent = new Intent(this, Level3ActivityDemoPaAngaExactChange.class);
        startActivity(intent);

    }

    public void setQuestion(int qNum) {
        correctOnFirstTry=true;
        scoringNumAttempts = 0;
        scoringCorrect = "error";
        scoringSelectedAnswer = "error";
        scoringQuestion = "error";
        scoringAnswers[0] = "error";
        scoringAnswers[1] = "error";
        scoringAnswers[2] = "error";

        item.setImageResource(tests[qNum].bought_item);
        totalCash = 0;
        for(int i = 0; i < currencies.length; i++) {
            currencies[i].paid.setImageResource(R.drawable.black_background);
            currencies[i].paidView.setText(String.valueOf(tests[qNum].numPaid[i]));
            if (tests[qNum].numPaid[i] > 0) {
                currencies[i].paid.setImageResource(currencies[i].drawable_id);
            }
            currencies[i].num = 0;
            currencies[i].numView.setText(String.valueOf(currencies[i].num));
            currencies[i].snap.setBackground(null);
        }

        cashView.setText(String.format(locale, "$T %.2f", totalCash));

    }

    public void resetBoard() {
        for(int i = 0; i < currencies.length; i++) {
            currencies[i].snap.setBackground(null);
            currencies[i].num =0;
            currencies[i].numView.setText("0");
        }
        totalCash = 0;
        cashView.setText(String.format(locale, "$T %.2f", totalCash));
    }

    public void checkAnswerPV(View v) {
        scoringAnswers[0] = "selectCash";
        scoringAnswers[1] = "selectCash";
        scoringAnswers[2] = "selectCash";

        scoringNumAttempts++;
        scoringQuestion = String.valueOf(tests[qNum].change);
        scoringSelectedAnswer = String.valueOf(totalCash);

        if (totalCash == tests[qNum].change) {
            scoringCorrect = "correct";
            if(correctOnFirstTry) {
                numCorrect++;
                String score_name = "starb" + numCorrect;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv = (ImageView) findViewById(R.id.score);
                tv.setImageResource(score_id);
            }
            writeToScore("level3exactchange.txt");
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            ++qNum;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (qNum < 10) {
                setQuestion(qNum);
            } else {
                thisUser.activityProgress[8] = true; // TODO
                onBackPressed();
            }

        } else {
            scoringCorrect = "incorrect";
            writeToScore("level3exactchange.txt"); //TODO - Change output name? Probably
            resetBoard();
        }

    }



    @Override
    public void onBackPressed() {
        if(!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        backButtonPressed = true;

        Intent intent = createIntent(Level3Activity.class); // TODO Pop all the way out ? Probably
                                                            // TODO : Make sure to test non admin behaviour
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
                            cashView.setText(String.format(locale, "$T %.2f", totalCash));
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


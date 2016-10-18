package com.fydp.myoralvillage;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by paulj on 2016-10-16.
 *
 * This is the superclass incorporating the vast majority of the code for the games
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
     * The following is some preliminary code to clean up things and make the code a lot more comprehensible.
     *
     * Doing this is straight forward but it will take time that I really don't have right now.
     *
     * So, at the moment this is just some dead code that compiled at one point
     *

    public class individual_test {
        int question;
        float answer;
        int[] paidAmount;
        individual_test(int ques, float ans, int amt0, int amt1, int amt2, int amt3, int amt4)
        {
            question = ques;
            answer = ans;
            paidAmount = new int[5];
            paidAmount[0] = amt0;
            paidAmount[1] = amt1;
            paidAmount[2] = amt2;
            paidAmount[3] = amt3;
            paidAmount[4] = amt4;
        }
    };

    individual_test test1 = new individual_test(R.drawable.bananas_pa_anga, 0.5f, 0, 1, 0, 0, 0);

    individual_test[] tests = {
            new individual_test(R.drawable.bananas_pa_anga, 0.5f, 0, 1, 0, 0, 0),
            new individual_test(R.drawable.bananas_pa_anga, 0.5f, 0, 1, 0, 0, 0)
    };
    */

    /*
     * Gods, this code is awful in so many respects.
     *
     * We only allow 10 questions. 10 is hardcoded in.
     */

    int[] questions = {
            R.drawable.bananas_pa_anga,
            R.drawable.basket_fish_pa_anga,
            R.drawable.basketoranges_pa_anga,
            R.drawable.bike_pa_anga,
            R.drawable.calculator_pa_anga,
            R.drawable.chair_pa_anga,
            R.drawable.chicken_pa_anga,
            R.drawable.clock_pa_anga,
            R.drawable.flipflops_pa_anga,
            R.drawable.notebook_pa_anga,
            R.drawable.pencil_pa_anga,
            R.drawable.mobilephone_pa_anga
    };

    float[] answers = {
            0.5f, // bananas
            1.5f, // basket fish
            1.5f, // basket oranges
            10.0f, // bike
            3.0f,  // calculator
            3.0f,  // chair
            5.0f,  // chicken
            2.5f,  // clock
            14.5f,  // flipflops
            2.0f,  // notebook
            4.5f,  // pencil
            0.0f   // mobilephone
    };

    int[][] paidAmount = {
            // Centi, 1, 5, 10, 20
            {0, 1, 0, 0, 0}, // bananas, paid $T1, change is $T0.50
            {0, 2, 1, 0, 0}, // fish, paid $T5, change $T 1.50
            {0, 0, 2, 0, 0}, // oranges, paid $T5, change $T 1.50
            {0, 0, 0, 0, 8}, // bike, paid $160, change $T 10
            {0, 0, 1, 1, 0}, // calculator, paid 15, change 3
            {0, 0, 2, 2, 1}, // chair, paid 50, change 2
            {0, 0, 0, 1, 1}, // chicken, paid $T30, change 5
            {0, 0, 2, 1, 0}, // clock, paid 20, change $2.50
            {0, 0, 0, 1, 1}, // flipflops, paid $30, change price to $15.50 change $14.50  (change price to $15.50)
            {0, 0, 2, 0, 0}, // notebook - price $T8, paid $T10, change $T2
            {0, 0, 1, 0, 0}, // pencil - price $0,50, paid $T5, change $T4.50
            {0, 0, 0, 0, 10} // mobilephone - price T$ 200, paid $

    };

    /*
     * Lets change all the sequential code into array lookups shall we?
     *
     * So, I need a class that has the appropriate information for each currency
     *
     * There is quite a bit of data here and maybe it should be split a little.
     *
     * Certainly names should be improved.
     */

    final int num_currencies = 5;
    public class PerCurrency {
        TextView numView;  // I think used to hold the number of this bill
        TextView paidView; // I think used to hold the
        ImageView paid;    // The actual drawable. Maybe?
        ImageView bill; // Image ?? Note, includes coins despite the name
        ImageView snap;
        int num;    // Number transferred
        int drawable_id;
        String drawable_name;
        float value;


        PerCurrency(int n_num, int n_paid_view, int n_paid, int n_bill, int n_snap,
                    int drawable, String nam, float val) {
            numView = (TextView) findViewById(n_num);
            paidView = (TextView) findViewById(n_paid_view);
            paid = (ImageView) findViewById(n_paid);
            bill =  (ImageView) findViewById(n_bill);
            snap = (ImageView) findViewById(n_snap);
            num = 0;
            drawable_name = nam;
            drawable_id = drawable;
            bill.setImageDrawable(getDrawable(drawable));
            bill.setOnTouchListener(new Level3ActivityGameExactChange.ChoiceTouchListener());
            value=val;
        }

    }

    PerCurrency[] currencies;

    final String currency_1_name = "com.fydp.myoralvillage:id/currency_1_bill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gameexactchange);
        Intent intent = getIntent();
        getExtras(intent);

        userHasViewedDemo = thisUser.demosViewed[8];  // TODO Refactor

        if (!userHasViewedDemo) {
            startDemo();
        }
        cashView = (TextView) findViewById(R.id.cashView);

        currencies = new PerCurrency[num_currencies];

        // I can probably even do this better. But it will do for the nonce
        currencies[0] = new PerCurrency(R.id.currency_1_num, R.id.currency_1_paidview, R.id.currency_1_paid,
                                        R.id.currency_1_bill, R.id.currency_1_billSnap, R.drawable.pa_anga_50_senti,
                                        currency_1_name, 0.5f);
        currencies[1] = new PerCurrency(R.id.currency_2_num, R.id.currency_2_paidview, R.id.currency_2_paid,
                R.id.currency_2_bill, R.id.currency_2_billSnap, R.drawable.pa_anga_1,
                "com.fydp.myoralvillage:id/currency_2_bill", 1.0f);

        currencies[2] = new PerCurrency(R.id.currency_3_num, R.id.currency_3_paidview, R.id.currency_3_paid,
                R.id.currency_3_bill, R.id.currency_3_billSnap, R.drawable.pa_anga_5,
                "com.fydp.myoralvillage:id/currency_3_bill", 5f);

        currencies[3] = new PerCurrency(R.id.currency_4_num, R.id.currency_4_paidview, R.id.currency_4_paid,
                R.id.currency_4_bill, R.id.currency_4_billSnap, R.drawable.pa_anga_10,
                "com.fydp.myoralvillage:id/currency_4_bill", 10f);

        currencies[4] = new PerCurrency(R.id.currency_5_num, R.id.currency_5_paidview, R.id.currency_5_paid,
                R.id.currency_5_bill, R.id.currency_5_billSnap, R.drawable.pa_anga_20,
                "com.fydp.myoralvillage:id/currency_5_bill", 20f);



        //views to drop onto
        imageSandbox = (ImageView) findViewById(R.id.imageSandbox);

        //set drag listeners
        imageSandbox.setOnDragListener(new Level3ActivityGameExactChange.ChoiceDragListener());

        //setup question
        item = (ImageView) findViewById(R.id.item);
        setQuestion(qNum);

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

        item.setImageResource(questions[qNum]);
        for(int i = 0; i < num_currencies; i++) {
            currencies[i].paid.setImageResource(R.drawable.black_background);
            currencies[i].paidView.setText(String.valueOf(paidAmount[qNum][i]));
            if (paidAmount[qNum][i] > 0) {
                currencies[i].paid.setImageResource(currencies[i].drawable_id);
            }
            currencies[i].num = 0;
            currencies[i].numView.setText(String.valueOf(currencies[i].num));
            currencies[i].snap.setBackground(null);
        }

        cashView.setText("$T " + String.format("%.2f", totalCash));

    }

    public void resetBoard() {
        for(int i = 0; i < num_currencies; i++) {
            currencies[i].snap.setBackground(null);
            currencies[i].num =0;
            currencies[i].numView.setText("0");
        }
        totalCash = 0;
        cashView.setText("$T " + String.format("%.2f", totalCash));
    }

    public void checkAnswerPV(View v) {
        scoringAnswers[0] = "selectCash";
        scoringAnswers[1] = "selectCash";
        scoringAnswers[2] = "selectCash";

        scoringNumAttempts++;
        scoringQuestion = String.valueOf(answers[qNum]);
        scoringSelectedAnswer = String.valueOf(totalCash);

        if (totalCash == answers[qNum]) {
            scoringCorrect = "correct";
            if(correctOnFirstTry==true) {
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
                thisUser.activityProgress[8] = true;
                onBackPressed();
            }

        } else {
            scoringCorrect = "incorrect";
            writeToScore("level3exactchange.txt");
            resetBoard();
        }

    }



    @Override
    public void onBackPressed() {
        if(!thisUser.userName.equals("admin")) {
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

                    for(int i = 0; i < num_currencies; i++) {
                        if (droppedId.equals(currencies[i].drawable_name)) {
                            currencies[i].snap.setBackgroundResource(currencies[i].drawable_id);
                            ++currencies[i].num;
                            totalCash = totalCash + currencies[i].value;
                            cashView.setText("$T " + String.format("%.2f", totalCash));
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


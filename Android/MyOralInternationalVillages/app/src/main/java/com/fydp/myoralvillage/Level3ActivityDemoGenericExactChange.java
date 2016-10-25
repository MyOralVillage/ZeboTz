package com.fydp.myoralvillage;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Paul on 10/19/2016
 *
 * This is a generic class implementing almost all of the currency specific demo code
 *
 * Note, there is too much code duplication with the regular Exact Change game. Probably will
 * refactor once again but first I want to just get to the point where it works with a little bit
 * of currency specific code
 */
public class Level3ActivityDemoGenericExactChange extends GenericActivityGame {

    /*
     * There is a great deal of code duplicated between the game and the demo
     *
     * Unfortunately, the demo code relies on a slightly different resource file
     *
     * This should be refactored so that the game and the demo use the same resource file, with the
     * addition of a couple of things (the finger that moves)
     *
     * Unfortunately, that turned out to be non trivial so I'll come back and do it later. First,
     * I want to complete the refactoring so that the games are all driven by nearly identical
     * code.
     *
     * Oh, the attempt to make PerCurrency its own class failed because it has to be inside
     * something that recognizes FindViewById. Again, probably fairly easy to lift out later
     * but not right now
     *
     * TODO : Refactor this code so that
     *      1) We only have one instance of the PerCurrency information across both games and
     *         both demos
     *      2) We reuse the identical resource file for the demos and games. That will ensure that
     *         if we change the demo the game remains in sync
     *
     * Note that this demo assumes that the item gets change back as 2 different bills. Make
     * sure to pick an example in each country which matches this criteria
     */



    PerCurrency[] currencies;

    String format_string;
    Locale locale = Locale.US;

    /*
     * The item that was bought, the amount paid for it, etc all vary by currency and country
     */

    int item_bought; // The id for the image of the item bought
    int bill_paid;   // The ID for the image of the bill used to buy the item
    float value_paid;
    int first_change_bill; // The index for the image of the first bill received as change
    int second_change_bill; // The index for the image of the second bill received as change

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_demoexactchange);
    }

    public void startDemo(){

        ImageButton mSkip = (ImageButton) findViewById(R.id.skip_button);
        ImageView imagefinger;
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.myrellayout);
        final ImageView finger1 = (ImageView) findViewById(R.id.finger1);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
        params.leftMargin = 30;
        params.topMargin = 350;
        rl.removeView(finger1);
        rl.addView(finger1, params);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int maxX = size.x;
        int maxY = size.y;

        System.out.println(maxX + "and " + maxY);



        int x = finger1.getLeft();
        int y = finger1.getTop();

        System.out.println(x + "and" + y);
        /**final ImageView finger3 = (ImageView) findViewById(R.id.finger3);
         final ImageView finger4 = (ImageView) findViewById(R.id.finger4); **/
        final ImageView item = (ImageView) findViewById(R.id.item);
        ImageView paidBox = (ImageView) findViewById(R.id.paidBox);

        final ImageView imageSandbox = (ImageView) findViewById(R.id.imageSandbox);
        final TextView cashView = (TextView) findViewById(R.id.cashView);
        cashView.setText(String.format(locale, format_string, 0f));
        item.setImageResource(item_bought);

        paidBox.setImageResource(bill_paid);


        // first animation/drag action
        final AnimationSet firstAnimationSet = new AnimationSet(true);

        TranslateAnimation animation = new TranslateAnimation(-10, 70,
                5, 41);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(3000);  // animation duration
        animation.setRepeatCount(0);  // animation repeat count
        animation.setRepeatMode(1);   // repeat animation (left to right, right to left )
        //      animation.setFillAfter(true);

        //img_animation.startAnimation(animation);  // start animation
        firstAnimationSet.addAnimation(animation);

        TranslateAnimation animation1 = new TranslateAnimation(5, 180,
                0, -150);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation1.setDuration(3000);  // animation duration
        animation1.setRepeatCount(0);  // animation repeat count
        animation1.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        animation.setFillEnabled(true);


        firstAnimationSet.addAnimation(animation1);

        finger1.startAnimation(firstAnimationSet);
        currencies[first_change_bill].bill.startAnimation(firstAnimationSet);
        firstAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                finger1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                //img_animation1.layout(1500,400,1500,1000);
                // Just adding a comment to see

                finger1.setVisibility(View.INVISIBLE);

                // How does snap work?
                currencies[first_change_bill].snap.setBackgroundResource(currencies[first_change_bill].drawable_id);
                cashView.setText(String.format(locale,format_string, currencies[first_change_bill].value));

            }
        });

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //final ImageView fingerX = (ImageView) findViewById(R.id.finger1);
                final ImageView imagefinger = new ImageView(Level3ActivityDemoGenericExactChange.this);
                imagefinger.setBackgroundResource(R.drawable.finger);
                int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(30, 50);
                params2.leftMargin = 400;
                params2.topMargin = 350;
                rl.addView(imagefinger, params2);


                // first animation/drag action
                final AnimationSet secondAnimationSet = new AnimationSet(true);

                TranslateAnimation animation2 = new TranslateAnimation(-10, -10,
                        5, 42);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation2.setStartOffset(500);
                animation2.setDuration(3000);  // animation duration
                animation2.setRepeatCount(0);  // animation repeat count
                animation2.setRepeatMode(1);   // repeat animation (left to right, right to left )
                //      animation.setFillAfter(true);

                //img_animation.startAnimation(animation);  // start animation
                secondAnimationSet.addAnimation(animation2);

                TranslateAnimation animation22 = new TranslateAnimation(5, 5,
                        0, -150);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation22.setStartOffset(500);
                animation22.setDuration(3000);  // animation duration
                animation22.setRepeatCount(0);  // animation repeat count
                animation22.setRepeatMode(1);   // repeat animation (left to right, right to left )
                animation22.setFillAfter(true);
                animation22.setFillEnabled(true);

                secondAnimationSet.addAnimation(animation22);
                imagefinger.startAnimation(secondAnimationSet);
                currencies[second_change_bill].bill.startAnimation(secondAnimationSet);
                secondAnimationSet.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // TODO Auto-generated method stub
                        //img_animation1.layout(1500,400,1500,1000);
                        currencies[second_change_bill].snap.setBackgroundResource(currencies[second_change_bill].drawable_id);
                        imagefinger.setVisibility(View.INVISIBLE);
                        cashView.setText(String.format(locale,format_string, currencies[first_change_bill].value+currencies[second_change_bill].value));
                    }
                });
            }
        }, 3400);


        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void exitDemo(View v) {
        finish();
    }
}

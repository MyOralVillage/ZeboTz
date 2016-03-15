package com.fydp.myoralvillage;

import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.os.Handler;

/**
 * Created by Usama on 3/2/2016.
 */
public class Level3ActivityDemoExactChange extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_demoexactchange);
        startDemo();
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
        final ImageView bill500 = (ImageView) findViewById(R.id.bill500);
        final ImageView bill2000 = (ImageView) findViewById(R.id.bill2000);
        final ImageView bill500Snap = (ImageView) findViewById(R.id.bill500Snap);
        final ImageView bill2000Snap = (ImageView) findViewById(R.id.bill2000Snap);
        final ImageView imageSandbox = (ImageView) findViewById(R.id.imageSandbox);
        final TextView cashView = (TextView) findViewById(R.id.cashView);
        item.setImageResource(R.drawable.corn);

        paidBox.setImageResource(R.drawable.bill_5000);




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
        bill500.startAnimation(firstAnimationSet);
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
                bill500Snap.setBackgroundResource(R.drawable.bill_500);
                finger1.setVisibility(View.INVISIBLE);
                cashView.setText("500/- Tsh");
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
                final ImageView imagefinger = new ImageView(Level3ActivityDemoExactChange.this);
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
                bill2000.startAnimation(secondAnimationSet);
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
                        bill2000Snap.setBackgroundResource(R.drawable.bill_2000);
                        imagefinger.setVisibility(View.INVISIBLE);
                        cashView.setText("2,500/- Tsh");
                    }
                });
            }
        }, 3400);



/**
        // first animation/drag action
        final AnimationSet secondAnimationSet = new AnimationSet(true);

        TranslateAnimation animation2 = new TranslateAnimation(-10, -10,
                5, 42);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation2.setStartOffset(3100);
        animation2.setDuration(3000);  // animation duration
        animation2.setRepeatCount(0);  // animation repeat count
        animation2.setRepeatMode(1);   // repeat animation (left to right, right to left )
        //      animation.setFillAfter(true);

        //img_animation.startAnimation(animation);  // start animation
        secondAnimationSet.addAnimation(animation2);

        TranslateAnimation animation22 = new TranslateAnimation(5, 5,
                0, -150);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation22.setStartOffset(3100);
        animation22.setDuration(3000);  // animation duration
        animation22.setRepeatCount(0);  // animation repeat count
        animation22.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation22.setFillAfter(true);
        animation22.setFillEnabled(true);

        secondAnimationSet.addAnimation(animation22);
        imagefinger.startAnimation(secondAnimationSet);
        bill2000.startAnimation(secondAnimationSet);
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
                bill2000Snap.setBackgroundResource(R.drawable.bill_2000);
                finger2.setVisibility(View.INVISIBLE);
                cashView.setText("2500");
            }
        });
**/
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

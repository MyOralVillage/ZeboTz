package com.fydp.myoralvillage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
        final ImageView finger1 = (ImageView) findViewById(R.id.finger1);
        final ImageView finger3 = (ImageView) findViewById(R.id.finger3);
        final ImageView finger3anim = (ImageView) findViewById(R.id.finger3);


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
                cashView.setText("500");
            }
        });

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





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

        finger3anim.setVisibility(View.VISIBLE);

        secondAnimationSet.addAnimation(animation22);
        finger3.startAnimation(secondAnimationSet);
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
                finger3.setVisibility(View.INVISIBLE);
                cashView.setText("2500");
            }
        });

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

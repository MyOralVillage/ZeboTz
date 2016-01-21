package com.fydp.myoralvillage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Level1ActivityGameTracing extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;

    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;


    private PaintView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gametracing);

        startGame();
    }


    public void startGame() {
        //game code in here as well as game method calls
        myView = (PaintView)findViewById(R.id.custView);
    }

    public void newNumber(View v) {
        final Random rand = new Random();

        int randNum = rand.nextInt(10);
        myView.setNumber(randNum);
    }
}

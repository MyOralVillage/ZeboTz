package com.fydp.myoralvillage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Level2ActivityGameOrdering extends AppCompatActivity {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    public int[] sequence = new int[5]; //this is where the sequence of numbers for the question is stored
    public int[] options = new int[5]; // this is where the option answers is stored (one of which is the correct answer)
    public int patternNumber; // this is the actual pattern itself. for ex: if patternNumber = 2, the numbers in the sequence will increment by 2
    public int missingPosition; //this stores the missing position
    public Random randomFirstNumber = new Random(); //this randomizes the first number for the question
    public Random randomPattern = new Random(); //this randomizes the patternNumber
    public Random randomMissingPosition = new Random(); //this randomizes the position that is missing (from 1-4)
    public Random randomWrongAnswers = new Random(); //this generates the 2 answer options that are wrong

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_gamefillintheblanks);
        generateSequence();

        // if(!userHasViewedDemo) {
        // startDemo();
        // }
        // startGame();
    }

    // public void startDemo() {
    //method call to DemoActivity (separate activity)
    //}


    public void generateSequence(){
        //generate a random first number, a random pattern and store the sequence in an array
        int[] testSequence;
        testSequence = new int[3];
        testSequence[0] = 4;
        sequence[0] = randomFirstNumber.nextInt(941) + 10;
        patternNumber = randomPattern.nextInt(4) + 2;
        sequence[1] = sequence[0]+ patternNumber;
        sequence[2] = sequence[1] + patternNumber;
        sequence[3] = sequence[2] + patternNumber;
        //generate a number that indicates the missing position
        missingPosition = randomMissingPosition.nextInt(4);
        // generate options
        options[0] = (sequence[0] - patternNumber);
        options[1] = (sequence[3] + patternNumber);
        options[2] = sequence[missingPosition];
        playGame(sequence, options, missingPosition);
    }

    public void playGame(int[]sequence, int[] options, int missingPosition) {
        // take the options array and display each number in a button at the bottom of the screen
        TextView sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
        if (sequence[missingPosition] == sequence[0]){
            sequenceView0.setText("____");
        }
        else {
            sequenceView0.setText(String.valueOf(sequence[0]));
        }

        TextView sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
        if (sequence[missingPosition] == sequence[1]) {
            sequenceView1.setText("_____");
        } else {
            sequenceView1.setText(String.valueOf(sequence[1]));
        }

        TextView sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
        if (sequence[missingPosition] == sequence[2]) {
            sequenceView2.setText("_____");
        } else {
            sequenceView2.setText(String.valueOf(sequence[2]));
        }

        TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
        if (sequence[missingPosition] == sequence[3]) {
            sequenceView3.setText("_____");
        } else {
            sequenceView3.setText(String.valueOf(sequence[3]));
        }

        TextView optionView0 = (TextView) findViewById(R.id.optionView0);
        optionView0.setText(String.valueOf(options[0]));

        TextView optionView1 = (TextView) findViewById(R.id.optionView1);
        optionView1.setText(String.valueOf(options[1]));

        TextView optionView2 = (TextView) findViewById(R.id.optionView2);
        optionView2.setText(String.valueOf(options[2]));
        // take the pattern array and display each number (with an underscore for the missing position) at the top of the screen
        // listen for an option being clicked on - react to correct answer accordingly
    }

    public void checkThisAnswer (View v) {
        finish();
    }
}

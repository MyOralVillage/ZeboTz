package com.fydp.myoralvillage;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Random;

public class Level2ActivityGameFillInTheBlanks extends AppCompatActivity {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    public int[] sequence = new int[5]; //this is where the sequence of numbers for the question is stored
    public int[] options = new int[5]; // this is where the option answers is stored (one of which is the correct answer)
    public int patternNumber; // this is the actual pattern itself. for ex: if patternNumber = 2, the numbers in the sequence will increment by 2
    public int missingPosition; //this stores the missing position
    public int missingAnswer; //this stores the position of the answer (1,2, or 3)
    public Random randomFirstNumber = new Random(); //this randomizes the first number for the question
    public Random randomPattern = new Random(); //this randomizes the patternNumber
    public Random randomMissingPosition = new Random(); //this randomizes the position that is missing (from 1-4)
    public Random randomMissingAnswer = new Random(); //this generates the random position of the answer (1,2 or 3)

    public int numCorrect = 0;
    public boolean firstAttempt = true;


    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean backButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_level2_gamefillintheblanks);
        Intent intent = getIntent();
        getExtras(intent);

        userHasViewedDemo = thisUser.demosViewed[3];


        Typeface myTypeFace = Typeface.createFromAsset(getAssets(),"fonts/TanzaFont.ttf");
        Button myButton = (Button)findViewById(R.id.optionView0);
        myButton.setTypeface(myTypeFace);
        myButton = (Button)findViewById(R.id.optionView1);
        myButton.setTypeface(myTypeFace);
        myButton = (Button)findViewById(R.id.optionView2);
        myButton.setTypeface(myTypeFace);
        TextView myTextView = (TextView)findViewById(R.id.sequenceView0);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView1);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView2);
        myTextView.setTypeface(myTypeFace);
        myTextView = (TextView)findViewById(R.id.sequenceView3);
        myTextView.setTypeface(myTypeFace);

       if(!userHasViewedDemo) {
           startDemo();
           thisUser.demosViewed[3] = true;
       }
       generateSequence();
   }

    public void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = intent.getIntExtra("USERSETTINGS_USERID", -1);
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
    }

   public void startDemo() {
       Intent intent = new Intent(this, Level2ActivityDemoFillInTheBlanks.class);
       startActivity(intent);
    }


    public void generateSequence(){
        firstAttempt = true;
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
        missingPosition = randomMissingPosition.nextInt(3);
        missingAnswer = randomMissingAnswer.nextInt(2);

        // generate options
        if (missingAnswer==0) {
            options[0] = sequence[missingPosition];
            options[1] = (sequence[0] - patternNumber);
            options[2] = (sequence[3] + patternNumber);

            playGame(sequence, options, missingPosition);
        }
        if (missingAnswer == 1) {
            options[0] = (sequence[0] - patternNumber);
            options[1] = sequence[missingPosition];
            options[2] = (sequence[3] + patternNumber);

            playGame(sequence, options, missingPosition);
        }

        if (missingAnswer == 2) {
            options[0] = (sequence[0] - patternNumber);
            options[1] = (sequence[3] + patternNumber);
            options[2] = sequence[missingPosition];
            playGame(sequence, options, missingPosition);
        }
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
        Button optionView0 = (Button) findViewById(R.id.optionView0);
        optionView0.setText(String.valueOf(options[0]));
        Button optionView1 = (Button) findViewById(R.id.optionView1);
        optionView1.setText(String.valueOf(options[1]));
        Button optionView2 = (Button) findViewById(R.id.optionView2);
        optionView2.setText(String.valueOf(options[2]));
        optionView0.setClickable(true);
        optionView1.setClickable(true);
        optionView2.setClickable(true);
        optionView0.setAlpha(1f);
        optionView1.setAlpha(1f);
        optionView2.setAlpha(1f);
     }

    public void checkThisAnswer (View v) {
        Button mButton = (Button) findViewById(v.getId());
        int thisNumber = Integer.parseInt(mButton.getText().toString());
        if (thisNumber==sequence[missingPosition]){
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            TextView sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
            TextView sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
            TextView sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
            TextView sequenceView3 = (TextView) findViewById(R.id.sequenceView3);
            /*sequenceView0.setText(String.valueOf(sequence[0]));
            sequenceView1.setText(String.valueOf(sequence[1]));
            sequenceView2.setText(String.valueOf(sequence[2]));
            sequenceView3.setText(String.valueOf(sequence[3]));*/
            sequenceView0.setText("tv");
            sequenceView1.setText("tv");
            sequenceView2.setText("tv");
            sequenceView3.setText("tv");
            if(firstAttempt) {
                numCorrect++;
            }
            mediaPlayer.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(numCorrect==15) {
                thisUser.activityProgress[3] = true;
                finish();
            } else {
                generateSequence();
            }
        }
        else {
            firstAttempt = false;
            mButton.setClickable(false);
            mButton.setAlpha(.5f);
        }

    }


    @Override
    public void onBackPressed() {
        backButtonPressed = true;
        finish();
    }

    public Intent createIntent(Class newActivity) {
        Intent intent = new Intent(this, newActivity);
        intent.putExtra("USERSETTINGS_USERNAME", thisUser.userName);
        intent.putExtra("USERSETTINGS_USERID", thisUser.userId);
        intent.putExtra("USERSETTINGS_DEMOSVIEWED", thisUser.demosViewed);
        intent.putExtra("USERSETTINGS_AVAILABLELEVELS", thisUser.availableLevels);
        intent.putExtra("USERSETTINGS_ACTIVITYPROGRESS", thisUser.activityProgress);
        return intent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        Intent intent = createIntent(Level2Activity.class);
        startActivity(intent);
    }

    public String stringifyUserSetting() {
        String thisString = thisUser.userName + "," + String.valueOf(thisUser.userId);
        for(int i = 0; i < thisUser.demosViewed.length; i++) {
            thisString += "," + String.valueOf(thisUser.demosViewed[i]);
        }
        for(int i = 0; i < thisUser.availableLevels.length; i++) {
            thisString += "," + String.valueOf(thisUser.availableLevels[i]);
        }
        for(int i = 0; i < thisUser.activityProgress.length; i++) {
            thisString += "," + String.valueOf(thisUser.activityProgress[i]);
        }

        return thisString;
    }

    public void updateUserSettings() {
        File userSettingsFile = new File(root, "usersettings.txt");

        try {
            // input the file content to the String "input"
            BufferedReader file = new BufferedReader(new FileReader(userSettingsFile));
            String line;
            String input = "";
            String newLine ="";
            String oldLine ="";

            while ((line = file.readLine()) != null) {
                String[] thisLine = line.split(",");
                if(thisLine[0].equals(thisUser.userName)) {
                    newLine = stringifyUserSetting();
                    oldLine = line;
                }
                input += line + '\n';
            }

            file.close();

            if(!oldLine.equals(newLine)) {
                input = input.replace(oldLine, newLine);
            }
            // write the new String with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(userSettingsFile);
            fileOut.write(input.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }
}

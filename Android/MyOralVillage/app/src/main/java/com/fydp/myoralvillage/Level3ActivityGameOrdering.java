package com.fydp.myoralvillage;

import android.content.ClipData;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.DragEvent;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;
import android.view.View.DragShadowBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level3ActivityGameOrdering extends AppCompatActivity {
    public int numCorrect;
    public int numWrong;
    public int score=0;
    public CharSequence dragData;
    public Button mNextButton;
    public TextView sequenceView0, sequenceView1, sequenceView2, sequenceView3, optionView0, optionView1, optionView2, optionView3;
    public boolean isCorrect = false;
    public int[][] problems = {{50,30,20,10},{60,40,70,50},{40,90,80,100},{10,20,90,40},{20,30,40,10},{40,20,80,60},{50,40,30,60},{20,10,30,20},{60,30,50,80},{50,90,30,20},{40,30,20,10},{90,50,60,20},{90,80,20,10},{30,20,50,10},{30,50,20,90},{10,20,40,30},{50,20,90,40},{60,80,70,20},{40,30,20,10},{40,60,50,30},{60,80,50,20},{30,20,10,40}};
    public int difficulty=0;
    public int[] randomNumbers = new int[4];
    public int[] orderedNumbers = new int[4];
    List<TextView> wrongAnswers = new ArrayList<TextView>();
    List<TextView> wrongBaskets = new ArrayList<TextView>();
    public boolean correctOnFirstTry;

    public boolean firstAttempt = true;
    public int numAnswersCorrect = 0;
    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

    int scoringNumAttempts = 0;
    String scoringCorrect;
    String scoringSelectedAnswer;
    String scoringQuestion;
    String[] scoringAnswers = new String[3];

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gameordering);
        Intent intent = getIntent();
        getExtras(intent);

        generateSequence();
    }

    public void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = intent.getIntExtra("USERSETTINGS_USERID", -1);
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
    }

    public void generateSequence() {
        correctOnFirstTry=true;
        scoringNumAttempts = 0;
        scoringCorrect = "error";
        scoringSelectedAnswer = "error";
        scoringQuestion = "";
        scoringAnswers[0] = "error";
        scoringAnswers[1] = "error";
        scoringAnswers[2] = "error";

        firstAttempt = true;

        isCorrect = false;
        numCorrect = 0;
        numWrong = 0;
//        Random r = new Random();
//        int randomNum = r.nextInt(problems[difficulty].length)-1;
//        generate an array of random numbers if diffciulty is
        for (int i=0; i<4; i++) {
            Random r = new Random();
            randomNumbers[i] = r.nextInt(999000) + 1000;
        }
//        randomNumbers = problems[randomNum];
        int[] tempNumbers = new int[4];
        tempNumbers = randomNumbers.clone();
        orderedNumbers = bubbleSort(tempNumbers);
        playGame(randomNumbers, orderedNumbers);
    }

    public int[] bubbleSort(int[] tempNum) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < tempNum.length - j; i++) {
                if (tempNum[i] > tempNum[i + 1]) {
                    tmp = tempNum[i];
                    tempNum[i] = tempNum[i + 1];
                    tempNum[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
        return tempNum;
    }

    public void playGame(int[] randomNumbers, int[] orderedNumbers) {
        // take the options array and display each number in a button at the bottom of the screen
        // views to drag
        sequenceView0 = (TextView) findViewById(R.id.sequenceView0);
        sequenceView1 = (TextView) findViewById(R.id.sequenceView1);
        sequenceView2 = (TextView) findViewById(R.id.sequenceView2);
        sequenceView3 = (TextView) findViewById(R.id.sequenceView3);

        sequenceView0.setText(String.valueOf(randomNumbers[0]));
        sequenceView1.setText(String.valueOf(randomNumbers[1]));
        sequenceView2.setText(String.valueOf(randomNumbers[2]));
        sequenceView3.setText(String.valueOf(randomNumbers[3]));

        optionView0 = (TextView) findViewById(R.id.optionView0);
        optionView1 = (TextView) findViewById(R.id.optionView1);
        optionView2 = (TextView) findViewById(R.id.optionView2);
        optionView3 = (TextView) findViewById(R.id.optionView3);

        optionView0.setText(String.valueOf(orderedNumbers[0]));
        optionView1.setText(String.valueOf(orderedNumbers[1]));
        optionView2.setText(String.valueOf(orderedNumbers[2]));
        optionView3.setText(String.valueOf(orderedNumbers[3]));

        //set touch listeners
        sequenceView0.setOnTouchListener(new ChoiceTouchListener());
        sequenceView1.setOnTouchListener(new ChoiceTouchListener());
        sequenceView2.setOnTouchListener(new ChoiceTouchListener());
        sequenceView3.setOnTouchListener(new ChoiceTouchListener());

        //set drag listeners
        optionView0.setOnDragListener(new ChoiceDragListener());
        optionView1.setOnDragListener(new ChoiceDragListener());
        optionView2.setOnDragListener(new ChoiceDragListener());
        optionView3.setOnDragListener(new ChoiceDragListener());

        scoringQuestion = orderedNumbers[0] +","+ orderedNumbers[1] +","+ orderedNumbers[2] +","+ orderedNumbers[3];
    }

    // private final class
    class ChoiceTouchListener implements OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    /*
                     * Drag details: we only need default behavior
                     * - clip data could be set to pass data as part of drag
                     * - shadow can be tailored
                     */
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    @SuppressLint("NewApi")
    private class ChoiceDragListener implements OnDragListener {

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
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    int number1 = Integer.valueOf(dropped.getText().toString());
                    int number2 = Integer.valueOf(dropTarget.getText().toString());
                    view.setVisibility(View.INVISIBLE);
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText().toString());
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    dropTarget.setTextColor(0xffffffff);
                    dropTarget.setBackgroundResource(R.drawable.basket_1_mango_full);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if (tag != null) {
                        //the tag is the view id already dropped here
                        int existingID = (Integer) tag;
                        //set the original view visible again
                        findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //set the tag in the target view being dropped on - to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());
                    //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
                    dropTarget.setOnDragListener(null);
                    //checking whether they are equal
                    if (number1 == number2) {
                        numCorrect = numCorrect+1;
                    }
                    else {
                        //set the original view visible again
                        numWrong=numWrong+1;
                        String IdAsString = dropped.getResources().getResourceName(dropped.getId());
                        IdAsString=IdAsString.substring(IdAsString.lastIndexOf('/') + 1);
                        String IdAsStringTar = dropTarget.getResources().getResourceName(dropTarget.getId());
                        IdAsStringTar=IdAsStringTar.substring(IdAsString.lastIndexOf('/') + 1);
                        wrongAnswers.add(dropped);
                        wrongBaskets.add(dropTarget);
                    }
                    if(numWrong+numCorrect==4) {
                    checkAnswer();
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

    // reset question
    public void reset(View view) {
        sequenceView0.setVisibility(TextView.VISIBLE);
        sequenceView1.setVisibility(TextView.VISIBLE);
        sequenceView2.setVisibility(TextView.VISIBLE);
        sequenceView3.setVisibility(TextView.VISIBLE);

        optionView0.setTag(null);
        optionView1.setTag(null);
        optionView2.setTag(null);
        optionView3.setTag(null);

        optionView0.setTypeface(Typeface.DEFAULT);
        optionView1.setTypeface(Typeface.DEFAULT);
        optionView2.setTypeface(Typeface.DEFAULT);
        optionView3.setTypeface(Typeface.DEFAULT);

        // reset text color
        optionView0.setTextColor(0x01060014);
        optionView1.setTextColor(0x01060014);
        optionView2.setTextColor(0x01060014);
        optionView3.setTextColor(0x01060014);

        // reset images
        optionView0.setBackgroundResource(R.drawable.basket_1);
        optionView1.setBackgroundResource(R.drawable.basket_1);
        optionView2.setBackgroundResource(R.drawable.basket_1);
        optionView3.setBackgroundResource(R.drawable.basket_1);

        optionView0.setOnDragListener(new ChoiceDragListener());
        optionView1.setOnDragListener(new ChoiceDragListener());
        optionView2.setOnDragListener(new ChoiceDragListener());
        optionView3.setOnDragListener(new ChoiceDragListener());
        generateSequence();
    }

    // reset question
    public void reset() {
        wrongBaskets.clear();
        wrongAnswers.clear();
        sequenceView0.setVisibility(TextView.VISIBLE);
        sequenceView1.setVisibility(TextView.VISIBLE);
        sequenceView2.setVisibility(TextView.VISIBLE);
        sequenceView3.setVisibility(TextView.VISIBLE);

        optionView0.setTag(null);
        optionView1.setTag(null);
        optionView2.setTag(null);
        optionView3.setTag(null);

        optionView0.setTypeface(Typeface.DEFAULT);
        optionView1.setTypeface(Typeface.DEFAULT);
        optionView2.setTypeface(Typeface.DEFAULT);
        optionView3.setTypeface(Typeface.DEFAULT);

        // reset text color
        optionView0.setTextColor(0x01060014);
        optionView1.setTextColor(0x01060014);
        optionView2.setTextColor(0x01060014);
        optionView3.setTextColor(0x01060014);

        // reset images
        optionView0.setBackgroundResource(R.drawable.basket_1);
        optionView1.setBackgroundResource(R.drawable.basket_1);
        optionView2.setBackgroundResource(R.drawable.basket_1);
        optionView3.setBackgroundResource(R.drawable.basket_1);

        optionView0.setOnDragListener(new ChoiceDragListener());
        optionView1.setOnDragListener(new ChoiceDragListener());
        optionView2.setOnDragListener(new ChoiceDragListener());
        optionView3.setOnDragListener(new ChoiceDragListener());
        generateSequence();
    }

    public void checkAnswer() {
        scoringNumAttempts++;
        scoringSelectedAnswer = optionView0.getText().toString() +","+ optionView1.getText().toString() +","+ optionView2.getText().toString() +","+ optionView3.getText().toString()+",";

        int checkTotal=wrongBaskets.size()+numCorrect;
        if ((numCorrect!=4)&&(checkTotal==4)) {
            correctOnFirstTry=false;
            scoringCorrect = "incorrect";
            writeToScore();
            firstAttempt = false;

            numWrong=0;
            // set bag of apples to visible
            for (int i=0; i<wrongAnswers.size(); i++) {
                TextView a = wrongAnswers.get(i);
                a.setVisibility(TextView.VISIBLE);
            }
//            sequenceView0.setVisibility(TextView.VISIBLE);
            for (int i=0; i<wrongBaskets.size(); i++) {
                TextView b = wrongBaskets.get(i);
                b.setVisibility(TextView.VISIBLE);
                b.setTypeface(Typeface.DEFAULT);
                b.setTextColor(0x01060014);
                b.setTag(null);
                b.setBackgroundResource(R.drawable.basket_1);
                b.setOnDragListener(new ChoiceDragListener());
            }
            wrongBaskets.clear();
            wrongAnswers.clear();
            optionView0.setText(String.valueOf(orderedNumbers[0]));
            optionView1.setText(String.valueOf(orderedNumbers[1]));
            optionView2.setText(String.valueOf(orderedNumbers[2]));
            optionView3.setText(String.valueOf(orderedNumbers[3]));
        }
        else if (numCorrect==4) {
            scoringCorrect = "correct";
            if(correctOnFirstTry==true) {
                score++;
                String score_name = "star" + score;
                int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
                ImageView tv = (ImageView) findViewById(R.id.score);
                tv.setImageResource(score_id);
            }
            writeToScore();
            // Toast.makeText(Level2ActivityGameOrdering.this, " This is right! ", Toast.LENGTH_LONG).show();
            if(firstAttempt) {
                numAnswersCorrect++;
            }
            // Toast.makeText(Level2ActivityGameOrdering.this, " This is right! ", Toast.LENGTH_LONG).show();
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
            mediaPlayer.start();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (numCorrect == 10) {
                        finish();
                    } else {
                        reset();
                    }
                }
            }, 3050);
        }
    }

    public void writeToScore() {
        try
        {
            if (!root.exists()) {
                root.mkdirs();
            }
            File userSettingsFile = new File(root, "level3ordering.txt");

            FileWriter writer = new FileWriter(userSettingsFile, true);
            writer.append(thisUser.userName + ",");
            writer.append(String.valueOf(thisUser.userId) + ",");
            writer.append(String.valueOf(scoringNumAttempts) + ",");
            writer.append(scoringCorrect + ",");
            writer.append(scoringSelectedAnswer);
            writer.append(scoringQuestion);

            writer.append("\n");
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        backButtonPressed = true;
        finish();
    }

    public void setHomeButton(View v) {
        homeButtonPressed = true;
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
        if(homeButtonPressed) {
            Intent intent = createIntent(GameMenuActivity.class);
            startActivity(intent);
        } else {
            Intent intent = createIntent(Level3Activity.class);
            startActivity(intent);
        }
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
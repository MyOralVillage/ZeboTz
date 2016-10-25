package com.fydp.myoralvillage;


import android.content.ClipData;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by paulj on 2016-10-17.
 *
 * This class is designed to implement the code that is common to all games.
 *
 * Reading and writing status, writing performance data (it is  not yet read) etc
 *
 * Basically, just trying to reduce code duplication and make my life easier down the road
 */

public abstract class GenericActivityGame extends AppCompatActivity {

    public boolean userHasViewedDemo = false;
    public int numCorrect=0;
    public boolean correctOnFirstTry=true;

    int scoringNumAttempts = 0;
    String scoringCorrect;
    String scoringSelectedAnswer;
    String scoringQuestion;
    String[] scoringAnswers = new String[3];
    Locale locale = Locale.US;

    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

     /*
     * This arguably shouldn't be here since it just applies to the currency
     * classes.
     *
     * But it is convenient
     *
     * TODO: Consider having a new element in the hierarchy just for currency games
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

        /*
         * Note that some of the information in here is applicable to only the exact change game
         *
         * TODO: Factor this into subclasses?
         */
        PerCurrency(int n_num, int n_paid_view, int n_paid, int n_bill, int n_snap,
                    String nam, int drawable, float val) {
            numView = (TextView) findViewById(n_num);
            paidView = (TextView) findViewById(n_paid_view);
            paid = (ImageView) findViewById(n_paid);
            bill =  (ImageView) findViewById(n_bill);
            snap = (ImageView) findViewById(n_snap);
            num = 0;
            drawable_name = nam;
            drawable_id = drawable;
            value = val;
            bill.setImageDrawable(getDrawable(drawable_id));
            bill.setOnTouchListener(new GenericActivityGame.ChoiceTouchListener());
        }
    }


    public void writeToScore(String score_name) {
        try
        {
            if (!root.exists()) {
                root.mkdirs(); // TODO do something if this fails. What I'm not sure
            }
            File userSettingsFile = new File(root, score_name);


            FileWriter writer = new FileWriter(userSettingsFile, true);
            writer.append(thisUser.userName);
            writer.append(",");
            writer.append(String.valueOf(thisUser.userId));
            writer.append(",");
            writer.append(String.valueOf(scoringNumAttempts));
            writer.append(",");
            writer.append(scoringCorrect);
            writer.append(",");
            writer.append(scoringSelectedAnswer);
            writer.append(",");
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

    public void setHomeButton(View v) {
        if (!thisUser.userName.equals("admin")) {
            updateUserSettings();
        }
        homeButtonPressed = true;

        final Intent intent = createIntent(GameMenuActivity.class);
        startActivity(intent);
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

    public void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = intent.getIntExtra("USERSETTINGS_USERID", -1);
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     */
    public final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * Drag details: we only need default behavior
             * - clip data could be set to pass data as part of drag
             * - shadow can be tailored
             */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

}

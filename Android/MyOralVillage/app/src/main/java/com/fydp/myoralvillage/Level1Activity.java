package com.fydp.myoralvillage;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Level1Activity extends AppCompatActivity {
    public UserSettings thisUser = new UserSettings();
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        Intent intent = getIntent();
        getExtras(intent);
    }

    public void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = intent.getIntExtra("USERSETTINGS_USERID", -1);
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
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

    public void goToLevel1QA(View v) {
        Intent intent = createIntent(Level1ActivityGameQA.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel1Tracing(View v) {
        Intent intent = createIntent(Level1ActivityGameTracing.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel1DualCoding(View v) {
        Intent intent = createIntent(Level1ActivityGameDualCoding.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        backButtonPressed = true;
        finish();
    }

    public void setHomeButton(View v) {
        homeButtonPressed = true;
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        if(backButtonPressed) {
            Intent intent = createIntent(GameMenuActivity.class);
            startActivity(intent);
        } else if (homeButtonPressed) {
            Intent intent = createIntent(GameMenuActivity.class);
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

package com.fydp.myoralvillage;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Level3Activity extends AppCompatActivity {
    public UserSettings thisUser = new UserSettings();
    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);

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

    public void goToLevel3PlaceValue(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent;
        if(thisUser.actual_country == UserSettings.Country.TANZANIA)
            intent = createIntent(Level3ActivityGameShillingPV.class);
        else if(thisUser.actual_country == UserSettings.Country.VANUATU)
            intent = createIntent(Level3ActivityGameVatuPV.class);
        else if(thisUser.actual_country == UserSettings.Country.TONGA)
            intent = createIntent(Level3ActivityGamePaAngaPV.class);
        else
            intent = createIntent(Level3ActivityGameShillingPV.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel3Ordering(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(Level3ActivityGameOrdering.class);
        startActivity(intent);
        finish();
    }

    public void goToLevel3ExactChange(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }

        Intent intent;
        if(thisUser.actual_country == UserSettings.Country.TANZANIA)
            intent = createIntent(Level3ActivityGameShillingExactChange.class);
        else if(thisUser.actual_country == UserSettings.Country.VANUATU)
            intent = createIntent(Level3ActivityGameVatuExactChange.class);
        else if(thisUser.actual_country == UserSettings.Country.TONGA)
            intent = createIntent(Level3ActivityGamePaAngaExactChange.class);
        else
            intent = createIntent(Level3ActivityGameShillingExactChange.class);

        startActivity(intent);
        finish();
    }

    public void setHomeButton(View v) {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(GameMenuActivity.class);
        startActivity(intent);
        homeButtonPressed = true;
        finish();
    }

    public void onBackPressed() {
        if(!thisUser.userName.equals("admin")) {
            if(thisUser.activityProgress[0]&&thisUser.activityProgress[1]&&thisUser.activityProgress[2]) {
                thisUser.availableLevels[1] = true;
            }
            if(thisUser.activityProgress[3]&&thisUser.activityProgress[4]&&thisUser.activityProgress[5]) {
                thisUser.availableLevels[2] = true;
            }
            updateUserSettings();
        }
        Intent intent = createIntent(GameMenuActivity.class);
        startActivity(intent);
        backButtonPressed = true;
        finish();
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

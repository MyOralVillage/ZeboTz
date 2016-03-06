package com.fydp.myoralvillage;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    UserSettings thisUser = new UserSettings();
    List<String> userNames = new ArrayList<>();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
    boolean newProfile = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View v) {
        setUserName();
        ParseFile();
        if(thisUser.userName.equals("admin")) {
            thisUser.userId = -1;
            for(int i = 0; i < thisUser.demosViewed.length; i++) {
                thisUser.demosViewed[i] = true;
            }
            for(int i = 0; i < thisUser.availableLevels.length; i++) {
                thisUser.availableLevels[i] = true;
            }
            for(int i = 0; i < thisUser.activityProgress.length; i++) {
                thisUser.activityProgress[i] = true;
            }
        } else if(newProfile) {
            thisUser.userId = userNames.size();
            WriteFile();
        }
        Intent intent = new Intent(this, GameMenuActivity.class);
        intent.putExtra("USERSETTINGS_USERNAME", thisUser.userName);
        intent.putExtra("USERSETTINGS_USERID", thisUser.userId);
        intent.putExtra("USERSETTINGS_DEMOSVIEWED", thisUser.demosViewed);
        intent.putExtra("USERSETTINGS_AVAILABLELEVELS", thisUser.availableLevels);
        intent.putExtra("USERSETTINGS_ACTIVITYPROGRESS", thisUser.activityProgress);
        startActivity(intent);
        finish();
    }

    public void setUserName() {
        EditText textField = (EditText) findViewById(R.id.et_username);
        thisUser.userName = textField.getText().toString().toLowerCase();
    }

    public void ParseFile() {
        File userSettingsFile = new File(root, "usersettings.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(userSettingsFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] thisLine = line.split(",");
                userNames.add(thisLine[0]);
                if(thisUser.userName.equals(thisLine[0])) {
                    setUserData(thisLine);
                    newProfile = false;
                }
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserData(String[] data) {
        thisUser.userId = Integer.parseInt(data[1]);
        for(int i = 0; i < thisUser.demosViewed.length; i++) {
            thisUser.demosViewed[i] = Boolean.parseBoolean(data[i+2]);
        }
        for(int i = 0; i < thisUser.availableLevels.length; i++) {
            thisUser.availableLevels[i] = Boolean.parseBoolean(data[i+11]);
        }
        for(int i = 0; i < thisUser.activityProgress.length; i++) {
            thisUser.activityProgress[i] = Boolean.parseBoolean(data[i+14]);
        }
    }

    public void WriteFile() {
        try
        {
            if (!root.exists()) {
                root.mkdirs();
            }
            File userSettingsFile = new File(root, "usersettings.txt");

            if (!thisUser.userName.equals("admin")) {
                FileWriter writer = new FileWriter(userSettingsFile, true);
                writer.append(thisUser.userName + ",");
                writer.append(String.valueOf(thisUser.userId));
                for (int i = 0; i < thisUser.demosViewed.length; i++) {
                    writer.append("," + thisUser.demosViewed[i]);
                }
                for (int i = 0; i < thisUser.availableLevels.length; i++) {
                    writer.append("," + thisUser.availableLevels[i]);
                }
                for (int i = 0; i < thisUser.activityProgress.length; i++) {
                    writer.append("," + thisUser.activityProgress[i]);
                }
                writer.append("\n");
                writer.flush();
                writer.close();
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

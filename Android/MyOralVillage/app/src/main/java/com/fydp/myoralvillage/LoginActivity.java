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

    UserSettings thisPlayer = new UserSettings();
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
        if(newProfile) {
            thisPlayer.userId = userNames.size();
            WriteFile();
        }
        Intent intent = new Intent(this, GameMenuActivity.class);
        startActivity(intent);
    }

    public void setUserName() {
        EditText textField = (EditText) findViewById(R.id.et_username);
        thisPlayer.userName = textField.getText().toString();
    }

    public void ParseFile() {
        File userSettingsFile = new File(root, "usersettings.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(userSettingsFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] thisLine = line.split(",");
                userNames.add(thisLine[0]);
                if(thisPlayer.userName.equals(thisLine[0])) {
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
        thisPlayer.userId = Integer.parseInt(data[1]);
        for(int i = 0; i < thisPlayer.demosViewed.length; i++) {
            thisPlayer.demosViewed[i] = Boolean.parseBoolean(data[i+2]);
        }
        for(int i = 0; i < thisPlayer.availableLevels.length; i++) {
            thisPlayer.availableLevels[i] = Boolean.parseBoolean(data[i+11]);
        }
        for(int i = 0; i < thisPlayer.activityProgress.length; i++) {
            thisPlayer.activityProgress[i] = Boolean.parseBoolean(data[i+14]);
        }
    }

    public void WriteFile() {
        try
        {
            if (!root.exists()) {
                root.mkdirs();
            }
            File userSettingsFile = new File(root, "usersettings.txt");
            FileWriter writer = new FileWriter(userSettingsFile);
            writer.append(thisPlayer.userName + ",");
            writer.append(String.valueOf(thisPlayer.userId));
            for(int i = 0; i < thisPlayer.demosViewed.length; i++) {
                writer.append(","+thisPlayer.demosViewed[i]);
            }
            for(int i = 0; i < thisPlayer.availableLevels.length; i++) {
                writer.append(","+thisPlayer.availableLevels[i]);
            }
            for(int i = 0; i < thisPlayer.activityProgress.length; i++) {
                writer.append(","+thisPlayer.activityProgress[i]);
            }
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

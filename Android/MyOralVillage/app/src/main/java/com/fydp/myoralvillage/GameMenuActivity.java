package com.fydp.myoralvillage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class GameMenuActivity extends ActionBarActivity {
    //Used to store user profile data
    public UserSettings thisUser = new UserSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        //Receive user profile data from LoginActivity
        Intent intent = getIntent();
        getExtras(intent);
        setLevelAvailability();
    }

    //Store user profile data within Activity
    public void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = intent.getIntExtra("USERSETTINGS_USERID", -1);
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
    }

    //Grant/deny level access to users based off of game completion status
    public void setLevelAvailability() {
        for (int i = 0; i < thisUser.availableLevels.length; i++) {
            String thisId = "btn_lvl" + String.valueOf(i+1);
            int resId = getResources().getIdentifier(thisId, "id", getPackageName());
            ImageButton thisButton = (ImageButton) findViewById(resId);
            thisButton.setClickable(true);
            thisButton.setAlpha(1.0f);
            if(!thisUser.availableLevels[i]) {
                thisButton.setClickable(false);
                thisButton.setAlpha(0.5f);
            }
        }
    }

    //onClick: go to L1 Menu
    public void goToLevel1(View v) {
        Intent intent = createIntent(Level1Activity.class);
        startActivity(intent);
        finish();
    }

    //Create intent containing user profile data to send to next activity
    public Intent createIntent(Class newActivity) {
        Intent intent = new Intent(this, newActivity);
        intent.putExtra("USERSETTINGS_USERNAME", thisUser.userName);
        intent.putExtra("USERSETTINGS_USERID", thisUser.userId);
        intent.putExtra("USERSETTINGS_DEMOSVIEWED", thisUser.demosViewed);
        intent.putExtra("USERSETTINGS_AVAILABLELEVELS", thisUser.availableLevels);
        intent.putExtra("USERSETTINGS_ACTIVITYPROGRESS", thisUser.activityProgress);
        return intent;
    }

    //onClick: go to L2 Menu
    public void goToLevel2(View v) {
        Intent intent = createIntent(Level2Activity.class);
        startActivity(intent);
        finish();
    }

    //onClick: go to L3 Menu
    public void goToLevel3(View v) {
        Intent intent = createIntent(Level3Activity.class);
        startActivity(intent);
        finish();
    }
}

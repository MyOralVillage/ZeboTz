package com.fydp.myoralvillage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class GameMenuActivity extends ActionBarActivity {

    UserSettings thisUser = new UserSettings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        Intent intent = getIntent();
        getExtras(intent);
    }

    public void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = Integer.parseInt(intent.getStringExtra("USERSETTINGS_USERID"));
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
    }

    public void goToLevel1(View v) {
        Intent intent = new Intent(this, Level1Activity.class);
        startActivity(intent);
    }

    public void goToLevel2(View v) {
        Intent intent = new Intent(this, Level2Activity.class);
        startActivity(intent);
    }

    public void goToLevel3(View v) {
        Intent intent = new Intent(this, Level3Activity.class);
        startActivity(intent);
    }
}

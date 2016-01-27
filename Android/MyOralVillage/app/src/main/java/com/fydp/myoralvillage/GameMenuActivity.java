package com.fydp.myoralvillage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;


public class GameMenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
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

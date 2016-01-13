package com.fydp.myoralvillage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class Level2Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
    }

    public void goToLevel2PlaceValue(View v) {
        Intent intent = new Intent(this, Level2ActivityGamePV.class);
        startActivity(intent);
    }

}

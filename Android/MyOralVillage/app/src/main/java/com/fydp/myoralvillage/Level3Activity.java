package com.fydp.myoralvillage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Level3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);
    }

    public void goToLevel3PlaceValue(View v) {
        Intent intent = new Intent(this, Level3ActivityGamePV.class);
        startActivity(intent);
    }

    public void goToLevel3Ordering(View v) {
        Intent intent = new Intent(this, Level3ActivityGameOrdering.class);
        startActivity(intent);
    }

    public void goToLevel3ValuesInCash(View v) {
        Intent intent = new Intent(this, Level3ActivityGameValuesInCash.class);
        startActivity(intent);
    }

    public void goToLevel3ExactChange(View v) {
        Intent intent = new Intent(this, Level3ActivityGameExactChange.class);
        startActivity(intent);
    }

}

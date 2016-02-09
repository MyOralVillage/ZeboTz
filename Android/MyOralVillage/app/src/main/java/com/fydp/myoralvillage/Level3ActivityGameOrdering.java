package com.fydp.myoralvillage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Level3ActivityGameOrdering extends AppCompatActivity {

    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gameordering);

        if(!userHasViewedDemo) {
            startDemo();
        }
        startGame();
    }

    public void startDemo() {
        //go to demo activity (separate activity)
    }

    public void startGame() {
        //game code in here as well as method calls to game functions
    }
}

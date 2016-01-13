package com.fydp.myoralvillage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Level1ActivityGameDualCoding extends AppCompatActivity {
    //getValue for userHasViewedDemo from text file, set it here
    //right now, because there are no users, we'll set this to false
    //(user must view demo every time)
    public boolean userHasViewedDemo = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gamedualcoding);
        if(!userHasViewedDemo){
            startDemo();
        }
        startGame();
    }

    public void startDemo() {
        //function call to go to this activity's demo (a separate activity)
    }

    public void startGame() {
        //Game code in here + game method calls
    }
}

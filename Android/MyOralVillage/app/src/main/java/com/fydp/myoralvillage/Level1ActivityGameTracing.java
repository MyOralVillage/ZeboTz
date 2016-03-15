 package com.fydp.myoralvillage;

        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.os.Environment;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.FileReader;
        import java.util.Random;

public class Level1ActivityGameTracing extends AppCompatActivity {
    private PaintView myView;
    private ImageView hImageViewPic;
    int[] images = { R.drawable.game1_tracing_0, R.drawable.game1_tracing_1, R.drawable.game1_tracing_2, R.drawable.game1_tracing_3, R.drawable.game1_tracing_4, R.drawable.game1_tracing_5, R.drawable.game1_tracing_6, R.drawable.game1_tracing_7, R.drawable.game1_tracing_8, R.drawable.game1_tracing_9 };
    public int numCorrect=0;
    public UserSettings thisUser = new UserSettings();
    File root = new File(Environment.getExternalStorageDirectory(), "Notes");

    boolean backButtonPressed = false;
    boolean homeButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gametracing);

        Intent intent = getIntent();
        getExtras(intent);

        startGame();
    }

    public void getExtras(Intent intent) {
        thisUser.userName = intent.getStringExtra("USERSETTINGS_USERNAME");
        thisUser.userId = intent.getIntExtra("USERSETTINGS_USERID", -1);
        thisUser.demosViewed = intent.getBooleanArrayExtra("USERSETTINGS_DEMOSVIEWED");
        thisUser.availableLevels = intent.getBooleanArrayExtra("USERSETTINGS_AVAILABLELEVELS");
        thisUser.activityProgress = intent.getBooleanArrayExtra("USERSETTINGS_ACTIVITYPROGRESS");
    }

    public void startGame() {
        myView = (PaintView)findViewById(R.id.custView);
        hImageViewPic = (ImageView)findViewById(R.id.image_view);

        final Random rand = new Random();
        int randNum = rand.nextInt(10);

        hImageViewPic.setImageResource(images[randNum]);
        myView.setNumber(randNum);
    }

    public void newNumber(View v) {
        final Random rand = new Random();
        int randNum = rand.nextInt(10);

        numCorrect++;
        String score_name = "star"+numCorrect;
        int score_id = getResources().getIdentifier(score_name, "drawable", getPackageName());
        ImageView tv = (ImageView) findViewById(R.id.score);
        tv.setImageResource(score_id);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
        mediaPlayer.start();
        if (numCorrect == 10) {
            finish();
        }
        hImageViewPic.setImageResource(images[randNum]);
        myView.setNumber(randNum);
    }


    @Override
    public void onBackPressed() {
        backButtonPressed = true;
        finish();
    }

    public void setHomeButton(View v) {
        homeButtonPressed = true;
        finish();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!thisUser.userName.equals("admin")) {
            thisUser.demosViewed[1] = true;
            thisUser.activityProgress[1] = true;
            updateUserSettings();
        }


        if(homeButtonPressed) {
            Intent intent = createIntent(GameMenuActivity.class);
            startActivity(intent);
        } else {
            Intent intent = createIntent(Level1Activity.class);
            startActivity(intent);
        }
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

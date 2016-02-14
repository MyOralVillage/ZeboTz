package com.fydp.myoralvillage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Level2ActivityDemoPV extends AppCompatActivity {
    int currentNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_demopv);
        startDemo();
    }

    public void startDemo() {

    }

    public void nextNumber(View v) {

        if (currentNumber==9) {
            finish();
        } else {
            currentNumber++;
            String numberFileName = "game1_demo_dualcoding_" + currentNumber;
            String representationFileName = "game1_dualcoding_" + currentNumber;

            int img_id_number = getResources().getIdentifier(numberFileName, "drawable", getPackageName());
            int img_id_representation = getResources().getIdentifier(representationFileName, "drawable", getPackageName());

            ImageView ivNumber = (ImageView) findViewById(R.id.img_number);
            ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation);

            ivNumber.setImageResource(img_id_number);
            ivRepresentation.setImageResource(img_id_representation);
            if(currentNumber!=0) {
                ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                iv.setVisibility(iv.VISIBLE);
            } else {
                ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                iv.setVisibility(iv.INVISIBLE);
            }
        }
    }

    public void prevNumber(View v) {
        if (currentNumber==1) {
            ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
            iv.setVisibility(iv.INVISIBLE);
        } else {

            ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
            iv.setVisibility(iv.VISIBLE);
        }
        currentNumber--;
        String numberFileName = "game1_demo_dualcoding_" + currentNumber;
        String representationFileName = "game1_dualcoding_" + currentNumber;

        int img_id_number = getResources().getIdentifier(numberFileName, "drawable", getPackageName());
        int img_id_representation = getResources().getIdentifier(representationFileName, "drawable", getPackageName());

        ImageView ivNumber = (ImageView) findViewById(R.id.img_number);
        ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation);

        ivNumber.setImageResource(img_id_number);
        ivRepresentation.setImageResource(img_id_representation);
    }
}

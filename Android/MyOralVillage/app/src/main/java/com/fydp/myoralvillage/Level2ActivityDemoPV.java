package com.fydp.myoralvillage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Level2ActivityDemoPV extends AppCompatActivity {
    int demoStage = 0;
    int demoType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_demopv);
        startDemo();
    }

    public void startDemo() {

    }

    public void nextNumber(View v) {
        demoStage++;
        if(demoType==0) {
            if (demoStage==3) {
                demoType++;
                nextNumberType2();
            } else {
                nextNumberType1();

                if(demoStage!=0) {
                    ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                    iv.setVisibility(iv.VISIBLE);
                } else {
                    ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                    iv.setVisibility(iv.INVISIBLE);
                }
            }
        } else if (demoType == 1) {
            nextNumberType2();
        }
    }

    public void nextNumberType2() {

        if(demoStage==4) {
            finish();
        }
        if(demoStage==3) {
            String repFileName = "game2_ones";
            String numeral = "3";

            int img_id_representation = getResources().getIdentifier(repFileName, "drawable", getPackageName());

            ImageView iv2a = (ImageView) findViewById(R.id.img_representation2a);
            ImageView iv2b = (ImageView) findViewById(R.id.img_representation2b);
            ImageView iv2c = (ImageView) findViewById(R.id.img_representation2c);
            TextView tvNumber = (TextView) findViewById(R.id.tv_answer);

            iv2a.setImageResource(img_id_representation);
            iv2b.setImageResource(img_id_representation);
            iv2c.setImageResource(img_id_representation);

            iv2a.setVisibility(View.VISIBLE);
            iv2b.setVisibility(View.VISIBLE);
            iv2c.setVisibility(View.VISIBLE);

            tvNumber.setText(numeral);
        }
    }

    public void nextNumberType1() {
        String representationFileName;
        String numeral;
        if(demoStage==1) {
            representationFileName = "game2_tens";
            numeral="10";
        } else if (demoStage == 2) {
            representationFileName = "game2_hundreds";
            numeral="100";
        } else {
            representationFileName = "game2_ones";
            numeral="1";
        }

        int img_id_representation = getResources().getIdentifier(representationFileName, "drawable", getPackageName());

        ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation2b);
        TextView tvNumber = (TextView) findViewById(R.id.tv_answer);

        ivRepresentation.setImageResource(img_id_representation);
        tvNumber.setText(numeral);
    }

    public void prevNumber(View v) {
        if (demoStage==1) {
            ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
            iv.setVisibility(iv.INVISIBLE);
        } else {

            ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
            iv.setVisibility(iv.VISIBLE);
        }
        demoStage--;
        if(demoStage==2) {
            demoType--;
            prevNumberType1();
        }
        if(demoType==0){
            prevNumberType1();
        } else {
            prevNumberType2();
        }
    }

    public void prevNumberType2() {

    }

    public void prevNumberType1() {
        String representationFileName;
        String numeral;
        if(demoStage==1) {
            representationFileName = "game2_tens";
            numeral = "10";
        } else if (demoStage == 2) {
            representationFileName = "game2_hundreds";
            numeral = "100";
        } else {
            representationFileName = "game2_ones";
            numeral = "1";
        }

        int img_id_representation = getResources().getIdentifier(representationFileName, "drawable", getPackageName());

        ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation2b);
        TextView tvNumber = (TextView) findViewById(R.id.tv_answer);

        tvNumber.setText(numeral);
        ivRepresentation.setImageResource(img_id_representation);
    }
}

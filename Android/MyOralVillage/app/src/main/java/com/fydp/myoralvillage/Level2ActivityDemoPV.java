package com.fydp.myoralvillage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
        if(demoType==0) {
            if (demoStage==2) {
                finish();
            } else {
                demoStage++;
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

                ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation);
                TextView tvNumber = (TextView) findViewById(R.id.tv_2);

                ivRepresentation.setImageResource(img_id_representation);
                tvNumber.setText(numeral);
                if(demoStage!=0) {
                    ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                    iv.setVisibility(iv.VISIBLE);
                } else {
                    ImageView iv = (ImageView) findViewById(R.id.btn_lvl1_dualCoding_previous);
                    iv.setVisibility(iv.INVISIBLE);
                }
            }
        }
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

        ImageView ivRepresentation = (ImageView) findViewById(R.id.img_representation);
        TextView tvNumber = (TextView) findViewById(R.id.tv_2);

        tvNumber.setText(numeral);
        ivRepresentation.setImageResource(img_id_representation);
    }
}

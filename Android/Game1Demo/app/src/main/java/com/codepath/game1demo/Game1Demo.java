package com.codepath.game1demo;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.codepath.game1demo.R;

public class Game1Demo extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageView hImageViewPic;

    private int currentImage = 0;
    int[] images = {R.drawable.owl1, R.drawable.owl2, R.drawable.owl3, R.drawable.owl4, R.drawable.owl5, R.drawable.owl6, R.drawable.owl7, R.drawable.owl8, R.drawable.owl9, R.drawable.owl10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_demo);
        hImageViewPic = (ImageView) findViewById(R.id.imageView);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton = (Button) findViewById(R.id.true_button);

        if (currentImage == 0) {
            mTrueButton.setVisibility(View.GONE);
        }


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImage--;
                mFalseButton.setVisibility(View.VISIBLE);
                mTrueButton.setVisibility(View.VISIBLE);
                currentImage = currentImage % images.length;

                if (currentImage == 0) {
                    mTrueButton.setVisibility(View.GONE);
                }

                if (currentImage == 9) {
                    mFalseButton.setVisibility(View.GONE);
                }



                hImageViewPic.setImageResource(images[currentImage]);
            }
        });


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentImage++;
                mTrueButton.setVisibility(View.VISIBLE);
                mFalseButton.setVisibility(View.VISIBLE);
                currentImage = currentImage % images.length;

                if (currentImage == 0) {
                    mTrueButton.setVisibility(View.GONE);
                }

                if (currentImage == 9) {
                    mFalseButton.setVisibility(View.GONE);
                }

                hImageViewPic.setImageResource(images[currentImage]);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game1_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
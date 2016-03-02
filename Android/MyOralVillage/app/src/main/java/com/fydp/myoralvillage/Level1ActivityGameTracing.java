 package com.fydp.myoralvillage;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.Random;

public class Level1ActivityGameTracing extends AppCompatActivity {
    private PaintView myView;
    private ImageView hImageViewPic;
    int[] images = { R.drawable.game1_tracing_0, R.drawable.game1_tracing_1, R.drawable.game1_tracing_2, R.drawable.game1_tracing_3, R.drawable.game1_tracing_4, R.drawable.game1_tracing_5, R.drawable.game1_tracing_6, R.drawable.game1_tracing_7, R.drawable.game1_tracing_8, R.drawable.game1_tracing_9 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gametracing);

        startGame();
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

        hImageViewPic.setImageResource(images[randNum]);
        myView.setNumber(randNum);
    }
}

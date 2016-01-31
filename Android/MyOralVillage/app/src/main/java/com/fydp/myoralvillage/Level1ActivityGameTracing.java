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

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;

    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;


    private PaintView myView;
    //private TextView textView;

    private ImageView hImageViewPic;
    //private Button iButton, gButton;

    private int currentImage = 0;
    int[] images = { R.drawable.game1_qa_answer0, R.drawable.game1_qa_answer1, R.drawable.game1_qa_answer2, R.drawable.game1_qa_answer3, R.drawable.game1_qa_answer4, R.drawable.game1_qa_answer5, R.drawable.game1_qa_answer6, R.drawable.game1_qa_answer7, R.drawable.game1_qa_answer8, R.drawable.game1_qa_answer9 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1_gametracing);

        startGame();
    }


    public void startGame() {
        //game code in here as well as game method calls
        //textView = (TextView)findViewById(R.id.myTextView);
        myView = (PaintView)findViewById(R.id.custView);
        hImageViewPic = (ImageView)findViewById(R.id.image_view);
       /*
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textView.setText("Touch coordinates : " +
                        String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
                return true;
            }
        }); */
    }

    public void newNumber(View v) {


        final Random rand = new Random();

        int randNum = rand.nextInt(10);

        hImageViewPic.setImageResource(images[randNum]);
        myView.setNumber(randNum);
    }
}

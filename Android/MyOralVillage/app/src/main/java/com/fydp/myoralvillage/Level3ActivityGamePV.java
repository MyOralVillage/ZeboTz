package com.fydp.myoralvillage;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Usama on 2/22/2016.
 */
public class Level3ActivityGamePV extends AppCompatActivity {


    //text views being dragged and dropped onto
    public ImageView item, imageSandbox, bill500Snap, bill1000Snap, bill2000Snap, bill5000Snap, bill10000Snap,nextArrow, bill500, bill1000, bill2000, bill5000, bill10000;
    public int num500, num1000, num2000, num5000, num10000, totalCash, qNum;
    public TextView cashView;
    int[] questions = {R.drawable.bike, R.drawable.blueberries, R.drawable.flipflops, R.drawable.mobilephone};
    int[] answers = {3000,3000, 30000, 500000};

    //public CharSequence dragData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gamepv);
        cashView = (TextView) findViewById(R.id.cashView);


        //get both sets of text views
        //views to drag
        bill500 = (ImageView) findViewById(R.id.bill500);
        bill1000 = (ImageView) findViewById(R.id.bill1000);
        bill2000 = (ImageView) findViewById(R.id.bill2000);
        bill5000 = (ImageView) findViewById(R.id.bill5000);
        bill10000 = (ImageView) findViewById(R.id.bill10000);


        //views to drop onto
        imageSandbox = (ImageView) findViewById(R.id.imageSandbox);

        //set touch listeners
        bill500.setOnTouchListener(new ChoiceTouchListener());
        bill1000.setOnTouchListener(new ChoiceTouchListener());
        bill2000.setOnTouchListener(new ChoiceTouchListener());
        bill5000.setOnTouchListener(new ChoiceTouchListener());
        bill10000.setOnTouchListener(new ChoiceTouchListener());


        //set drag listeners
        imageSandbox.setOnDragListener(new ChoiceDragListener());

        //initialize snap postiions
        bill500Snap = (ImageView) findViewById(R.id.bill500Snap);
        bill1000Snap = (ImageView) findViewById(R.id.bill1000Snap);
        bill2000Snap = (ImageView) findViewById(R.id.bill2000Snap);
        bill5000Snap = (ImageView) findViewById(R.id.bill5000Snap);
        bill10000Snap = (ImageView) findViewById(R.id.bill10000Snap);

        //setup question
        item = (ImageView) findViewById(R.id.item);
        setQuestion(qNum);

    }

    public void setQuestion (int qNum){
        item.setImageResource(questions[qNum]);
        totalCash=0;
        cashView.setText(String.valueOf(totalCash) + "/-Tsh");
        bill500Snap.setBackground(null);
        bill1000Snap.setBackground(null);
        bill2000Snap.setBackground(null);
        bill5000Snap.setBackground(null);
        bill10000Snap.setBackground(null);

    }

    public void resetBoard(){
        bill500Snap.setBackground(null);
        bill1000Snap.setBackground(null);
        bill2000Snap.setBackground(null);
        bill5000Snap.setBackground(null);
        bill10000Snap.setBackground(null);
        totalCash = 0;
        cashView.setText(String.valueOf(totalCash + "/-Tsh"));


    }

    public void checkAnswerPV(View v){

       if (totalCash == answers[qNum]){
               MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.applause);
               mediaPlayer.start();
               ++qNum;
           try {
               Thread.sleep(2000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
               setQuestion(qNum);
           }
           else{
               resetBoard();
           }
       }


    /**
     * ChoiceTouchListener will handle touch events on draggable views
     */
    private final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * Drag details: we only need default behavior
             * - clip data could be set to pass data as part of drag
             * - shadow can be tailored
             */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the drag process
     */
    @SuppressLint("NewApi")
    public class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.VISIBLE);

                    //view being dragged and dropped

                    bill500Snap = (ImageView) findViewById(R.id.bill500Snap);
                    bill1000Snap = (ImageView) findViewById(R.id.bill1000Snap);
                    bill2000Snap = (ImageView) findViewById(R.id.bill2000Snap);
                    bill5000Snap = (ImageView) findViewById(R.id.bill5000Snap);
                    bill10000Snap = (ImageView) findViewById(R.id.bill10000Snap);



                    TextView cashView = (TextView) findViewById(R.id.cashView);
                    ImageView dropped = (ImageView) view;
                    String droppedId = dropped.getResources().getResourceName(dropped.getId());
                    //String boxId = imageBox1.getResources().getResourceName(imageBox1.getId());
                    System.out.println(droppedId);


                    //update the text in the target view to reflect the data being dropped
                    if (droppedId.equals("com.fydp.myoralvillage:id/bill500")) {
                        bill500Snap.setBackgroundResource(R.drawable.bill_500);
                        ++num500;
                        totalCash = totalCash + 500;
                        cashView.setText(String.valueOf(totalCash) + "/-Tsh");
                    }

                    if (droppedId.equals("com.fydp.myoralvillage:id/bill1000")) {
                        bill1000Snap.setBackgroundResource(R.drawable.bill_1000);
                        ++num1000;
                        totalCash = totalCash + 1000;
                        cashView.setText(String.valueOf(totalCash) + "/-Tsh");

                    }


                    if (droppedId.equals("com.fydp.myoralvillage:id/bill2000")) {
                        bill2000Snap.setBackgroundResource(R.drawable.bill_2000);
                        ++num2000;
                        totalCash = totalCash + 2000;
                        cashView.setText(String.valueOf(totalCash) + "/-Tsh");
                    }

                    if (droppedId.equals("com.fydp.myoralvillage:id/bill5000")) {
                        bill5000Snap.setBackgroundResource(R.drawable.bill_5000);
                        ++num5000;
                        totalCash = totalCash + 5000;
                        cashView.setText(String.valueOf(totalCash) + "/-Tsh");
                    }

                    if (droppedId.equals("com.fydp.myoralvillage:id/bill10000")) {
                        bill10000Snap.setBackgroundResource(R.drawable.bill_10000);
                        ++num10000;
                        totalCash = totalCash + 10000;
                        cashView.setText(String.valueOf(totalCash) + "/-Tsh");
                    }
                     break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}


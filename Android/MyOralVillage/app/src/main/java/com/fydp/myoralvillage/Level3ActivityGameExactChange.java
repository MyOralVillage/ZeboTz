package com.fydp.myoralvillage;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Usama on 2/22/2016.
 */
public class Level3ActivityGameExactChange extends AppCompatActivity {


    //text views being dragged and dropped onto
    public ImageView imageButton1, imageButton2, imageSandbox, imageBox1, imageBox2;

    public CharSequence dragData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3_gameexactchange);

        //get both sets of text views
        //views to drag
        imageButton1 = (ImageView)findViewById(R.id.imageButton1);
        imageButton2 = (ImageView)findViewById(R.id.imageButton2);

        //views to drop onto
        imageSandbox = (ImageView)findViewById(R.id.imageSandbox);

        //set touch listeners
        imageButton1.setOnTouchListener(new ChoiceTouchListener());
        imageButton2.setOnTouchListener(new ChoiceTouchListener());


        //set drag listeners
        imageSandbox.setOnDragListener(new ChoiceDragListener());
    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     *
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
     *
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
                    view.setVisibility(View.INVISIBLE);

                    //view being dragged and dropped

                    imageBox1 = (ImageView)findViewById(R.id.imageBox1);
                    imageBox2 = (ImageView)findViewById(R.id.imageBox2);
                    ImageView dropped = (ImageView) view;
                    String droppedId = dropped.getResources().getResourceName(dropped.getId());
                    //String boxId = imageBox1.getResources().getResourceName(imageBox1.getId());
                    System.out.println(droppedId);
                    System.out.println("com.fydp.myoralvillage:id/imageButton1");

                    //update the text in the target view to reflect the data being dropped
                    if (droppedId.equals("com.fydp.myoralvillage:id/imageButton1")) {
                        imageBox1.setBackgroundResource(R.drawable.game1_demo_dualcoding_0);
                    }

                    if (droppedId.equals("com.fydp.myoralvillage:id/imageButton2")) {
                        imageBox2.setBackgroundResource(R.drawable.game1_demo_dualcoding_2);
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


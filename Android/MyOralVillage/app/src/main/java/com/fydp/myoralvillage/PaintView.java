package com.fydp.myoralvillage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usama on 06/01/2016.
 */

public class PaintView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private static final int TOUCH_TOLERANCE_DP = 24;
    //private static final int BACKGROUND = 0xFFDDDDDD;
    private static final int BACKGROUND = 0x00AAAAAA;
    private List<Point> mPoints = new ArrayList<Point>();
    private int mLastPointIndex = 0;
    private int mTouchTolerance;
    private boolean isPathStarted = false;
    public int redDot;
    private int currentNumber;
    public boolean completedNumber = false;

    public void setParameter(int currentNumber)
    {
        this.currentNumber=currentNumber;
    }

    public PaintView(Context context) {
        super(context);
        mCanvas = new Canvas();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //mPaint.setStrokeWidth(18);
        mPaint.setStrokeWidth(48);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);

        // TODO just test points
        Point p1 = new Point(20, 20);
        Point p2 = new Point(100, 100);
        Point p3 = new Point(200, 250);
        Point p4 = new Point(280, 400);
        Point p5 = new Point(350, 600);
        Point p6 = new Point(400, 500);
        mPoints.add(p1);
        mPoints.add(p2);
        mPoints.add(p3);
        mPoints.add(p4);
        mPoints.add(p5);
        mPoints.add(p6);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCanvas = new Canvas();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //mPaint.setStrokeWidth(12);
        mPaint.setStrokeWidth(30);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);

        // TODO just test points
        Point p1 = new Point(20, 20);
        Point p2 = new Point(100, 100);
        Point p3 = new Point(200, 250);
        Point p4 = new Point(280, 400);
        Point p5 = new Point(350, 600);
        Point p6 = new Point(400, 500);
        mPoints.add(p1);
        mPoints.add(p2);
        mPoints.add(p3);
        mPoints.add(p4);
        mPoints.add(p5);
        mPoints.add(p6);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCanvas = new Canvas();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        clear();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(BACKGROUND);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPaint);

        // TODO remove if you dont want points to be drawn

        if (this.currentNumber != 8 && this.currentNumber != 0){
            redDot = 1;
        }
        else if(this.currentNumber == 8){
            redDot = 25;
        }
        else if(this.currentNumber == 0){
            redDot = 21;
        }

        int i = 1;
        for (Point point : mPoints) {
            if (i == redDot) {
                mPaint.setColor(Color.RED);
            }
            canvas.drawPoint(point.x, point.y, mPaint);
            mPaint.setColor(Color.BLACK);
            i++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up(x, y);
                invalidate();
                break;
        }
        return true;
    }

    private void touch_start(float x, float y) {

        if (checkPoint(x, y, mLastPointIndex)) {
            mPath.reset();
            // user starts from given point so path can beis started
            isPathStarted = true;
        } else {
            // user starts move from point which doen's belongs to mPinst list
            isPathStarted = false;
        }

    }

    private void touch_move(float x, float y) {
// draw line with finger move
        if (isPathStarted) {
            mPath.reset();
            Point p = mPoints.get(mLastPointIndex);
            mPath.moveTo(p.x, p.y);
            if (checkPoint(x, y, mLastPointIndex + 1)) {
                p = mPoints.get(mLastPointIndex + 1);
                mPath.lineTo(p.x, p.y);
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                ++mLastPointIndex;
            } else {
                mPath.lineTo(x, y);
            }
        }
    }

    /**
     * Adds certain number
     */

    public void setNumber(int x){
        completedNumber = false;
        // TODO just test points
        if (x == 0) {
            setParameter(0);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();

            Point p23 = new Point(132, 310);
            Point p24 = new Point(159, 241);
            Point p25 = new Point(216, 193);
            Point p26 = new Point(300, 166);
            Point p27 = new Point(384, 193);
            Point p28 = new Point(441, 241);
            Point p29 = new Point(468, 310);
            Point p30 = new Point(468, 379);
            Point p31 = new Point(468, 448);
            Point p32 = new Point(468, 517);
            Point p33 = new Point(468, 586);

            Point p34 = new Point(441, 655);
            Point p35 = new Point(384, 703);
            Point p36 = new Point(300, 730);
            Point p37 = new Point(216, 703);
            Point p38 = new Point(159, 655);
            Point p39 = new Point(132, 586);
            Point p40 = new Point(132, 517);
            Point p41 = new Point(132, 448);
            Point p42 = new Point(132, 379);
            Point p43 = new Point(132, 310);
            //Point p44 = new Point(130, 448);
            //Point p44 = new Point(200, 410);

            mPoints.add(p23);
            mPoints.add(p24);
            mPoints.add(p25);
            mPoints.add(p26);
            mPoints.add(p27);
            mPoints.add(p28);
            mPoints.add(p29);
            mPoints.add(p30);
            mPoints.add(p31);
            mPoints.add(p32);
            mPoints.add(p33);
            mPoints.add(p34);
            mPoints.add(p35);
            mPoints.add(p36);
            mPoints.add(p37);
            mPoints.add(p38);
            mPoints.add(p39);
            mPoints.add(p40);
            mPoints.add(p41);
            mPoints.add(p42);
            mPoints.add(p43);

            invalidate();
            requestLayout();
        }
        if (x == 3) {
            setParameter(3);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();


             Point p17 = new Point(159, 237);
             Point p18 = new Point(216, 189);
             Point p19 = new Point(300, 162);
             Point p20 = new Point(384, 189);
             Point p21 = new Point(441, 237);
             Point p22 = new Point(468, 306);
             Point p23 = new Point(429, 375);
             Point p24 = new Point(351, 450);
             Point p25 = new Point(429, 525);
             Point p26 = new Point(468, 594);
             Point p27 = new Point(441, 663);
             Point p28 = new Point(384, 711);
             Point p29 = new Point(300, 738);
             Point p30 = new Point(216, 711);
             Point p31 = new Point(159, 663);


            mPoints.add(p17);
            mPoints.add(p18);
            mPoints.add(p19);
            mPoints.add(p20);
            mPoints.add(p21);
            mPoints.add(p22);
            mPoints.add(p23);
            mPoints.add(p24);
            mPoints.add(p25);
            mPoints.add(p26);
            mPoints.add(p27);
            mPoints.add(p28);
            mPoints.add(p29);
            mPoints.add(p30);
            mPoints.add(p31);

            invalidate();
            requestLayout();
        }
        else if (x == 1){
            setParameter(1);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(300, 166);
            Point p2 = new Point(300, 307);
            Point p3 = new Point(300, 448);
            Point p4 = new Point(300, 589);
            Point p5 = new Point(300, 730);
            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            invalidate();
            requestLayout();
        }
        else if (x == 2){
            setParameter(2);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();

            Point p16 = new Point(132, 310);
            Point p17 = new Point(159, 241);
            Point p18 = new Point(216, 193);
            Point p19 = new Point(300, 166);
            Point p20 = new Point(384, 193);
            Point p21 = new Point(441, 241);
            Point p22 = new Point(468, 310);
            Point p23 = new Point(384, 400);
            Point p24 = new Point(300, 490);
            Point p25 = new Point(216, 580);
            Point p26 = new Point(132, 670);
            Point p27 = new Point(216, 670);
            Point p28 = new Point(300, 670);
            Point p29 = new Point(384, 670);
            Point p30 = new Point(468, 670);


            mPoints.add(p16);
            mPoints.add(p17);
            mPoints.add(p18);
            mPoints.add(p19);
            mPoints.add(p20);
            mPoints.add(p21);
            mPoints.add(p22);
            mPoints.add(p23);
            mPoints.add(p24);
            mPoints.add(p25);
            mPoints.add(p26);
            mPoints.add(p27);
            mPoints.add(p28);
            mPoints.add(p29);
            mPoints.add(p30);
            invalidate();
            requestLayout();
        }
        else if (x == 8){
            setParameter(8);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();

            Point p30 = new Point(132, 310);
            Point p31 = new Point(159, 241);
            Point p32 = new Point(216, 193);
            Point p33 = new Point(300, 166);
            Point p34 = new Point(384, 193);
            Point p35 = new Point(441, 241);
            Point p36 = new Point(468, 310);
            Point p37 = new Point(441, 379);
            Point p38 = new Point(384, 427);
            Point p39 = new Point(300, 454);
            Point p40 = new Point(216, 481);
            Point p41 = new Point(159, 529);
            Point p42 = new Point(132, 598);
            Point p43 = new Point(159, 667);
            Point p44 = new Point(216, 715);
            Point p45 = new Point(300, 742);
            Point p46 = new Point(384, 715);
            Point p47 = new Point(441, 667);
            Point p48 = new Point(468, 598);
            Point p49 = new Point(441, 529);
            Point p50 = new Point(384, 481);
            Point p51 = new Point(300, 454);
            Point p52 = new Point(216, 427);
            Point p53 = new Point(159, 379);
            Point p54 = new Point(132, 310);


            mPoints.add(p30);
            mPoints.add(p31);
            mPoints.add(p32);
            mPoints.add(p33);
            mPoints.add(p34);
            mPoints.add(p35);
            mPoints.add(p36);
            mPoints.add(p37);
            mPoints.add(p38);
            mPoints.add(p39);
            mPoints.add(p40);
            mPoints.add(p41);
            mPoints.add(p42);
            mPoints.add(p43);
            mPoints.add(p44);
            mPoints.add(p45);
            mPoints.add(p46);
            mPoints.add(p47);
            mPoints.add(p48);
            mPoints.add(p49);
            mPoints.add(p50);
            mPoints.add(p51);
            mPoints.add(p52);
            mPoints.add(p53);
            mPoints.add(p54);
            invalidate();
            requestLayout();
        }
        else if (x == 7){
            setParameter(7);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(118, 217);
            //Point p2 = new Point(194, 62);
            Point p2 = new Point(300, 217);
            //Point p4 = new Point(362, 62);
            Point p3 = new Point(482, 217);
            Point p4 = new Point(398, 331);
            Point p5 = new Point(314, 448);
            Point p6 = new Point(230, 562);
            Point p7 = new Point(118, 709);
            //Point p13 = new Point(82, 254);


            mPoints.add(p1);
            //mPoints.add(p2);
            mPoints.add(p2);
            //mPoints.add(p4);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            //mPoints.add(p11);
            //mPoints.add(p13);
            invalidate();
            requestLayout();
        }
        else if (x == 9){
            setParameter(9);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();

            Point p19 = new Point(468, 721);
            Point p20 = new Point(468, 652);
            Point p21 = new Point(468, 583);
            Point p22 = new Point(468, 514);
            Point p23 = new Point(468, 445);
            Point p24 = new Point(468, 376);
            Point p25 = new Point(468, 307);
            Point p26 = new Point(441, 238);
            Point p27 = new Point(384, 190);
            Point p28 = new Point(300, 163);
            Point p29 = new Point(216, 190);
            Point p30 = new Point(159, 238);
            Point p31 = new Point(132, 307);
            Point p32 = new Point(159, 376);
            Point p33 = new Point(216, 424);
            Point p34 = new Point(300, 451);
            Point p35 = new Point(384, 424);
            Point p36 = new Point(441, 376);
            Point p37 = new Point(468, 307);


            mPoints.add(p19);
            mPoints.add(p20);
            mPoints.add(p21);
            mPoints.add(p22);
            mPoints.add(p23);
            mPoints.add(p24);
            mPoints.add(p25);
            mPoints.add(p26);
            mPoints.add(p27);
            mPoints.add(p28);
            mPoints.add(p28);
            mPoints.add(p29);
            mPoints.add(p30);
            mPoints.add(p31);
            mPoints.add(p32);
            mPoints.add(p33);
            mPoints.add(p34);
            mPoints.add(p35);
            mPoints.add(p36);
            mPoints.add(p37);



            invalidate();
            requestLayout();
        }
        else if (x == 6){
            setParameter(6);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            //Point p1 = new Point(185, 62);
            Point p2 = new Point(384, 118);
            Point p3 = new Point(300, 118);
            Point p4 = new Point(216, 148);
            Point p5 = new Point(159, 241);
            Point p6 = new Point(132, 310);
            Point p7 = new Point(132, 379);
            Point p8 = new Point(132, 466);
            Point p9 = new Point(132, 598);
            Point p10 = new Point(159, 667);
            Point p11 = new Point(216, 715);
            Point p12 = new Point(300, 742);
            Point p13 = new Point(384, 715);
            Point p14 = new Point(441, 667);
            Point p15 = new Point(468, 598);
            Point p16 = new Point(441, 529);
            Point p17 = new Point(384, 481);
            Point p18 = new Point(300, 454);
            Point p19 = new Point(216, 481);
            Point p20 = new Point(159, 529);

            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);
            mPoints.add(p18);
            mPoints.add(p19);
            mPoints.add(p20);
            invalidate();
            requestLayout();
        }
        else if (x == 4){
            setParameter(4);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();


            Point p2 = new Point(418, 736);
            Point p3 = new Point(418, 640);
            Point p4 = new Point(418, 544);
            Point p5 = new Point(418, 448);
            Point p6 = new Point(418, 352);
            Point p7 = new Point(418, 256);
            Point p8 = new Point(418, 160);
            Point p9 = new Point(334, 235);
            Point p10 = new Point(250, 310);
            Point p11 = new Point(166, 376);
            Point p12 = new Point(82, 448);
            Point p13 = new Point(166, 448);
            Point p14 = new Point(250, 448);
            Point p15 = new Point(334, 448);
            Point p16 = new Point(418, 448);
            Point p17 = new Point(502, 448);

            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);

            invalidate();
            requestLayout();
        }
        else  if (x == 5){
            setParameter(5);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(447, 160);
            Point p2 = new Point(384, 160);
            Point p3 = new Point(321, 160);
            Point p4 = new Point(258, 160);
            Point p5 = new Point(195, 160);
            Point p6 = new Point(132, 160);
            Point p7 = new Point(132, 235);
            Point p8 = new Point(132, 310);
            Point p9 = new Point(132, 385);
            Point p10 = new Point(132, 460);
            Point p11 = new Point(216, 418);
            Point p12 = new Point(300, 388);
            Point p13 = new Point(384, 415);
            Point p14 = new Point(441, 463);
            Point p15 = new Point(468, 523);
            Point p16 = new Point(468, 592);
            Point p17 = new Point(441, 661);
            Point p18 = new Point(384, 709);
            Point p19 = new Point(300, 736);
            Point p20 = new Point(216, 736);
            Point p21 = new Point(132, 709);

            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);
            mPoints.add(p18);
            mPoints.add(p19);
            mPoints.add(p20);
            mPoints.add(p21);
            invalidate();
            requestLayout();
        }
    }

    /**
     * Draws line.
     */
    private void touch_up(float x, float y) {
        mPath.reset();
        //completedNumber = false;
        if (checkPoint(x, y, mLastPointIndex + 1) && isPathStarted) {
            // move finished at valid point so draw whole line
            // start point
            Point p = mPoints.get(mLastPointIndex);
            mPath.moveTo(p.x, p.y);
            // end point
            p = mPoints.get(mLastPointIndex + 1);
            mPath.lineTo(p.x, p.y);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
            // increment point index
            ++mLastPointIndex;
            isPathStarted = false;
        }
        if(checkPoint(x,y,mLastPointIndex + 1) == false) {
            if(mLastPointIndex + 1 == mPoints.size()) {
                completedNumber = true;
            }
        }
    }

    /**
     * Sets paint
     *
     * @param paint
     */
    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    /**
     * Returns image as bitmap
     *
     * @return
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * Clears canvas
     */
    public void clear() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(BACKGROUND);
        mCanvas.setBitmap(mBitmap);
        invalidate();
    }

    /**
     * Checks if user touch point with some tolerance
     */
    private boolean checkPoint(float x, float y, int pointIndex) {
        if (pointIndex == mPoints.size()) {
            // out of bounds
            return false;
        }
        Point point = mPoints.get(pointIndex);
        //EDIT changed point.y to poin.x in the first if statement
        if (x > (point.x - mTouchTolerance) && x < (point.x + mTouchTolerance)) {
            if (y > (point.y - mTouchTolerance) && y < (point.y + mTouchTolerance)) {
                return true;
            }
        }
        return false;
    }

    public List<Point> getPoints() {
        return mPoints;
    }

    public void setPoints(List<Point> points) {
        this.mPoints = points;
    }

    private int dp2px(int dp) {
        Resources r = getContext().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }
}

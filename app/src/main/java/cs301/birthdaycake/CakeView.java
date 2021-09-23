package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class CakeView extends SurfaceView implements View.OnTouchListener{

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint greenPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 40.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;
    public static final float patternWidth = 25.0f;
    public static final float patternHeight = 20.0f;

    //making private CakeView variable
    private CakeModel cakeModel;
    private float patternX;
    private float patternY;
    private boolean drawText;
    private static final Paint redPaint = new Paint();
    private float x;
    private float y;

    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFF5500FF);  //violet-red
        //COLOR WAS CHANGED ABOVE HERE
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);

        setBackgroundColor(Color.WHITE);  //better than black default

        //initalize with a new cake model object
        cakeModel = new CakeModel();
        drawText = false;
        redPaint.setColor(Color.RED);
        greenPaint.setColor(Color.GREEN);
        x = 0;
        y = 0;

        patternX= -1;
        patternY= -1;
    }

    //returns reference to CakeModel object
    public CakeModel getCakeModel(){
        return cakeModel;
    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        canvas.drawRect(left, bottom - candleHeight, left + candleWidth + candleWidth/2, bottom, candlePaint);

        if (cakeModel.litOrNah) {
            //draw the outer flame for flame 1
            float flameCenterX = left + (candleWidth+(candleWidth/2)) / 2;
            float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

            //draw the inner flame
            flameCenterY += outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
        }

        //draw the wick
        float wickLeft = left + (candleWidth+(candleWidth/2))/2 - wickWidth/2;
        float wickTop = bottom - wickHeight - candleHeight;
        canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);

    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top;
        float bottom;

        if (cakeModel.frostOrNah){
            top = cakeTop;
            bottom = cakeTop + frostHeight;

            //Frosting on top
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
            top += frostHeight;
            bottom += layerHeight;

            //Then a cake layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
            top += layerHeight;
            bottom += frostHeight;

            //Then a second frosting layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
            top += frostHeight;
            bottom += layerHeight;

            //Then a second cake layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        }
        else{
            top = cakeTop;
            bottom = cakeTop;

            //Then a cake layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
            top += layerHeight;

            //Then a second cake layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        }

        //draw candles if true, otherwise don't
        if (cakeModel.candlesOrNah){
            //have to take into account numCandles from seekBar
            for (int i = 0; i < cakeModel.numCandle; i++){
                drawCandle(canvas, cakeLeft + ((i+1)*cakeWidth)/(cakeModel.numCandle+1) - candleWidth/2, cakeTop);
            }
        }

        //draw pattern
        if (patternX >= 0 && patternY >= 0){
            //TOP LEFT
            canvas.drawRect(patternX-patternWidth, patternY - patternHeight, patternX, patternY, greenPaint);
            //TOP RIGHT
            canvas.drawRect(patternX, patternY - patternHeight, patternX+patternWidth, patternY, redPaint);
            //BOTTOM LEFT
            canvas.drawRect(patternX-patternWidth, patternY, patternX, patternY + patternHeight, redPaint);
            //BOTTOM RIGHT
            canvas.drawRect(patternX, patternY, patternX+patternWidth, patternY + patternHeight, greenPaint);
        }
    }//onDraw

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
            patternX = event.getX();
            patternY = event.getY();
            v.invalidate();
            x = event.getX();
            y = event.getY();
            drawText = true;
            return true;
        }
        return false;
    }
}//class CakeView


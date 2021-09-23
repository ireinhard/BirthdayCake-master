package cs301.birthdaycake;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    //private instance variable
    private CakeView cakeView;
    private CakeModel cakeModel;

    //constructor
    public CakeController(CakeView cakeViewIn) {
        cakeView = cakeViewIn;
        cakeModel = cakeView.getCakeModel();
    }

    public void onClick(View v){
        //if clicked than should turn off candles
        cakeModel.litOrNah = !cakeModel.litOrNah;
        if (cakeModel.litOrNah == false){
            //R.id.blowOutButton("Re-light");
            //blowOutBut
        }
        //call invalidate to redraw
        cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.candlesSwitch:
                //if switch checked then want candle
                if (b == true){
                    cakeModel.candlesOrNah = true;
                }
                //if of then don't want candles
                else{
                    cakeModel.candlesOrNah = false;
                }
                //call invalidate to redraw
                cakeView.invalidate();
                break;
            case R.id.frostingSwitch:
                if (b == true){
                    cakeModel.frostOrNah = true;
                }
                else{
                    cakeModel.frostOrNah = false;
                }
                cakeView.invalidate();
                break;
        }
    }

    //one I will be using
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        //set num of candles to progress
        cakeModel.numCandle = i;
        cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

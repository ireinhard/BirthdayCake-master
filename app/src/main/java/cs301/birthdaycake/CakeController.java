package cs301.birthdaycake;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class CakeController {

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
        cakeModel.litOrNah = false;

        //call invalidate to redraw
        cakeView.invalidate();
    }

}

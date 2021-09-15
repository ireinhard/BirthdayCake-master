package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        //retrieves reference to cakeview object made in GUI and saves it
        CakeView cakeView = findViewById(R.id.cakeview);
        //creates new CakeController object
        CakeController cakeController = new CakeController(cakeView);

        //1 find button
        Button blowOutButton = (Button) findViewById(R.id.blowOutButton);

        //2 set the listener for the EVENT to be handled
        blowOutButton.setOnClickListener((View.OnClickListener) cakeView);
    }

    public void goodbye(View button){
        Log.i("button", "Goodbye");
        finishAffinity();
    }

}

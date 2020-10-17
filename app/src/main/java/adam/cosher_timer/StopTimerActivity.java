package adam.cosher_timer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StopTimerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Adam", "StopTimerActivity.onCreate()");

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
//        this.setContentView(R.layout.empty);
//        MainActivity.start_timer_widget = true; // blocking the buttons work
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Adam", "StopTimerActivity.onStart()");

    }


}

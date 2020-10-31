package adam.cosher_timer;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

/**
* This class is responsible for presenting the time to the user
* @version 2.0
* @author Adam L
*/
public class Timer extends CountDownTimer {

    protected RemoteViews widget_views; // the output text zone of the widget
    protected TextView timerOutput; // the output text zone
    @SuppressLint("StaticFieldLeak")
    protected static Timer timer = null; // mimics a Singleton
    protected long millis_time;
    protected long interval; // the time be7tween every tick of the clock
    public static long stopped_on_millis; // time when stop button was pushed

    /**
     * Constructor
     * @param millisInFuture the remaining amount of time in milliseconds
     * @param interval the time between every tick of the clock
     * @param timerOutput the TextView to which the time is written
     */
    public Timer(long millisInFuture, long interval, TextView timerOutput,  RemoteViews widget_views){
        super(millisInFuture, interval);
        this.timerOutput = timerOutput;
        this.millis_time = millisInFuture;
        this.interval = interval;
        Timer.timer = this;
        this.widget_views = widget_views;
    }

    /**
     * Mimics a Singleton
     * @param millisInFuture the remaining amount of time in milliseconds
     * @param interval the time between every tick of the clock
     * @param timerOutput the TextView to which the time is written
     * @return an instance of this Timer class
     */
    public static Timer getTimer(long millisInFuture, long interval, TextView timerOutput,  RemoteViews widget_views){
        if(Timer.timer == null)
            Timer.timer = new Timer(millisInFuture, interval, timerOutput, widget_views);
        return Timer.timer;
    }

    /**
     * Updates the text of the clock every second
     * Gets called on every tick of the timer
     * @param millisUntilFinished the remaining amount of time in milliseconds
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onTick(long millisUntilFinished) {
        Timer.stopped_on_millis = millisUntilFinished;

        // calculate the current time
        long secsUntilFinished = millisUntilFinished / 1000;
        long hours = secsUntilFinished / 3600;
        long minutes = secsUntilFinished / 60 - hours * 60;
        long seconds = secsUntilFinished - (secsUntilFinished / 60) * 60;
        long milliseconds = millisUntilFinished - secsUntilFinished * 1000;
        long hundredth_seconds = milliseconds / 10;
        long milli = milliseconds % 10;

        // generate Strings of the current time
        String h = hours >= 10 ? String.valueOf(hours) : "0" + hours;
        String m = minutes >= 10 ? String.valueOf(minutes) : "0" + minutes;
        String s = seconds >= 10 ? String.valueOf(seconds) : "0" + seconds;
        String hs = hundredth_seconds >= 10 ? String.valueOf(hundredth_seconds) : "0" + hundredth_seconds;
        String ms = "0" + milli; // milli < 10 will always be true

        // set text of the main textView
        this.timerOutput.setText(MainActivity.COUNTING+String.format("\n%s:%s:%s", h, m, s));
        // set text of the widgets' textView
        RemoteViews views = new RemoteViews(MainActivity.PACKAGE_NAME, R.layout.widget_layout); // StartTimerActivity.start_context.getPackageName()
        views.setTextViewText(R.id.timer_out_widget, MainActivity.COUNTING+String.format("\n%s:%s:%s", h, m, s));
        // update the widget
        if(WidgetTimer.widget_appWidgetManager != null)
            WidgetTimer.widget_appWidgetManager.updateAppWidget(WidgetTimer.WidgetId, views);
    }

    /**
     * Set the clocks' text to 'DONE', update the widget & alert the user
     * Gets called when the Timer is cancelled or when millisUntilFinished is 0
     */
    @Override
    public void onFinish() {
        this.timerOutput.setText(MainActivity.DONE);
//        this.widget_views.setTextViewText(R.id.timer_out_widget, MainActivity.DONE);

        RemoteViews views = new RemoteViews(MainActivity.PACKAGE_NAME, R.layout.widget_layout);
        views.setTextViewText(R.id.timer_out_widget, MainActivity.DONE);
        if(WidgetTimer.widget_appWidgetManager != null)
            WidgetTimer.widget_appWidgetManager.updateAppWidget(WidgetTimer.WidgetId, views);

        MainActivity.alert(MainActivity.mainContext);
    }

    /**
     * Cancels the Timer
     */
    public void cancel_timer(){
        this.millis_time = 0;
        this.cancel();
//        return getTimer();
    }

    public void update_time(long timeInMillis){
        this.millis_time = timeInMillis;
//        this.finalize();
    }
}

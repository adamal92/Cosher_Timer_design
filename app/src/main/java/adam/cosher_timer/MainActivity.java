package adam.cosher_timer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;

/**
 * This class creates the main UI elements of the program
 * @version 2.0
 * @author Adam L
 * */
public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static String TITLE; // title of the notification
    public static String TEXT; // textual description of the notification
    public static String DONE; // shown to the user when the timer stops
    public static String COUNTING; // shown to the user when the timer runs
    public static String PACKAGE_NAME = "adam.cosher_timer"; // the name of this apps' package
    protected Timer timer;
    protected long milliseconds_till_finish;
    protected boolean stopped_timer = false; // true if stop button was pressed
    @SuppressLint("StaticFieldLeak")
    public static MainActivity mainContext = null; // a reference to this objects' instance (casted later to Context)

    /**
     * Initializes some of the static members and creates a channel
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ru - russian, en - english, he - hebrew
        LocaleHelper.setLocale(this,"he"); // set the apps' language
        setContentView(R.layout.activity_main);  // R - the res (resources) folder

        MainActivity.TITLE = this.getString(R.string.notification_title);
        MainActivity.TEXT = this.getString(R.string.notification_text);
        MainActivity.DONE = this.getString(R.string.done);
        MainActivity.COUNTING = this.getString(R.string.counting);
        MainActivity.PACKAGE_NAME = this.getPackageName();

        MainActivity.mainContext = this; // create an intended memory leak
        create_channel(CHANNEL_ID, "channel name", "channel description");
    }

    /**
     * Starts the timer from the main start button
     * @param view the main activity's view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void start_timer(View view){
        Log.d("Adam", "MainActivity.start_timer()");
        final android.widget.TextView timer_out = this.findViewById(R.id.timer_out);
        RemoteViews widget_views =
                new RemoteViews("adam.cosher_timer", R.layout.widget_layout); // context.getPackageName() = adam.cosher_timer
        // get the desired time from the user
        Long ho, mi, sex;
        EditText H = this.findViewById(R.id.hours);
        EditText M = this.findViewById(R.id.mints);
        EditText S = this.findViewById(R.id.secs);

        // set default values
        ho = !H.getText().toString().equals("") ?  Long.valueOf(Integer.parseInt(H.getText().toString())) : Long.valueOf(4);
        mi = !M.getText().toString().equals("") ? Long.valueOf(Integer.parseInt(M.getText().toString())) : Long.valueOf(0);
        sex = !S.getText().toString().equals("") ?  Long.valueOf(Integer.parseInt(S.getText().toString())) : Long.valueOf(0);

        Log.d("Adam", String.format("h: %d m: %d s: %d", ho, mi, sex));

        long millis = ho*3600*1000 + mi*60*1000 + sex*1000; // total time in milliseconds
        // when stop button is clicked, remember the time on which the timer was stopped
        if(stopped_timer){ millis = Timer.stopped_on_millis; this.stopped_timer = false; }

        // create and set the timer
        if(this.milliseconds_till_finish == millis){
            this.timer = Timer.getTimer(millis, 1, timer_out, widget_views);
        } else{
            this.milliseconds_till_finish = millis;
            if(this.timer != null) this.timer.cancel_timer();
            this.timer = new Timer(millis, 1, timer_out, widget_views);
        }

        this.timer.update_time(millis);
        this.timer.start(); // starts the timer
    }

    /**
     * Stops the timer from the main stop button
     * @param view the main activity's view
     */
    public void stop_timer(View view){
        if(this.timer == null) return;
        this.timer.cancel_timer(); // shuts down the timer
        this.stopped_timer = true;
        // set the user input to default values
        EditText h = this.findViewById(R.id.hours);
        EditText m = this.findViewById(R.id.mints);
        EditText S = this.findViewById(R.id.secs);
        h.setText(null);
        m.setText(null);
        S.setText(null);
    }

    /**
     * Creates a notification channel. it has to be called before any notification is created
     * @param CHANNEL_ID
     * @param CHANNEL_NAME
     * @param CHANNEL_DESC
     */
    protected void create_channel(String CHANNEL_ID, String CHANNEL_NAME, String CHANNEL_DESC){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ // if the android version is later than Oreo
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    /**
     * Creates a notification
     * @param context the activity from which the notification is created
     */
    public static void alert(Context context){
        Log.i("Adam", "alert()");
        // build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle(MainActivity.TITLE)
                .setContentText(MainActivity.TEXT).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1, builder.build());
    }

}
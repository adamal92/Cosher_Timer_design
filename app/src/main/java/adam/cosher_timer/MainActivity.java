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

/**
 * This class creates the main UI elements of the program
 * @version 1.0
 * @author Adam L
 * */
public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static String TITLE; // title of the notification
    public static String TEXT; // textual description of the notification
    public static String DONE; // shown to the user when the timer stops
    public static String COUNTING; // shown to the user when the timer runs
    protected Timer timer;
    protected long milliseconds_till_finish;
    protected boolean stopped_timer = false; // true if stop button was pressed
    public static boolean stopped_timer_widget = false; // true if stop button was pressed
    public static boolean start_timer_widget = false; // true if start button was pressed
    @SuppressLint("StaticFieldLeak")
    public static Context mainContext = null; // a reference to this objects' instance (casted to Context)

    /**
     * Initializes some of the static members and creates a channel
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // R - the res (resources) folder

        MainActivity.TITLE = this.getString(R.string.notification_title);
        MainActivity.TEXT = this.getString(R.string.notification_text);
        MainActivity.DONE = this.getString(R.string.done);
        MainActivity.COUNTING = this.getString(R.string.counting);

        MainActivity.mainContext = this;
        create_channel(CHANNEL_ID, "channel name", "channel description");


//        alert(MainActivity.mainContext);
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("Adam", String.valueOf(MainActivity.start_timer_widget));
//        if(MainActivity.stopped_timer_widget){
//            MainActivity.stopped_timer_widget = false;
//            this.stop_timer(null);
//        }if(MainActivity.start_timer_widget){
//            MainActivity.start_timer_widget = false;
//            this.start_timer(null);
//        }
//    }

    /**
     * Starts the timer
     * @param view the main activity's view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void start_timer(View view){
        final android.widget.TextView timer_out = this.findViewById(R.id.timer_out);
        Long ho, mi, sex;
        EditText H = this.findViewById(R.id.hours);
        EditText M = this.findViewById(R.id.mints);
        EditText S = this.findViewById(R.id.secs);

        // set default values
        ho = !H.getText().toString().equals("") ?  Long.valueOf(Integer.parseInt(H.getText().toString())) : Long.valueOf(4);
        mi = !M.getText().toString().equals("") ? Long.valueOf(Integer.parseInt(M.getText().toString())) : Long.valueOf(0);
        sex = !S.getText().toString().equals("") ?  Long.valueOf(Integer.parseInt(S.getText().toString())) : Long.valueOf(0);

        Log.i("Adam", String.format("h: %d m: %d s: %d", ho, mi, sex));

        long millis = ho*3600*1000 + mi*60*1000 + sex*1000;

        if(stopped_timer){ millis = Timer.stopped_on_millis; this.stopped_timer = false; }

//        this.timer = new adam.cosher_timer.Timer(millis, 1, timer_out);

        if(this.milliseconds_till_finish == millis){
            this.timer = Timer.getTimer(millis, 1, timer_out);
        } else{
            this.milliseconds_till_finish = millis;
            if(this.timer != null) this.timer.cancel_timer();
            this.timer = new Timer(millis, 1, timer_out);
        }

        this.timer.update_time(millis);
        this.timer.start(); // starts the timer

    }

    /**
     * Stops the timer
     * @param view the main activity's view
     */
    public void stop_timer(View view){
//        final TextView timer_out = this.findViewById(R.id.timer_out);
        this.timer.cancel_timer(); // shuts down the timer
        this.stopped_timer = true;
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
        // build the notification7
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle(MainActivity.TITLE)
                .setContentText(MainActivity.TEXT).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1, builder.build());
    }

}
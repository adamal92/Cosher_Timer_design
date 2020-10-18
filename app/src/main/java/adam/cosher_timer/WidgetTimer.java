package adam.cosher_timer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetTimer extends AppWidgetProvider {
    private Timer timer;
    private boolean stopped_timer;
    private static final String ACTION_NAME = "start_timer";
    public static int WidgetId = 0;

    /**
     * enables the widget buttons
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int[] appWidgetIds) {
        Log.d("Adam", "WidgetTimer.updateAppWidget()");
        WidgetTimer.WidgetId = appWidgetId;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Create an Intent to launch ExampleActivity when clicked
        // Register an onClickListener
        Intent intent_stop = new Intent(context, StopTimerActivity.class);
        Intent intent_start = new Intent(context, StartTimerActivity.class);

        intent_stop.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent_start.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        intent_stop.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        intent_start.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        PendingIntent pendingIntent_stop = PendingIntent.getBroadcast(context,0, intent_stop, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent_start = PendingIntent.getBroadcast(context,0, intent_start, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.stop_clock_widget, pendingIntent_stop);
        views.setOnClickPendingIntent(R.id.start_clock_widget, pendingIntent_start);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Enables the Start button
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     * @param appWidgetIds
     */
    static void update_StartTimer_button(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int[] appWidgetIds) {
        Log.d("Adam", "WidgetTimer.update_StartTimer_button()");
        WidgetTimer.WidgetId = appWidgetId;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        // Set the text of TextView
//        WidgetTimer.a++;
//        views.setTextViewText(R.id.timer_out_widget, String.valueOf(WidgetTimer.a));
        // Create an Intent to launch ExampleActivity when clicked
        // Register an onClickListener
        Intent intent_one = new Intent(context, StartTimerActivity.class);

        intent_one.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent_one.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        PendingIntent pendingIntent_one = PendingIntent.getBroadcast(context,0, intent_one, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.start_clock_widget, pendingIntent_one);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

        /**
         *7
         * @param context
         * @param appWidgetManager
         * @param appWidgetIds
         */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("Adam", "WidgetTimer.onUpdate()");
//        MainActivity.alert(MainActivity.mainContext);

        // Get all ids
        ComponentName thisWidget = new ComponentName(context, WidgetTimer.class);
        int[] all_WidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : all_WidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, appWidgetIds); // enables the widget buttons
//            update_StartTimer_button(context, appWidgetManager, appWidgetId, appWidgetIds); // enables the start button
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("Adam", "WidgetTimer.onReceive()");

        if (ACTION_NAME.equals(intent.getAction())) {
            Log.d("Adam", "MADAFACKA!!!!!!!!!!!!!!!!");
        }
    }
}

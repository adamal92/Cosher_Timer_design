package adam.cosher_timer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Create the widgets of the app
 * @version 2.0
 * @author Adam L
 */
public class WidgetTimer extends AppWidgetProvider {
    private static final String ACTION_NAME = "start_timer";
    public static int WidgetId = 0;
    public static WidgetTimer widgetTimer = null;
    public static Context widget_context = null;
    public static AppWidgetManager widget_appWidgetManager = null;

    /**
     * Enable the widget buttons' action (the 'click')
     * and update the widget
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int[] appWidgetIds) {
        Log.d("Adam", "WidgetTimer.updateAppWidget()");
        WidgetTimer.WidgetId = appWidgetId;
        // Construct the RemoteViews object. views - the widget_layout
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Create an Intent to launch ExampleActivity when clicked
        // Register an onClickListener
        Intent intent_stop = new Intent(context, StopTimerActivity.class);
        Intent intent_start = new Intent(context, StartTimerActivity.class);

        // set an update action
        intent_stop.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent_start.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // register the widgets' id
        intent_stop.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        intent_start.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        // get an update broadcast
        PendingIntent pendingIntent_stop = PendingIntent
                .getBroadcast(context,0, intent_stop, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent_start = PendingIntent
                .getBroadcast(context,0, intent_start, PendingIntent.FLAG_UPDATE_CURRENT);

        // set click listener
        views.setOnClickPendingIntent(R.id.stop_clock_widget, pendingIntent_stop);
        views.setOnClickPendingIntent(R.id.start_clock_widget, pendingIntent_start);

        // instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Initialise every instance of the widget
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("Adam", "WidgetTimer.onUpdate()");

        WidgetTimer.widgetTimer = this; // create an intended memory leak

        // initialize static values
        WidgetTimer.widget_context = context;
        WidgetTimer.widget_appWidgetManager = appWidgetManager;

        // Get all ids
        ComponentName thisWidget = new ComponentName(context, WidgetTimer.class);
        int[] all_WidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : all_WidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, appWidgetIds); // enables the widget buttons
        }
    }
}

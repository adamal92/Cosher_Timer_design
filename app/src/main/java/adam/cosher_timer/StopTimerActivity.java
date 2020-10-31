package adam.cosher_timer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Create the widget stop button action
 * @version 2.0
 * @author Adam L
 */
public class StopTimerActivity extends AppWidgetProvider {
    /**
     * When clicked, stop the timer and update the widget
     * @param context the widgets' context
     * @param appWidgetManager
     * @param appWidgetIds an array of all the ids of the widgets
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("Adam", "StopTimerActivity.onUpdate()");

        MainActivity.mainContext.stop_timer(new View(MainActivity.mainContext)); // stop the timer

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        // Set the text of TextView
        // Instruct the widget manager to update the widget7
        appWidgetManager.updateAppWidget(WidgetTimer.WidgetId, views);
    }
}

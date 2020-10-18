package adam.cosher_timer;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class StopTimerActivity extends AppWidgetProvider {
    /**
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("Adam", "StopTimerActivity.onUpdate()");
        MainActivity.mainContext.stop_timer(new View(MainActivity.mainContext));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        // Set the text of TextView
//        WidgetTimer.a++;
//        views.setTextViewText(R.id.timer_out_widget, String.valueOf(WidgetTimer.a));
        // Instruct the widget manager to update the widget7
        appWidgetManager.updateAppWidget(WidgetTimer.WidgetId, views);
    }
}

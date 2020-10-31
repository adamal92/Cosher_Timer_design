package adam.cosher_timer;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

/**
 * Create the widget start button action
 * @version 2.0
 * @author Adam L
 */
public class StartTimerActivity extends AppWidgetProvider {
    /**
     * When clicked, start the timer and update the widget
     * @param context the widgets' context
     * @param appWidgetManager
     * @param appWidgetIds  an array of all the ids of the widgets
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("Adam", "StartTimerActivity.onUpdate()");

        MainActivity.mainContext.start_timer(new View(MainActivity.mainContext)); // start the timer

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(MainActivity.PACKAGE_NAME, R.layout.widget_layout); // context.getPackageName() = adam.cosher_timer
        // Set the text of TextView
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(WidgetTimer.WidgetId, views);
    }

    /**
     * Implements {@link BroadcastReceiver#onReceive} to dispatch calls to the various
     * other methods on AppWidgetProvider.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("Adam", "StartTimerActivity.onReceive()");

    }

    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_DELETED} broadcast when
     * one or more AppWidget instances have been deleted.  Override this method to implement
     * your own AppWidget functionality.
     * <p>
     * {@more}
     *
     * @param context      The {@link Context Context} in which this receiver is
     *                     running.
     * @param appWidgetIds The appWidgetIds that have been deleted from their host.
     * @see AppWidgetManager#ACTION_APPWIDGET_DELETED
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("Adam", "StartTimerActivity.onDeleted()");

    }

    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_DISABLED} broadcast, which
     * is sent when the last AppWidget instance for this provider is deleted.  Override this method
     * to implement your own AppWidget functionality.
     * <p>
     * {@more}
     *
     * @param context The {@link Context Context} in which this receiver is
     *                running.
     * @see AppWidgetManager#ACTION_APPWIDGET_DISABLED
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d("Adam", "StartTimerActivity.onDisabled()");

    }

    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_ENABLED} broadcast when
     * the a AppWidget for this provider is instantiated.  Override this method to implement your
     * own AppWidget functionality.
     * <p>
     * {@more}
     * When the last AppWidget for this provider is deleted,
     * {@link AppWidgetManager#ACTION_APPWIDGET_DISABLED} is sent by the AppWidget manager, and
     * {@link #onDisabled} is called.  If after that, an AppWidget for this provider is created
     * again, onEnabled() will be called again.
     *
     * @param context The {@link Context Context} in which this receiver is
     *                running.
     * @see AppWidgetManager#ACTION_APPWIDGET_ENABLED
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("Adam", "StartTimerActivity.onEnabled()");

    }

    /**
     * Called in response to the {@link AppWidgetManager#ACTION_APPWIDGET_RESTORED} broadcast
     * when instances of this AppWidget provider have been restored from backup.  If your
     * provider maintains any persistent data about its widget instances, override this method
     * to remap the old AppWidgetIds to the new values and update any other app state that may
     * be relevant.
     *
     * <p>This callback will be followed immediately by a call to {@link #onUpdate} so your
     * provider can immediately generate new RemoteViews suitable for its newly-restored set
     * of instances.
     * <p>
     * {@more}
     *
     * @param context
     * @param oldWidgetIds
     * @param newWidgetIds
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.d("Adam", "StartTimerActivity.onRestored()");

    }
}

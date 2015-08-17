package com.example.safet.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.safet.Application;
import com.example.safet.R;
import com.example.safet.utils.PrefsManager;

public final class TriggerWidgetProvider extends AppWidgetProvider {
    static final String ACTION_TRIGGER = "Safety Trigger";
    private static ComponentName sComponentName;
    private static RemoteViews sWidgetView;

    static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, TriggerWidgetProvider.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_TRIGGER.equals(intent.getAction())) {
            // TODO password for making it inactive.

            int resId = R.drawable.trigger_on;
            if (PrefsManager.isTriggered()) {
                PrefsManager.setIsTriggered(false);
                resId = R.drawable.trigger_off;
            } else
                PrefsManager.setIsTriggered(true);

            // Change widget image, and update current app widget.
            getWidgetView().setImageViewResource(R.id.trigger_image, resId);
            AppWidgetManager.getInstance(context).updateAppWidget(getComponentName(), getWidgetView());
        }
    }

    static ComponentName getComponentName() {
        if (sComponentName == null)
            // Create a component name for this class.
            sComponentName = new ComponentName(Application.getContext(), TriggerWidgetProvider.class);
        return sComponentName;
    }

    static RemoteViews getWidgetView() {
        if (sWidgetView == null)
            // Get the layout for the App Widget and attach an on-click listener to the button
            sWidgetView = new RemoteViews(Application.getContext().getPackageName(), R.layout.widget_trigger);
        return sWidgetView;
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        if(PrefsManager.isTriggered()){
            // TODO ask what to do from user.. with password if active
        }
    }
}
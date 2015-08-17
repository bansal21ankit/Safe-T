package com.example.safet.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.safet.R;
import com.example.safet.utils.Logger;
import com.example.safet.utils.PrefsManager;

public final class TriggerWidgetActivity extends AppCompatActivity {
    private static final String TAG = TriggerWidgetActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
        if (extras != null)
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);

        RemoteViews remoteView = TriggerWidgetProvider.getWidgetView();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName componentName = TriggerWidgetProvider.getComponentName();
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        if (appWidgetIds.length <= 1) {
            // Update widget image based on current trigger state.
            int resId = PrefsManager.isTriggered() ? R.drawable.trigger_on : R.drawable.trigger_off;
            remoteView.setImageViewResource(R.id.trigger_image, resId);

            // Add click action on the image in widget.
            PendingIntent pendingIntent = TriggerWidgetProvider.getPendingSelfIntent(this, TriggerWidgetProvider.ACTION_TRIGGER);
            remoteView.setOnClickPendingIntent(R.id.trigger_image, pendingIntent);

            // Tell AppWidgetManager to perform an update on the current app widget.
            appWidgetManager.updateAppWidget(componentName, remoteView);

            // Adding this widget to home screen.
            Intent resultValue = new Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
        } else {
            // Don't let widgets add to home screen if count is >1.
            AppWidgetHost host = new AppWidgetHost(this, 0);
            for (int i = 1; i < appWidgetIds.length; i++) {
                int widgetId = appWidgetIds[i];
                host.deleteAppWidgetId(widgetId);
                Log.d(TAG, "Deleting widget listener for: " + widgetId);
                appWidgetManager.updateAppWidget(componentName, remoteView);
            }

            // Warn user that widget cannot be added.
            Logger.toast("Already have widget.", Toast.LENGTH_SHORT);
        }
        finish();
    }
}
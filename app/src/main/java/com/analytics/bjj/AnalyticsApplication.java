package com.analytics.bjj;

import android.app.Application;
import android.util.Log;


import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */
public class AnalyticsApplication extends Application {
    private Tracker mTracker;

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
//        if (mTracker == null) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            mTracker = analytics.newTracker(R.xml.global_tracker);
//        }
//        return mTracker;

        try {
            final GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(this);
            return googleAnalytics.newTracker(R.xml.analytics);

        }catch(final Exception e){
            Log.e("GA Track", "Failed to initialize Google Analytics V4");
        }
        return null;
    }
}
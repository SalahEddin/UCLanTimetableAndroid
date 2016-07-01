package uclancyprusguide.inspirecenter.org.uclantimetable.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import uclancyprusguide.inspirecenter.org.uclantimetable.BuildConfig;
import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.UclanCyApplication;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.SharingTools;

public class ActivityAbout extends AppCompatActivity {

    /**
     * The {@link Tracker} used to record screen views.
     */
    private static Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Obtain the shared Tracker instance.
        final UclanCyApplication application = (UclanCyApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("ActivityAbout")
                .setAction("Created")
                .build());

        final ActionBar actionBar = getActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content.
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new AboutFragment())
                .commit();
    }

    public static class AboutFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.about);

            {
                final Preference applicationVersionPreference = findPreference("applicationVersion");
                applicationVersionPreference.setSummary(BuildConfig.VERSION_NAME);
            }
            {
                final Preference rateUsPreference = findPreference("share");
                rateUsPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        SharingTools.shareApp(getActivity());
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("ActivityAbout")
                                .setAction("Menu:Share")
                                .build());
                        return false;
                    }
                });
            }
        }
    }
}

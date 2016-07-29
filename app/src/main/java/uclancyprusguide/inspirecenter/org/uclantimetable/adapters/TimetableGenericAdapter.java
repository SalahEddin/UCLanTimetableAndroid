package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;

/**
 * Created by salah on 14/07/16.
 */
public abstract class TimetableGenericAdapter extends ArrayAdapter<TimetableSession> {
    public static final String TAG = "uclan-cy";
    private LayoutInflater layoutInflater;

    public TimetableGenericAdapter(Context context, int resource, final ArrayList<TimetableSession> dataset) {
        super(context, resource, dataset);
        this.layoutInflater = LayoutInflater.from(context);
    }
}

package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Badge;

/**
 * Created by salah on 19/07/16.
 */
public class BadgeGridAdapter extends BaseAdapter {
    private Context mContext;
    protected ArrayList<Badge> mBadgeDataset;

    // Constructor
    public BadgeGridAdapter(Context c, ArrayList<Badge> mBaseDataset) {
        mContext = c;
        mBadgeDataset = mBaseDataset;
    }

    public int getCount() {
        return mBadgeDataset.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;

        if (vi == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.badge_grid_item, null);
        }
        ImageView badgeSvg = (ImageView) vi.findViewById(R.id.badge_image);
        TextView badgeName = (TextView) vi.findViewById(R.id.badge_name);
        //badgeSvg.
        //badgeSvg.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
        return vi;
    }


}

package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.BitSet;

import okhttp3.internal.Util;
import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Badge;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;

/**
 * Created by salah on 19/07/16.
 */
public class BadgeGridAdapter extends BaseAdapter {
    private Context mContext;
    private ImageLoader mImageLoader;
    protected ArrayList<Badge> mBadgeDataset;

    // Constructor
    public BadgeGridAdapter(Context c, ArrayList<Badge> mBaseDataset, ImageLoader imageLoader) {
        mContext = c;
        mBadgeDataset = mBaseDataset;
        mImageLoader = imageLoader;
    }

    public int getCount() {
        return mBadgeDataset.size();
    }

    @Override
    public Object getItem(int i) {
        return mBadgeDataset.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Integer.parseInt(mBadgeDataset.get(i).getbADGEID());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;

        if (vi == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.badge_grid_item, null);
        }

        final Badge badge = (Badge) getItem(i);
        final ImageView badgeSvg = (ImageView) vi.findViewById(R.id.badge_image);

        // Load image, decode it to Bitmap and return Bitmap to callback
        mImageLoader.loadImage(badge.getbADGEURL(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
                if (loadedImage != null) {
                    badgeSvg.setImageBitmap(loadedImage);
                }
            }
        });

        TextView badgeName = (TextView) vi.findViewById(R.id.badge_name);
        String name = badge.getbADGENAME();
        if (name.length() > 13) {
            name = String.format("%s...", name.substring(0, 10));
        }
        badgeName.setText(name);
        return vi;
    }
}

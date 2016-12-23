package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.BadgeGridAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.BadgesInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Badge;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;

public class FragmentAttendance extends Fragment implements BadgesInterface {

    private TextView pointsTextView;
    private GridView badgesGridView;
    private BadgeGridAdapter mBadgeGridAdapter;
    private ArrayList<Badge> mBadges;
    private ImageLoader imageLoader;

    public FragmentAttendance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vi = inflater.inflate(R.layout.fragment_attendance, container, false);

        imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity().getBaseContext()));

        pointsTextView = (TextView) vi.findViewById(R.id.totalBadgesText);
        mBadges = new ArrayList<>();
        mBadgeGridAdapter = new BadgeGridAdapter(vi.getContext(), mBadges, imageLoader);
        badgesGridView = (GridView) vi.findViewById(R.id.badge_gridView);
        badgesGridView.setAdapter(mBadgeGridAdapter);
        String account_id = Misc.loadUser(getActivity()).getACCOUNT_ID();

        TimetableData.LoadBadges(account_id, getActivity().getBaseContext(), FragmentAttendance.this);

        // on item click // open a pop up with more details about the badge
        badgesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // get clicked badge details
                Badge b = mBadges.get(i);
                if (b != null) {
                    LocalDateTime bCreationDate = Misc.APITimestampToLocalDate(b.getcREATEDATE());
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, yyyy");
                    String formattedDate = dtf.format(bCreationDate);

                    AlertDialog alertDialog = new AlertDialog.Builder(vi.getContext()).create();
                    alertDialog.setTitle(b.getbADGECALCULATION() + ": " + b.getbADGENAME());
                    alertDialog.setMessage(b.getbADGEDESCRIPTION() + "\n" + "Awarded: " + formattedDate);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok, Cool",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(vi.getContext()).create();
                    alertDialog.setTitle("Sorry");
                    alertDialog.setMessage("This badge cannot be show at the moment");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        return vi;
    }

    @Override
    public void onDownloaded(List<Badge> badges) {
        mBadges.clear();
        mBadges.addAll(badges);
        mBadgeGridAdapter.notifyDataSetChanged();
        // update the badge counter
        int size = badges.size();
        if (size > 0) {
            pointsTextView.setText(Integer.toString(size) + " Badges");
        } else {
            pointsTextView.setText("-");
        }
    }
}

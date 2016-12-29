package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.BadgeGridAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.ModuleAttendanceAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.AttendanceCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.BadgesInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Attendance;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.Badge;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.TimetableData;

public class FragmentAttendance extends Fragment implements AttendanceCallbackInterface, BadgesInterface {

    private TextView pointsTextView;
    private TextView attendanceTextView;
    private GridView badgesGridView;
    private BadgeGridAdapter mBadgeGridAdapter;
    private ArrayList<Badge> mBadges;
    private ImageLoader imageLoader;
    private ArcProgress progress;
    private Context mContext;
    private Button detailsButton;

    public FragmentAttendance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vi = inflater.inflate(R.layout.fragment_attendance, container, false);
        mContext = getActivity().getBaseContext();
        imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        detailsButton = (Button) vi.findViewById(R.id.detailsButton);
        attendanceTextView = (TextView) vi.findViewById(R.id.attTextView);
        pointsTextView = (TextView) vi.findViewById(R.id.totalBadgesText);
        mBadges = new ArrayList<>();
        mBadgeGridAdapter = new BadgeGridAdapter(vi.getContext(), mBadges, imageLoader);
        badgesGridView = (GridView) vi.findViewById(R.id.badge_gridView);
        badgesGridView.setAdapter(mBadgeGridAdapter);
        progress = (ArcProgress) vi.findViewById(R.id.arc_progress);
        LinearLayout summaryLinearLayout = (LinearLayout) vi.findViewById(R.id.SummaryLinearLayout);
        final TextView nameTxt = (TextView) vi.findViewById(R.id.usernameTextView);
        final TextView emailTxt = (TextView) vi.findViewById(R.id.emailTextView);

        final String account_id = Misc.loadUser(getActivity()).getACCOUNT_ID();
        final String username = Misc.loadUser(getActivity()).getFULLNAME();
        final String email = Misc.loadUser(getActivity()).getEMAIL();
        nameTxt.setText(username);
        emailTxt.setText(email);
        // Load Data Async
        TimetableData.LoadBadges(account_id, mContext, FragmentAttendance.this);
        TimetableData.GetAvgAttendance(account_id, mContext, FragmentAttendance.this);
        // on item click // open a pop up with more details about the badge
        badgesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // get clicked badge details
                Badge b = mBadges.get(i);
                if (b != null) {
                    LocalDateTime bCreationDate = Misc.APITimestampToLocalDate(b.getCREATEDATE());
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM, yyyy");
                    String formattedDate = dtf.format(bCreationDate);

                    AlertDialog alertDialog = new AlertDialog.Builder(vi.getContext()).create();
                    alertDialog.setTitle(b.getBADGECALCULATION() + ": " + b.getBADGENAME());
                    alertDialog.setMessage(b.getBADGEDESCRIPTION() + "\n" + "Awarded: " + formattedDate);
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

        // if progress is clicked, show custom detailed modules dialog
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimetableData.GetDetailedAttendance(account_id, getActivity().getBaseContext(), FragmentAttendance.this);
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
            pointsTextView.setText(String.format("%s Badges", Integer.toString(size)));
        } else {
            pointsTextView.setText("-");
        }
    }

    @Override
    public void onAvgDownloaded(Attendance attendance) {
        int val = attendance.getATTENDANCEAVERAGE().intValue();
        progress.setProgress(val);
        int col;

        if (val < 60) {
            attendanceTextView.setText("Poor\nAttendance");
            col = FragmentAttendance.this.getActivity().getResources().getColor(R.color.att_poor);

        } else if (val < 80) {
            attendanceTextView.setText("Good\nAttendance");
            col = FragmentAttendance.this.getActivity().getResources().getColor(R.color.att_good);
        } else {
            attendanceTextView.setText("Excellent\nAttendance");
            col = FragmentAttendance.this.getActivity().getResources().getColor(R.color.att_excellent);
        }
        progress.setFinishedStrokeColor(col);
        progress.setTextColor(col);
        attendanceTextView.setTextColor(col);
    }

    @Override
    public void onDetailedDownloaded(List<Attendance> attendance) {
        Log.d("yes", "received");
        // create view with all detailed modules
        // custom dialog
        final Dialog dialog = new Dialog(FragmentAttendance.this.getActivity());
        dialog.setContentView(R.layout.detailed_attendance_dialog);

        // set the custom dialog components - text, image and button
        ListView attendanceListView = (ListView) dialog.findViewById(R.id.listView);
        Button dialogButton = (Button) dialog.findViewById(R.id.dismissButton);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // set listview adapter and content
        final ArrayList<Attendance> list = new ArrayList<>(attendance.size());
        list.addAll(attendance);
        final ModuleAttendanceAdapter eventArrAdapter = new ModuleAttendanceAdapter(mContext, list);
        attendanceListView.setAdapter(eventArrAdapter);
        dialog.show();
    }
}
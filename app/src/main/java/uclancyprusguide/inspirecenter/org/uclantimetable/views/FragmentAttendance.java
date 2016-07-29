package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.BadgeCard;


public class FragmentAttendance extends Fragment {

    boolean cardExpandedY4 = false, cardExpandedY3 = false, cardExpandedY2 = false, cardExpandedY1 = false;
    Button showMoreYear4Button;
    LinearLayout year4MoreLayout;
    TextView year4PointsTextView;
    GridView year4BadgesGridView;

    public FragmentAttendance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.fragment_attendance, container, false);

        showMoreYear4Button = (Button) vi.findViewById(R.id.show_more_year_4_button);
        year4MoreLayout = (LinearLayout) vi.findViewById(R.id.year_4_more_layout);
        year4PointsTextView = (TextView) vi.findViewById(R.id.year_4_points_textView);
        year4BadgesGridView = (GridView) vi.findViewById(R.id.year_4_badge_gridView);

        BadgeCard year4 = new BadgeCard(vi, year4MoreLayout, year4BadgesGridView, showMoreYear4Button, false, year4PointsTextView);


        return vi;
    }
}

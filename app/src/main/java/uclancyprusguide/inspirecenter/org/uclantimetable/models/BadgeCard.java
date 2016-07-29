package uclancyprusguide.inspirecenter.org.uclantimetable.models;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.views.BadgeGridAdapter;

/**
 * Created by salah on 19/07/16.
 */
public class BadgeCard {
    LinearLayout moreLayout;
    GridView badgeGridView;
    Button showMoreButton;
    TextView pointsTextView;
    boolean isExpanded;

    public BadgeCard(final View vi, final LinearLayout moreLayout, final GridView badgeGridView, final Button showMoreButton, final boolean initIsExpanded, final TextView initPointsTextView) {
        this.moreLayout = moreLayout;
        this.showMoreButton = showMoreButton;
        this.isExpanded = initIsExpanded;
        this.pointsTextView = initPointsTextView;
        this.badgeGridView = badgeGridView;

        showMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreLayout.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
                showMoreButton.setText(isExpanded ? R.string.show_more_button_text : R.string.show_less_button_text);
                // update toggle status
                isExpanded = !isExpanded;
            }
        });

        loadBadges();
    }

    private void loadBadges() {
        final ArrayList<Badge> badgeList = new ArrayList<>();
        badgeList.add(new Badge());
        badgeList.add(new Badge());
        badgeList.add(new Badge());
        badgeList.add(new Badge());
        badgeList.add(new Badge());
        badgeList.add(new Badge());
        badgeList.add(new Badge());

        final BadgeGridAdapter adapter = new BadgeGridAdapter(badgeGridView.getContext(), badgeList);
        badgeGridView.setAdapter(adapter);
    }
}

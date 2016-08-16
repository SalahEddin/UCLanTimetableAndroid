package uclancyprusguide.inspirecenter.org.uclantimetable.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.TextStyle;

import java.util.ArrayList;
import java.util.Locale;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.RSS.NewsItem;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.TimetableSession;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.Misc;

/**
 * @author Salah Eddin Alshaal
 *         22/11/2015.
 */
public class NewsAdapter extends ArrayAdapter<NewsItem> {

    public static final String TAG = "uclan-cy";

    private LayoutInflater layoutInflater;
    private Context mContext;

    // Added second constructor to support ArrayLists
    public NewsAdapter(final Context context, final ArrayList<NewsItem> newsItems) {
        super(context, 0, newsItems);
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.news_list_item, null);
        }

        final NewsItem item = getItem(position);

        // binding
        final TextView title = (TextView) view.findViewById(R.id.news_title);
        final TextView date = (TextView) view.findViewById(R.id.news_date);
        final TextView time = (TextView) view.findViewById(R.id.news_time);
        final TextView desc = (TextView) view.findViewById(R.id.news_desc);


        title.setText(item.getMtitle());
        // LocalDateTime dateTime1 = DateTimeFormatter.RFC_1123_DATE_TIME.parse();
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'BST'");//.RFC_1123_DATE_TIME; // .ofPattern("EEE, dd MMM yyyy HH:mm:ss z");
        LocalDateTime dateTime = LocalDateTime.parse(item.getMpubDate().trim(), dtf);
        date.setText(dateTime.format(DateTimeFormatter.ofPattern("E")));
        time.setText(dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyy")));

        if (item.getMdescription() != null) {
            desc.setText(item.getMdescription());
        } else {
            desc.setText("click for details");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getMlink() == null) return;

                String url = item.getMlink().trim();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getContext().startActivity(i);
            }
        });
        return view;
    }
}
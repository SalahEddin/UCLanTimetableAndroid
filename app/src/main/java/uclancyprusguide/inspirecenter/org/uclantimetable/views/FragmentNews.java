package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.NewsAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.adapters.TimetableExamAdapter;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.NewsCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.RSS.NewsItem;
import uclancyprusguide.inspirecenter.org.uclantimetable.util.NewsRSS;

/**
 * @author Nearchos
 *         Created: 03-Jun-16
 */
public class FragmentNews extends Fragment implements NewsCallbackInterface {

    private ArrayList<NewsItem> newsItems;
    private NewsAdapter adapter;
    private SwipeRefreshLayout pullToRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, null);

        // dummy array
        newsItems = new ArrayList<>();
        adapter = new NewsAdapter(getActivity(), newsItems);

        // bind the listView
        ListView newsListView = (ListView) view.findViewById(R.id.news_listView);
        newsListView.setAdapter(adapter);

        reloadNews();
        return view;
    }

    private void reloadNews() {
        NewsRSS.LoadNews(this);
    }

    @Override
    public void onDownloaded(List<NewsItem> newsItems) {
        this.newsItems.clear();
        this.newsItems.addAll(newsItems);
        adapter.notifyDataSetChanged();
    }
}

package uclancyprusguide.inspirecenter.org.uclantimetable.interfaces;

import java.util.List;

import uclancyprusguide.inspirecenter.org.uclantimetable.models.RSS.NewsItem;

/**
 * Created by salah on 16/08/16.
 */
public interface NewsCallbackInterface {
    void onDownloaded(List<NewsItem> newsItems);
}

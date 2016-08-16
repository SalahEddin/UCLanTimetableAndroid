package uclancyprusguide.inspirecenter.org.uclantimetable.models.RSS;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by salah on 16/08/16.
 */
@Root(name = "channel", strict = false)
public class RSSChannel implements Serializable {

    @ElementList(inline = true, name = "item")
    private List<NewsItem> mFeedItems;

    public List<NewsItem> getFeedItems() {
        return mFeedItems;
    }

    public RSSChannel() {
    }

    public RSSChannel(List<NewsItem> mFeedItems) {
        this.mFeedItems = mFeedItems;
    }

}

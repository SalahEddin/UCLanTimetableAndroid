package uclancyprusguide.inspirecenter.org.uclantimetable.models.RSS;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by salah on 16/08/16.
 */
@Root(name = "rss", strict = false)
public class NewsRSSResponse implements Serializable {
    @Element(name = "channel")
    private RSSChannel mChannel;

    public RSSChannel getmChannel() {
        return mChannel;
    }

    public NewsRSSResponse() {
    }

    public NewsRSSResponse(RSSChannel mChannel) {
        this.mChannel = mChannel;
    }
}

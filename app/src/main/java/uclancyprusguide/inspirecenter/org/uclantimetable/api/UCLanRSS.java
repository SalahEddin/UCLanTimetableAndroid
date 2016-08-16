package uclancyprusguide.inspirecenter.org.uclantimetable.api;

import retrofit2.Call;
import retrofit2.http.GET;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.RSS.NewsRSSResponse;

/**
 * Created by salah on 01/08/16.
 */
public interface UCLanRSS {
    String ENDPOINT = "http://www.uclancyprus.ac.cy/tools/blocks/page_list/";

    @GET("rss?bID=975&cID=146&arHandle=newsblog")
    Call<NewsRSSResponse> listNews();

}

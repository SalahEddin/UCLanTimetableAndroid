package uclancyprusguide.inspirecenter.org.uclantimetable.util;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import uclancyprusguide.inspirecenter.org.uclantimetable.api.UCLanRSS;
import uclancyprusguide.inspirecenter.org.uclantimetable.interfaces.NewsCallbackInterface;
import uclancyprusguide.inspirecenter.org.uclantimetable.models.RSS.NewsRSSResponse;

/**
 * Created by salah on 16/08/16.
 * load news from XML RSS news feed
 */
public class NewsRSS {
    public static void LoadNews(final NewsCallbackInterface callbackInterface) {
        // XML converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UCLanRSS.ENDPOINT)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        UCLanRSS apiService = retrofit.create(UCLanRSS.class);
        Call<NewsRSSResponse> getCall = apiService.listNews();

        getCall.enqueue(new Callback<NewsRSSResponse>() {
            @Override
            public void onResponse(Call<NewsRSSResponse> call, Response<NewsRSSResponse> response) {
                int code = response.code();
                if (code == 200) {

                    NewsRSSResponse rssResponse = response.body();
                    rssResponse.getmChannel().getFeedItems();

                    callbackInterface.onDownloaded(rssResponse.getmChannel().getFeedItems());
                } else {
                    Log.d("response", "not 200");
                }
            }

            @Override
            public void onFailure(Call<NewsRSSResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}

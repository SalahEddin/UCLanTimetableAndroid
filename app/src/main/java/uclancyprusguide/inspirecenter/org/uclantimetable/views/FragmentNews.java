package uclancyprusguide.inspirecenter.org.uclantimetable.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import uclancyprusguide.inspirecenter.org.uclantimetable.R;

/**
 * @author Nearchos
 *         Created: 03-Jun-16
 */
public class FragmentNews extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, null);
        final WebView webView = (WebView) view.findViewById(R.id.fragment_news_web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://www.uclancyprus.ac.cy/en/news/latest-news");
        return view;
    }
}

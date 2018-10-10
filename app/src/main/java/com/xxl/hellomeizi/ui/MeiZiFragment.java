package com.xxl.hellomeizi.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxl.hellomeizi.R;
import com.xxl.hellomeizi.adapter.MeiZiAdapter;
import com.xxl.netcore.bean.PageBean;
import com.xxl.netcore.jsoup.JsoupFactory;
import com.xxl.netcore.jsoup.PageSoup;
import com.xxl.netcore.net.Callback;
import com.xxl.netcore.net.HttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public class MeiZiFragment extends Fragment {
    private static final String TITLE = "title";
    private static final String URL = "url";

    private String mTitle;
    private String mUrl;

    public static MeiZiFragment newInstance(String title, String url) {
        MeiZiFragment meiZiFragment = new MeiZiFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(URL, url);
        meiZiFragment.setArguments(bundle);
        return meiZiFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mTitle = arguments.getString(TITLE);
        mUrl = arguments.getString(URL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_meizi, null);
        TextView tv = view.findViewById(R.id.fragment_meizi_tv);
        final RecyclerView rv = view.findViewById(R.id.fragment_meizi_rv);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //manager.setAutoMeasureEnabled(true);
//        rv.setLayoutManager(staggeredGridLayoutManager);
        tv.setText(mTitle);
        HttpClient httpClient = new HttpClient();
        httpClient.get(mUrl, new Callback() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

                Map<String, Object> values = JsoupFactory.parseHtml(PageSoup.class, response);
                final List<PageBean> pageBeans = (List<PageBean>) values.get(PageSoup.class.getSimpleName());
                if (pageBeans != null && pageBeans.size() > 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MeiZiAdapter meiZiAdapter = new MeiZiAdapter(pageBeans);
                            rv.setAdapter(meiZiAdapter);
                        }
                    });

                }
            }
        });
        return view;
    }

}

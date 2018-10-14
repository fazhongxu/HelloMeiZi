package com.xxl.hellomeizi.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
public class MeiZiFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TITLE = "title";
    private static final String URL = "url";

    private String mTitle;
    private String mUrl;
    private MeiZiAdapter mMeiZiAdapter;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tv = view.findViewById(R.id.fragment_meizi_tv);
        final RecyclerView rv = view.findViewById(R.id.fragment_meizi_rv);
        tv.setText(mTitle);
        HttpClient httpClient = new HttpClient();
        httpClient.get(mUrl, new Callback() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

                Map<String, Object> values = JsoupFactory.parseHtml(PageSoup.class, response);
                if (values == null) return;
                final PageBean pageBean = (PageBean) values.get(PageSoup.class.getSimpleName());
                if (pageBean != null && pageBean.getModelList() != null && pageBean.getModelList().size() >0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<PageBean.PageModel> modelList = pageBean.getModelList();
                            mMeiZiAdapter = new MeiZiAdapter(modelList,pageBean.getNextPageUrl());
                            mMeiZiAdapter.setOnLoadMoreListener(MeiZiFragment.this,rv);
                            rv.setAdapter(mMeiZiAdapter);
                        }
                    });

                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_meizi, null);
    }

    @Override
    public void onLoadMoreRequested() {
        HttpClient httpClient = new HttpClient();
        String nextUrl = mMeiZiAdapter.getNextUrl();
        if (TextUtils.isEmpty(nextUrl)) {
            mMeiZiAdapter.loadMoreEnd();
            mMeiZiAdapter.notifyDataSetChanged();
            return;
        }
        httpClient.get(nextUrl, new Callback() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String response) throws IOException {

                Map<String, Object> values = JsoupFactory.parseHtml(PageSoup.class, response);
                final PageBean pageBean = (PageBean) values.get(PageSoup.class.getSimpleName());
                if (pageBean != null && pageBean.getModelList() != null && pageBean.getModelList().size() >0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMeiZiAdapter.setNextUrl(pageBean.getNextPageUrl());
                            mMeiZiAdapter.addData(pageBean.getModelList());
                            mMeiZiAdapter.loadMoreComplete();
                        }
                    });

                }
            }
        });
    }
}

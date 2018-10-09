package com.xxl.hellomeizi.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xxl.hellomeizi.R;

/**
 * Created by xxl on 2018/10/9.
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
        tv.setText(mTitle);
        return view;
    }


}

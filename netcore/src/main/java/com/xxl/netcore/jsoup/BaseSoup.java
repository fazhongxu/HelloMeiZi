package com.xxl.netcore.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public abstract class BaseSoup implements ISoup {
    private String mHtml;
    private Map<String,Object> mValues;

    public BaseSoup(String html) {
        this.mHtml = html;
    }

    @Override
    public Map<String, Object> doParse() {
        Document document = Jsoup.parse(mHtml);
        if (mValues == null) {
            mValues = new HashMap<>();
        }
        parse(document,document.head(),document.body(),mValues);
        return mValues;
    }
}

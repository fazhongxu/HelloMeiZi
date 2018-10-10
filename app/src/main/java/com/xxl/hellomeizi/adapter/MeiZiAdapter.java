package com.xxl.hellomeizi.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxl.hellomeizi.R;
import com.xxl.hellomeizi.ui.MeiziDetailActivity;
import com.xxl.netcore.bean.PageBean;

import java.util.List;

/**
 * Created by xxl on 2018/10/10.
 *
 * Description :
 */

public class MeiZiAdapter extends BaseQuickAdapter<PageBean.PageModel,BaseViewHolder> {
    private String nextUrl;
    public MeiZiAdapter( @Nullable List<PageBean.PageModel> data,String nextUrl) {
        super(R.layout.ui_meizi_layout,data);
        this.nextUrl = nextUrl;
    }


    @Override
    protected void convert(BaseViewHolder helper, PageBean.PageModel item) {
        ImageView avatarIv = helper.getView(R.id.ui_meizi_avatar_iv);
        String url = item.getImgUrl();

        String host = "";
        if (url.startsWith("https://")) {
            host = url.replace("https://", "");
        } else if (url.startsWith("http://")) {
            host = url.replace("http://", "");
        }
        host = host.substring(0, host.indexOf("/"));
        LazyHeaders.Builder builder = new LazyHeaders.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E)  Chrome/60.0.3112.90 Mobile Safari/537.36")
                .addHeader("Accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                .addHeader("Host", host)
                .addHeader("Proxy-Connection", "keep-alive")
                .addHeader("Referer", "http://m.mzitu.com/");

        final GlideUrl glideUrl = new GlideUrl(url, builder.build());

        Glide.with(mContext)
                .load(glideUrl)
                .into(avatarIv);
        helper.setText(R.id.ui_meizi_des,item.getTitle());

        avatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,MeiziDetailActivity.class);
                intent.putExtra("url",glideUrl.toStringUrl());
                mContext.startActivity(intent);
            }
        });
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }
}

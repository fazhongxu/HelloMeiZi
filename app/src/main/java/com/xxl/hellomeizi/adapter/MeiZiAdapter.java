package com.xxl.hellomeizi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xxl.hellomeizi.R;
import com.xxl.hellomeizi.ui.MeiziDetailActivity;
import com.xxl.netcore.bean.PageBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by xxl on 2018/10/10.
 *
 * Description :
 */

public class MeiZiAdapter extends BaseQuickAdapter<PageBean.PageModel,BaseViewHolder> {
    private String nextUrl;
    private File picFile;

    public MeiZiAdapter( @Nullable List<PageBean.PageModel> data,String nextUrl) {
        super(R.layout.ui_meizi_layout,data);
        this.nextUrl = nextUrl;
    }


    @Override
    protected void convert(BaseViewHolder helper, PageBean.PageModel item) {
        final ImageView avatarIv = helper.getView(R.id.ui_meizi_avatar_iv);
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
                .asBitmap()

                .load(glideUrl)

                .into(avatarIv);

        avatarIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File file = Glide.with(mContext)
                                    .asFile()
                                    .load(glideUrl).submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();

                            copy(file,System.currentTimeMillis()+".jpg");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                return true;
            }
        });



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

    public void copy(File file,String fileName) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        File picFile = null;

        try {
             fis = new FileInputStream(file);
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "HelloC";
            File fileDir = new File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
             picFile = new File(fileDir, fileName);
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
             fos = new FileOutputStream(picFile);
            byte[] buffer = new byte[8 * 1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer,0,len);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
                final File finalPicFile = picFile;
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext,  finalPicFile == null ? "": "图片下载至"+finalPicFile.getPath(), Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }
}

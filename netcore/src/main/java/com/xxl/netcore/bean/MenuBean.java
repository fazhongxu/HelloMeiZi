package com.xxl.netcore.bean;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public class MenuBean {
    private String title;
    private String url;

    public MenuBean(String title,String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

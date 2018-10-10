package com.xxl.netcore.bean;

import java.util.List;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public class PageBean {
    private String nextPageUrl;

    private List<PageModel> modelList;
    public PageBean(){

    }

    public List<PageModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<PageModel> modelList) {
        this.modelList = modelList;
    }



    public String getNextPageUrl() {
        return nextPageUrl;
    }
    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public static class PageModel {
        private String title;
        private String imgUrl;

        public PageModel(String title,String imgUrl) {
            this.title = title;
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}

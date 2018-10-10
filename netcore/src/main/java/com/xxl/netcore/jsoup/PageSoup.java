package com.xxl.netcore.jsoup;

import android.util.Log;

import com.xxl.netcore.bean.PageBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public class PageSoup extends MenuSoup {
    private final String KEY = PageSoup.class.getSimpleName();
    public PageSoup(String html) {
        super(html);
    }

    @Override
    public void parse(Document root, Element head, Element body, Map<String, Object> values) {
        super.parse(root, head, body, values);

        Element content = body.getElementById("content");
        Elements figures = content.getElementsByTag("figure");
        List<PageBean.PageModel> pageBeans = new ArrayList<>();
        PageBean pageBean = new PageBean();
        if (figures != null && figures.size() > 0) {
            for (Element figure : figures) {
                Elements a = figure.getElementsByTag("a");
                String title = a.attr("title");
                List<Node> nodes = figure.childNodes();
                Node item = nodes.get(1);
                String imgUrl = item.childNode(0).attr("data-original");
                PageBean.PageModel pageModel = new PageBean.PageModel(title, imgUrl);
                pageBeans.add(pageModel);
            }

            Element pagebtn = content.getElementById("pagebtn");
            if (pagebtn != null) {
                Elements elements = pagebtn.getElementsByTag("a");
                for (Element element : elements) {
                    String text = element.text();
                    if (text.contains("下一页")) {
                        String nextUrl = element.attr("href");
                        pageBean.setNextPageUrl(nextUrl);
                    }
                }
            }
            pageBean.setModelList(pageBeans);
            values.put(KEY,pageBean);
            return;
        }

        Elements children = content.getElementsByTag("p");
        //自拍
        if (children != null && children.size() > 0) {
            for (Element localChild : children) {
                Element element = localChild.getElementsByTag("img").get(0);
                String url = element.attr("src");
                String des = element.attr("alt");
                PageBean.PageModel pageModel = new PageBean.PageModel(des, url);
                pageBeans.add(pageModel);
            }
            pageBean.setModelList(pageBeans);

            Elements childs = content.getElementsByClass("prev-next");
            if (childs != null && childs.size() > 0) {
                childs = childs.get(0).getElementsByTag("a");
                for (int i = 0; i < childs.size(); i++) {
                    Element ele = childs.get(i);
                    if (ele.text().contains("下一页")) {
                        String nextUrl = ele.attr("href");
                        pageBean.setNextPageUrl(nextUrl);
                        break;
                    }
                }
            }
            values.put(KEY, pageBean);
        }
    }
}

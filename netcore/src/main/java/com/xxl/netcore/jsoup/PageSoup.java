package com.xxl.netcore.jsoup;

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
        List<PageBean> pageBeans = new ArrayList<>();
        if (figures != null && figures.size() > 0) {
            for (Element figure : figures) {

                Elements a = figure.getElementsByTag("a");
                String title = a.attr("title");
                //String imgUrl = a.attr("data-original");
                List<Node> nodes = figure.childNodes();
                Node item = nodes.get(1);
                String imgUrl = item.childNode(0).attr("data-original");
                PageBean pageBean = new PageBean(title, imgUrl);
                pageBeans.add(pageBean);
            }
            values.put(KEY,pageBeans);
            return;
        }

        Elements children = content.getElementsByTag("p");
        //自拍
        if (children != null && children.size() > 0) {
            for (Element localChild : children) {
                Element element = localChild.getElementsByTag("img").get(0);
                String url = element.attr("src");
                String des = element.attr("alt");
                PageBean pageBean = new PageBean(des, url);
                pageBeans.add(pageBean);
                //PageModel.ItemModel model = new PageModel.ItemModel(url.substring(url.lastIndexOf("/") + 1), des, url);
                //value.add(model);
            }

            /*Elements childs = content.getElementsByClass("prev-next");
            if (childs != null && childs.size() > 0) {
                childs = childs.get(0).getElementsByTag("a");
                for (int i = 0; i < childs.size(); i++) {
                    Element ele = childs.get(i);
                    if (ele.text().contains("下一页")) {
                        String nextUrl = ele.attr("href");
                        pageModel.setNextPage(nextUrl);
                        break;
                    }
                }
            }*/
            //values.put(getClass().getSimpleName(), pageModel);
            values.put(KEY, pageBeans);
        }
    }
}

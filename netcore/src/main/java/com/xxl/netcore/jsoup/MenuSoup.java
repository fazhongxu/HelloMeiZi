package com.xxl.netcore.jsoup;

import android.text.TextUtils;

import com.xxl.netcore.bean.MenuBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public class MenuSoup extends BaseSoup {
    private static final String KEY = "MenuSoup";
    public MenuSoup(String url) {
        super(url);
    }

    @Override
    public void parse(Document root, Element head, Element body, Map<String, Object> values) {
        Element elementMenu = body.getElementById("menu-ease-mobile");
        Elements elementsArray = elementMenu.getElementsByTag("a");
        List<MenuBean> menuBeans = new ArrayList<>();
        for (Element element : elementsArray) {
            String title = element.attr("title");
            String href = element.attr("href");
            if (!"首页".equalsIgnoreCase(title)) {  // 忽略首页
                MenuBean menuBean = new MenuBean(title, href);
                menuBeans.add(menuBean);
            }
        }
        // 移除 每日更新 美女专题
        menuBeans.remove(menuBeans.size() -1);
        menuBeans.remove(menuBeans.size() -1);
        MenuBean temp = null;

        Iterator<MenuBean> iterator = menuBeans.iterator();
        while (iterator.hasNext()) {
            MenuBean next = iterator.next();
            if (next.getTitle().contains("自拍")) {
                temp = next;
                iterator.remove();
            }
        }
        if (temp != null) {
            menuBeans.add(0,temp);
        }
        values.put(KEY,menuBeans);
    }
}

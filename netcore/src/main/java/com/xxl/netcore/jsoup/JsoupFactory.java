package com.xxl.netcore.jsoup;

import java.util.Map;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public class JsoupFactory {
    public static Map<String,Object> parseHtml(Class<? extends BaseSoup> clazz,String html) {
        try {
            BaseSoup baseSoup = clazz.getConstructor(String.class).newInstance(html);
            return baseSoup.doParse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

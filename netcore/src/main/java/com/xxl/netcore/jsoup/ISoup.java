package com.xxl.netcore.jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;

/**
 * Created by xxl on 2018/10/9.
 *
 * Description :
 */

public interface ISoup {
    void parse(Document root, Element head, Element body, Map<String,Object> values);

    Map<String,Object> doParse();
}

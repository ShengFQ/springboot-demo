package com.elasticsearch.spring.demo.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: JsoupUtils
 * Description: 爬虫爬取类
 *https://search.jd.com/Search?keyword=java
 * @author shengfq
 * @date: 2022/8/30 2:55 下午
 */
public class JsoupUtils {
    /**
     * 根据给定的关键字,爬取对应的商品信息列表
     * */
    public static List<WebContent> parse(String keyword) throws Exception{
        String url ="https://search.jd.com/Search?keyword="+keyword;
        Document document=Jsoup.parse(new URL(url),2000);
        Element element=document.getElementById("J_goodsList");
        Elements elements=element.getElementsByTag("li");
        List<WebContent> list=new ArrayList<>();
        for (Element el:elements){
            String img=el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price=el.getElementsByClass("p-price").eq(0).text();
            String title=el.getElementsByClass("p-name").eq(0).text();
            WebContent webContent=WebContent.builder().img(img).price(price).title(title).build();
            list.add(webContent);
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            parse("java").forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

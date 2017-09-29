package com.lin.elastic;

import com.lin.elastic.Blog;
import com.lin.elastic.JsonUtil;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class DataFactory {
    private static DataFactory dataFactory = new DataFactory();
    private String xx;

    private DataFactory() {
    }

    public static DataFactory getInstance() {

            return dataFactory;

    }

    public  List<String> getInitJsonData() {

        List<String> list = new ArrayList<String>();
        String data1 = JsonUtil.model2Json(new Blog(1, "git简介", "2016-06-19", "SVN与Git最主要的区别..."));
        String data2 = JsonUtil.model2Json(new Blog(2, "Java中泛型的介绍与简单使用", "2016-06-19", "学习目标 掌握泛型的产生意义..."));
        String data3 = JsonUtil.model2Json(new Blog(3, "SQL基本操作", "2016-06-19", "基本操作：CRUD ..."));
        String data4 = JsonUtil.model2Json(new Blog(4, "Hibernate框架基础", "2016-06-19", "Hibernate框架基础..."));
        String data5 = JsonUtil.model2Json(new Blog(5, "Shell基本知识", "2016-06-19", "Shell是什么..."));
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        list.add(data5);
        return list;
    }

    public BulkRequestBuilder buildrequest(List<Blog> blogs,String index,String type){


        BulkRequestBuilder requestBuilder=ElasticSearchHandler.getClient().prepareBulk();

        for(Blog blog:blogs) {
         IndexRequestBuilder  indexRequestBuilder= ElasticSearchHandler.getClient().prepareIndex(index,type);
            requestBuilder.add(indexRequestBuilder.setSource(JsonUtil.model2Json2(blog)));
        }
        return requestBuilder;

    }

}
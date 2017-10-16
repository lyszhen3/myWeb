package com.lin.elastic;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


/**
 * 2.2.0
 */
public class ElasticSearchHandler {
    private static Client client;


    public static Client getClient(){
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;

    }
    public static void main(String[] args) {

            /* 创建客户端 */
            // client startup
        //createData();
        createDataList();

    }


    public static void createData(){
        Client client = getClient();

            List<String> jsonData = DataFactory.getInstance().getInitJsonData();


            for (int i = 0; i < jsonData.size(); i++) {
                IndexResponse response = client.prepareIndex("blog", "article").setSource(jsonData.get(i)).get();

                if (response.isCreated()) {
                    System.out.println("创建成功!");
                }
            }
            client.close();
    }

    //批量添加
    public static void createDataList(){

        List<Blog> blogs=new ArrayList<Blog>();
        blogs.add(new Blog(1, "git简介", "2016-06-19", "SVN与Git最主要的区别..."));
        blogs.add(new Blog(2, "Java中泛型的介绍与简单使用", "2016-06-19", "学习目标 掌握泛型的产生意义..."));
        blogs.add(new Blog(3, "SQL基本操作", "2016-06-19", "基本操作：CRUD ..."));
        blogs.add(new Blog(4, "Hibernate框架基础", "2016-06-19", "Hibernate框架基础..."));
        blogs.add(new Blog(5, "Shell基本知识", "2016-06-19", "Shell是什么..."));
       BulkRequestBuilder bulkRequestBuilder= DataFactory.getInstance().buildrequest(blogs,"blog","article");
        BulkResponse bulkResponse = bulkRequestBuilder.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            System.out.println("错误？？");
        }
        client.close();

    }

}
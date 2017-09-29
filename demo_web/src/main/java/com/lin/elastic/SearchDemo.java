package com.lin.elastic;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by pc on 2017-01-17.
 *
 * @author pc
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class SearchDemo {

    //查找已创建索引的各个值
    @Test
    public void search(){

        Client client=ElasticSearchHandler.getClient();

        //查询title包含hibernate的的文档
        QueryBuilder qb1= QueryBuilders.termQuery("title","hibernate");
        //查询 title content 包含git的文档
        QueryBuilder qb2= QueryBuilders.multiMatchQuery("git", "title","content");


        SearchResponse response = client.prepareSearch("blog").setTypes("article").setQuery(qb2).execute()
                .actionGet();
        SearchHits hits=response.getHits();
        System.out.println(response.getHits());


        for(SearchHit hit:hits){
            Blog b=new Blog();


            System.out.println(b.getContent());
        }

    }

}

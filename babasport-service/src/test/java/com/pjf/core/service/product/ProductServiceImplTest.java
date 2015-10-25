package com.pjf.core.service.product;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * Created by pengjinfei on 2015/10/24.
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class ProductServiceImplTest {


    @Qualifier("productService")
    @Autowired
    private ProductService productService;
    @Autowired
    private Jedis jedis;
    @Autowired
    private SolrServer solrServer;

    @Test
    public void testSelectPaginationByQuery() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        jedis.hset("brand", "6", "iphone");
        String brand = jedis.hget("brand", "6");
        System.out.println(brand);
    }

    @Test
    public void testIsShow() throws Exception {
        SolrQuery query=new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            Object brandId = result.get("brandId");
            if (brandId == null) {
                solrServer.deleteById((String) result.get("id"));
            }
        }
        solrServer.commit();
    }

    @Test
    public void testSelectPaginationbyQueryFromSolr() throws Exception {
        solrServer.deleteById("9");
        solrServer.commit();
    }
}
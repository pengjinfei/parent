package com.pjf.core.service.product;

import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.*;
import com.pjf.core.dao.product.ImgDao;
import com.pjf.core.dao.product.ProductDao;
import com.pjf.core.dao.product.SkuDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

/**
 * Created by pengjinfei on 2015/10/20.
 * Description:
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ImgDao imgDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private Jedis jedis;
    @Autowired
    private SolrServer solrServer;


    @Override
    public Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow) {
        ProductQuery query=new ProductQuery();
        query.setPageNo(Pagination.cpn(pageNo));
        query.setPageSize(3);
        ProductQuery.Criteria criteria = query.createCriteria();
        query.setOrderByClause("id desc");
        StringBuilder stringBuilder=new StringBuilder();
        if (name != null) {
            criteria.andNameLike("%" + name + "%");
            stringBuilder.append("name=" + name);
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
            stringBuilder.append("&brandId=" + brandId);
        }
        if (isShow != null) {
            criteria.andIsShowEqualTo(isShow);
            stringBuilder.append("&isShow=" + isShow);
        }else{
            criteria.andIsShowEqualTo(false);
            stringBuilder.append("&isShow=").append(false);
        }
        List<Product> products = productDao.selectByExample(query);
        for (Product product : products) {
            ImgQuery imgQuery=new ImgQuery();
            imgQuery.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(true);
            Img img = imgDao.selectByExample(imgQuery).get(0);
            product.setImg(img);
        }
        Pagination pagination = new Pagination(query.getPageNo(),
                query.getPageSize(), productDao.countByExample(query));
        query.setPageNo(pagination.getPageNo());
        pagination.pageView("/product/list.do",stringBuilder.toString());
        pagination.setList(products);
        return pagination;
    }

    @Override
    public void insert(Product product) {
        Long pname = jedis.incr("pname");
        product.setId(pname);
        //设置上下架  默认要求是下架
        product.setIsShow(false);
        //设置是否删除   不删除 1
        product.setIsDel(true);
        //保存商品
        productDao.insertSelective(product);
        //图片保存
        Img img = product.getImg();
        //设置商品ID
        img.setProductId(product.getId());
        //设置是否是默认
        img.setIsDef(true);
        //保存图片表
        imgDao.insertSelective(img);
        //保存Sku
        // 商品ID 、颜色、尺码 （最小销售）
        for(String color : product.getColors().split(",")){
            //9 转Long
            //创建Sku对象
            Sku sku = new Sku();
            //设置颜色
            sku.setColorId(Long.parseLong(color));
            //设置商品ID
            sku.setProductId(product.getId());
            for(String size : product.getSizes().split(",")){
                //设置尺码
                sku.setSize(size);
                //设置运费
                sku.setDeliveFee(10f);
                //市场价格
                sku.setMarketPrice(0f);
                //售价
                sku.setPrice(0f);
                //库存
                sku.setStock(0);
                //默认200件  可更改
                sku.setUpperLimit(200);
                //遍历尺码 处  保存Sku
                skuDao.insertSelective(sku);
            }

        }
    }

    @Override
    public void isShow(Long[] ids) {
        for (Long id : ids) {
            Product product=new Product();
            product.setId(id);
            product.setIsShow(true);
            productDao.updateByPrimaryKeySelective(product);
            product = productDao.selectByPrimaryKey(id);
            //保存商品搜索信息到solr服务器
            SolrInputDocument document=new SolrInputDocument();
            document.setField("id", id);
            document.setField("name_ik", product.getName());
            //价格
            SkuQuery skuQuery = new SkuQuery();
            skuQuery.createCriteria().andProductIdEqualTo(id);
            skuQuery.setOrderByClause("price asc");
            skuQuery.setPageNo(1);
            skuQuery.setPageSize(1);
            skuQuery.setFields("price");
            List<Sku> skus = skuDao.selectByExample(skuQuery);
            document.setField("price", skus.get(0).getPrice());
            //图片Url
            ImgQuery imgQuery = new ImgQuery();
            imgQuery.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(true);
            List<Img> imgs = imgDao.selectByExample(imgQuery);
            document.setField("url",imgs.get(0).getUrl());
            //品牌
            document.setField("brandId",product.getBrandId());
            //时间
            document.setField("last_modified",new Date());
            try {
                solrServer.add(document);
                solrServer.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Pagination selectPaginationbyQueryFromSolr(Integer pageNo,String keyword,Long brandId,String price) throws Exception {
        SolrQuery solrQuery=new SolrQuery();
        StringBuilder params=new StringBuilder();
        if (StringUtils.isNotEmpty(keyword)) {
            solrQuery.set("q", "name_ik:" + keyword);
            params.append("&keyword=" + keyword);
        } else {
            solrQuery.setQuery("*:*");
        }
        if (brandId != null) {
            solrQuery.addFilterQuery("brandId:" + brandId);
            params.append("&brandId=" + brandId);
        }
        if (StringUtils.isNotEmpty(price)) {
            String[] split = price.split("-");
            solrQuery.addFilterQuery("price:[" + split[0] + " TO " + split[1] + "]");
            params.append(("&price=" + price));
        }
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("name_ik");
        solrQuery.setHighlightSimplePre("<font style='color:red'>");
        solrQuery.setHighlightSimplePost("</font>");
        pageNo = Pagination.cpn(pageNo);
        int startRow = (pageNo - 1) * Product.PAGESIZE;
        solrQuery.setStart(startRow);
        solrQuery.setRows(Product.PAGESIZE);
        solrQuery.addSort("price", SolrQuery.ORDER.asc);
        QueryResponse response = solrServer.query(solrQuery);
        SolrDocumentList results = response.getResults();
        Pagination pagination=new Pagination(pageNo,Product.PAGESIZE, (int) results.getNumFound());
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        List<Product> products=new ArrayList<>();
        for (SolrDocument result : results) {
            Product product=new Product();
            String id = (String) result.get("id");
            product.setId(Long.parseLong(id));
            String name_ik = highlighting.get(id).get("name_ik").get(0);
            product.setName(name_ik);
            Sku sku=new Sku();
            sku.setPrice((Float) result.get("price"));
            product.setSku(sku);
            Img img=new Img();
            img.setUrl((String) result.get("url"));
            product.setImg(img);
            product.setBrandId(Long.valueOf((Integer) result.get("brandId")));
            products.add(product);
        }
        pagination.setList(products);
        String url = "/product/display/list.shtml";
        pagination.pageView(url, params.toString());
        return pagination;
    }

    @Override
    public List<Brand> selectBrandListFormRedis() {
        List<Brand> brands=new ArrayList<>();
        Map<String, String> map = jedis.hgetAll("brand");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            Brand brand=new Brand();
            brand.setId(Long.parseLong(entry.getKey()));
            brand.setName(entry.getValue());
            brands.add(brand);
        }
        return brands;
    }

    @Override
    public String selectBrandNameById(Long brandId) {
        return jedis.hget("brand", String.valueOf(brandId));
    }

    @Override
    public Product selectProdutById(Long id) {
        return productDao.selectByPrimaryKey(id);
    }
}
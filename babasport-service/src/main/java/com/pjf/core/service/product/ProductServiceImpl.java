package com.pjf.core.service.product;

import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.*;
import com.pjf.core.dao.product.ImgDao;
import com.pjf.core.dao.product.ProductDao;
import com.pjf.core.dao.product.SkuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

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
}
package com.pjf.core.service.product;


import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.Brand;
import com.pjf.core.bean.product.Product;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/20.
 * Description:
 */
public interface ProductService {
    Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow);

    void insert(Product product);

    void isShow(Long[] ids);

    Pagination selectPaginationbyQueryFromSolr(Integer pageNo,String keyword,Long brandId,String price) throws Exception;

    List<Brand> selectBrandListFormRedis();

    String selectBrandNameById(Long brandId);

    Product selectProdutById(Long id);
}

package com.pjf.core.service.product;


import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.Product;

/**
 * Created by pengjinfei on 2015/10/20.
 * Description:
 */
public interface ProductService {
    Pagination selectPaginationByQuery(Integer pageNo, String name, Long brandId, Boolean isShow);

    void insert(Product product);
}

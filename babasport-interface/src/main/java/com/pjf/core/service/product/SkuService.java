package com.pjf.core.service.product;

import com.pjf.core.bean.product.Sku;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/21.
 * Description:
 */
public interface SkuService {
    void update(Sku sku);
    List<Sku> selectByProductId(Long productId);

    List<Sku> selectSkuListByProductIdWithStock(Long id);
}

package com.pjf.core.service.product;

import com.pjf.core.bean.product.Sku;
import com.pjf.core.bean.product.SkuQuery;
import com.pjf.core.dao.product.ColorDao;
import com.pjf.core.dao.product.SkuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/21.
 * Description:
 */
@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;

    @Override
    public void update(Sku sku) {
        skuDao.updateByPrimaryKeySelective(sku);
    }

    @Override
    public List<Sku> selectByProductId(Long productId) {
        SkuQuery query=new SkuQuery();
        query.createCriteria().andProductIdEqualTo(productId);
        List<Sku> skus = skuDao.selectByExample(query);
        for (Sku sku : skus) {
            sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
        }
        return skus;
    }
}

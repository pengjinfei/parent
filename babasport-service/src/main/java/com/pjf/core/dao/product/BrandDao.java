package com.pjf.core.dao.product;

import com.pjf.core.bean.product.Brand;
import com.pjf.core.bean.product.BrandQuery;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
public interface BrandDao {
    //查询
    List<Brand> selectBrandListByQuery(BrandQuery brandQuery);

    //查询总条件（符合条件)
    Integer selectCount(BrandQuery brandQuery);

    //保存
    void insertBrand(Brand brand);

    void deleteBatch(Long[] ids);

    void deleteById(Long id);

    Brand findById(Long id);

    void update(Brand brand);
}

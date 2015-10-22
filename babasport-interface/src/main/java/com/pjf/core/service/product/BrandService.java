package com.pjf.core.service.product;

import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.Brand;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
public interface BrandService  {
    // 查询结果信
    // 查询
    List<Brand> selectBrandListByQuery(String name, Integer isDisplay);


    //查询分页对象
    Pagination selectPaginationByQuery(Integer pageNo, String name, Integer isDisplay);

    //保存
    void insertBrand(Brand brand);


    void deleteById(Long id);

    void deleteBatch(Long[] ids);

    Brand selectBrandById(Long id);

    void updateBrand(Brand brand);
}

package com.pjf.core.service.product;

import cn.itcast.common.page.Pagination;
import com.pjf.core.bean.product.Brand;
import com.pjf.core.bean.product.BrandQuery;
import com.pjf.core.dao.product.BrandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/19.
 * Description:
 */
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> selectBrandListByQuery(String name, Integer isDisplay) {
        BrandQuery query=new BrandQuery();
        if (name != null) {
            query.setName(name);
        }
        if (isDisplay != null) {
            query.setIsDisplay(isDisplay);
        }
        return brandDao.selectBrandListByQuery(query);
    }

    @Override
    public Pagination selectPaginationByQuery(Integer pageNo, String name, Integer isDisplay) {
        BrandQuery query=new BrandQuery();
        StringBuilder builder=new StringBuilder();
        if (name != null) {
            query.setName(name);
            builder.append("name=" + name);
        }
        if (isDisplay != null) {
            query.setIsDisplay(isDisplay);
            builder.append("&isDisplay=" + isDisplay);
        } else {
            builder.append("&isDisplay=" + 1);
        }
        query.setPageNo(Pagination.cpn(pageNo));
        Pagination pagination=new Pagination(query.getPageNo(),query.getPageSize(),
                brandDao.selectCount(query));
        pagination.setList(brandDao.selectBrandListByQuery(query));
        pagination.pageView("/brand/list.do",builder.toString());
        return pagination;
    }

    @Override
    public void insertBrand(Brand brand) {
        brandDao.insertBrand(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandDao.deleteById(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        brandDao.deleteBatch(ids);
    }

    @Override
    public Brand selectBrandById(Long id) {
        return brandDao.findById(id);
    }

    @Override
    public void updateBrand(Brand brand) {
        brandDao.update(brand);
    }
}

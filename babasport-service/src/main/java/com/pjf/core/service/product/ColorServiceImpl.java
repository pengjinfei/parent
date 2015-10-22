package com.pjf.core.service.product;

import com.pjf.core.bean.product.Color;
import com.pjf.core.bean.product.ColorQuery;
import com.pjf.core.dao.product.ColorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/21.
 * Description:
 */
@Service("colorService")
@Transactional
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorDao colorDao;

    @Override
    public List<Color> selectColorList() {
        ColorQuery query=new ColorQuery();
        query.createCriteria().andParentIdNotEqualTo(0L);
        return colorDao.selectByExample(query);
    }
}

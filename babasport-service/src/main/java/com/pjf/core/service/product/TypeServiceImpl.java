package com.pjf.core.service.product;

import com.pjf.core.bean.product.Type;
import com.pjf.core.bean.product.TypeQuery;
import com.pjf.core.dao.product.TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/21.
 * Description:
 */
@Service("typeService")
@Transactional
public class TypeServiceImpl implements TypeService {


    @Autowired
    private TypeDao typeDao;

    @Override
    public List<Type> selectTypeList() {
        TypeQuery query=new TypeQuery();
        query.createCriteria().andParentIdNotEqualTo(0L).andIsDisplayEqualTo(true);
        return typeDao.selectByExample(query);
    }
}

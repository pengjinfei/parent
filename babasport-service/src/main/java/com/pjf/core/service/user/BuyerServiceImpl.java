package com.pjf.core.service.user;

import com.pjf.core.bean.user.Buyer;
import com.pjf.core.dao.user.BuyerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pengjinfei on 2015/10/26.
 * Description:
 */
@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private BuyerDao buyerDao;

    @Override
    public Buyer selectByUsername(String username) {
        return buyerDao.selectByPrimaryKey(username);
    }
}

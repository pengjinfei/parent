package com.pjf.core.service.user;

import com.pjf.core.bean.user.Buyer;

/**
 * Created by pengjinfei on 2015/10/26.
 * Description:
 */
public interface BuyerService {
    Buyer selectByUsername(String username);
}

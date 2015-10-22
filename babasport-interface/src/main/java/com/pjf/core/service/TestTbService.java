package com.pjf.core.service;


import com.pjf.core.bean.TestTb;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/17.
 * Description:
 */

public interface TestTbService {

    //保存
     void insertTestTb(TestTb testTb);

    //查询
     List<TestTb> selectTestTbList();

}

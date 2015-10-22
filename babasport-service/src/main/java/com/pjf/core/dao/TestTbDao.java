package com.pjf.core.dao;

import com.pjf.core.bean.TestTb;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/17.
 * Description:
 */
public interface TestTbDao {

    void addTestTb(TestTb testTb);

    List<TestTb> selectAllTestTb();
}

package com.pjf.core.service;

import com.pjf.core.bean.TestTb;
import com.pjf.core.dao.TestTbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengjinfei on 2015/10/17.
 * Description:
 */
@Service(value = "testTbService")
@Transactional
public class TestTbServiceImpl implements TestTbService {


    @Autowired
    private TestTbDao testTbDao;

    @Override
    public void insertTestTb(TestTb testTb) {
        testTbDao.addTestTb(testTb);
    }

    @Override
    public List<TestTb> selectTestTbList() {
        return testTbDao.selectAllTestTb();
    }
}

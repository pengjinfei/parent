package com.pjf.core.dao;


import com.pjf.core.bean.TestTb;
import com.pjf.core.bean.product.Brand;
import com.pjf.core.service.TestTbService;
import com.pjf.core.service.product.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


/**
 * Created by pengjinfei on 2015/10/17.
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestTbDaoTest {

    @Autowired
    private TestTbService testTbService;

    @Autowired
    private BrandService brandService;
    @Test
    public void testAddTestTb() throws Exception {
        TestTb tb=new TestTb();
        tb.setName("pjf");
        tb.setBirthday(new Date());
        testTbService.insertTestTb(tb);
    }

    @Test
    public void testSelectAllTestTb() throws Exception {
        Brand brand=new Brand();
        brand.setName("pjf");
       brandService.insertBrand(brand);

    }
}
package com.pjf.core.dao.product;


import com.pjf.core.bean.product.Feature;
import com.pjf.core.bean.product.FeatureQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeatureDao {
    int countByExample(FeatureQuery example);

    int deleteByExample(FeatureQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(Feature record);

    int insertSelective(Feature record);

    List<Feature> selectByExample(FeatureQuery example);

    Feature selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Feature record, @Param("example") FeatureQuery example);

    int updateByExample(@Param("record") Feature record, @Param("example") FeatureQuery example);

    int updateByPrimaryKeySelective(Feature record);

    int updateByPrimaryKey(Feature record);
}
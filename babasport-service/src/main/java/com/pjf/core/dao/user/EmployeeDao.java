package com.pjf.core.dao.user;


import com.pjf.core.bean.user.Employee;
import com.pjf.core.bean.user.EmployeeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDao {
    int countByExample(EmployeeQuery example);

    int deleteByExample(EmployeeQuery example);

    int deleteByPrimaryKey(String username);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeQuery example);

    Employee selectByPrimaryKey(String username);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeQuery example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeQuery example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}
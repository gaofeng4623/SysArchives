package com.system.dao.database;

import java.util.List;

import com.archives.pojo.ArchivesInfo;
import com.system.base.pojo.Department;
import com.system.base.pojo.Rolemembers;

public interface DepartmentDao {
    int deleteByPrimaryKey(String departmentid);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String departmentid);
    
    Department selectByName(String departmentName);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

	List<Department> selectAllDepratments();

	List<Department> selectBySuperId(String departmentid);

	List<Rolemembers> selectRoleId(String roleId);
	List<ArchivesInfo> listInfo(String middleTime);
}
package com.system.dao.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.system.base.pojo.Menu;
import com.system.base.pojo.MenuItem;

public interface MenuDao {
    int deleteByPrimaryKey(String menuId);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(String menuId);

    List<Menu> selectBySuperId(String superid);

    int updateByPrimaryKeySelective(Menu record);

    List<Menu> selectAllMenu();

    List<Menu> findMenuByIds(@Param("idList")List<Long> idList);

    List<Menu> selectMenuByMenuIds(@Param("idList")List<String> idList,@Param("startIndex")int startIndex,@Param("perPageNum")int perPageNum);

    Integer countMenuByMenuIds(@Param("idList")List<String> idList);
    
    List<Menu> selectLimitedMenusByRoleId(@Param("roleid")String roleid);	
    
    List<MenuItem> selectLimitedMenusByUserid(@Param("userid")String userid);
    
    List<MenuItem> selectAllMenuItems();
    
    List<Menu> selectMenusByGroupId(@Param("groupid")String groupId);
    
    List<Menu> selectGroupMenus();

	void deleteLimitByMenuId(String menuId);
}
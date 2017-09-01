package com.system.dao.database;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.stereotype.Component;

@Component
public class InitSystemDataDao {
	@Resource
	private DataSource gtds;
	
	public void initSysData() {
		Connection conn = null;
		try {
			conn = gtds.getConnection();
			ScriptRunner runner = new ScriptRunner(conn);
			runner.runScript(new InputStreamReader(
			this.getClass().getClassLoader().getResourceAsStream("com/system/base/resource/initMenu.sql"), "utf-8"));
		} catch (Exception e) {
			System.err.println("初始化系统菜单数据出错,错误信息：" + e.getMessage());
		} finally {
			if (conn != null) try {conn.close();} catch (SQLException e) {}
		}
	}
	
}

package DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.json.JSONArray;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import Tool.GetLog;
import Tool.ReadConfig;
import Tool.ResultSet_To_JSON;
import Tool.String_Tool;

public class DB {
	

	public static void main(String args[]) {
		//System.out.println(String_Tool.DataBaseH_M_S());
	//	select(GetConnection.getSpuerConn());
		//System.out.println(String_Tool.DataBaseH_M_S());
		try {
			select(GetConnection.getSpuerConn());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static JSONArray select(Connection conn) { // 测试方法
		String sql = "   select * from PS_Wallet.dbo.W_bBuyer";
		try {
			PreparedStatement past = conn.prepareStatement(sql);
			ResultSet rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			 System.out.println(array);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Map<String, DataSource> map = new HashMap<String, DataSource>();

	static void init(String Ip, String DataSourceName) {
		Properties p = new Properties();
		p.setProperty("driverClassName",
				"com.microsoft.sqlserver.jdbc.SQLServerDriver");
		p.setProperty("url", "jdbc:sqlserver://" + Ip + ";databaseName="
				+ DataSourceName);
		p.setProperty("username", "sa");
		p.setProperty("password", new ReadConfig().getprop().getProperty("PassWord"));

		p.setProperty("filters", "stat");
		p.setProperty("initialSize", "30");
		p.setProperty("maxActive", "3000");
		p.setProperty("maxWait", "60000");

		p.setProperty("timeBetweenEvictionRunsMillis", "60000");
		p.setProperty("minEvictableIdleTimeMillis", "300000");
		p.setProperty("testWhileIdle", "false");
		p.setProperty("testOnBorrow", "false"); //
		p.setProperty("testOnReturn", "false");
		p.setProperty("poolPreparedStatements", "false");
		p.setProperty("maxPoolPreparedStatementPerConnectionSize", "200");
		try {
			DataSource dataSource = DruidDataSourceFactory.createDataSource(p);
			map.put(DataSourceName, dataSource);
		} catch (Exception e) {
			e.printStackTrace();
			GetLog.getLogger(GetLog.class).error("获取连接池异常", e);
		}
	}

	public static  Connection getConnection(String Ip,String DataSourceName) throws SQLException {
		DataSource dataSource = map.get(DataSourceName);
		if (dataSource == null) {
			init(Ip, DataSourceName);
			System.out.println("新建连接池" + DataSourceName);
			dataSource = map.get(DataSourceName);
		}
		return dataSource.getConnection();
	}

	public static void closeResultSet(ResultSet rs) { // 关闭结果集
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeCallState(CallableStatement c) { // 关闭存储过程
		try {
			if (c != null) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConn(Connection conn) {// 关闭链接
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closePreparedStatement(Statement  past) {
		if (past != null) {
			try {
				past.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}
	public static void closeRs_Statement(ResultSet rs,Statement  past) {
		closeResultSet(rs);
		closePreparedStatement(past);
		
	}
	public static void closeStatement_Rs(Statement  past,Connection conn) {
		closePreparedStatement(past);
		closeConn(conn);
	}

	public static void closeRs_Con(CallableStatement c, Connection conn) {
		closeCallState(c);
		closeConn(conn);
	}

	public static void closeRs_Con(ResultSet rs, CallableStatement c,
			Connection conn) {
		closeResultSet(rs);
		closeRs_Con(c, conn);
	}

	public static void closeRs_Con(ResultSet rs, Statement past,
			Connection conn) {
		closeResultSet(rs);
		closePreparedStatement(past);
		closeConn(conn);
	}

	public static void closeAll(ResultSet rs, Statement  past,
			CallableStatement c, Connection conn) {
		closeResultSet(rs);
		closePreparedStatement(past);
		closeRs_Con(c, conn);
	}
}

package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import Tool.ReadConfig;
import Tool.ResultSet_To_JSON;

public class GetConnection {
	static {
		DB.closeConn(GetConnection.getSpuerConn());
		DB.closeConn(GetConnection.getPos_ManagermainConn());
		DB.closeConn(GetConnection.getStoreWalletConn());
		DB.closeConn(GetConnection.getSupermarketConn());
	}

	static Logger logger = Logger.getLogger(GetConnection.class);

	// 得到总部的数据库的链接
	public static Connection getSpuerConn() { // 得到simple_online数据库的连接
		try {
			Connection conn = DB.getConnection(new ReadConfig().getprop().getProperty("Simple_online_IP"),
					new ReadConfig().getprop().getProperty("Simple_online_DataSource")); // 初始化线上数据库的连接
			return conn;
		} catch (Exception e) {
			logger.error("或得商家下的分店的数据库Connection异常");
			e.printStackTrace();
		}
		return null;
	}

	// 得到总部数据库的连接
	public static Connection getPos_ManagermainConn() {
		try {
			Connection conn = DB.getConnection(new ReadConfig().getprop().getProperty("Posmanagement_main_IP"),
					new ReadConfig().getprop().getProperty("Posmanagement_main_DataSource")); // 初始化总部数据库的连接
			return conn;
		} catch (Exception e) {
			logger.error("或得商家下的分店的数据库Connection异常");
			e.printStackTrace();
		}
		return null;
	}

	// 得到商家下的分店的数据库的链接
	public static Connection getStoreConn(String storeno) {
		try {
			Connection conn = DB.getConnection(new ReadConfig().getprop().getProperty(storeno),
					new ReadConfig().getprop().getProperty(storeno + "DataSource")); // 初始化加盟商户的数据库链接
			return conn;
		} catch (Exception e) {
			logger.error("或得商家下的分店的数据库Connection异常");
			e.printStackTrace();
		}
		return null;
	}

	// 得到钱包数据库的链接
	public static Connection getStoreWalletConn() {
		try {
			Connection conn = DB.getConnection(new ReadConfig().getprop().getProperty("PS_Wallet_IP"),
					new ReadConfig().getprop().getProperty("PS_Wallet_DataSource")); //
			return conn;
		} catch (Exception e) {
			logger.error("或得商家下的分店的数据库Connection异常");
			e.printStackTrace();
		}
		return null;
	}

	// 得到supermarket数据库的链接
	public static Connection getSupermarketConn() {
		try {
			Connection conn = DB.getConnection(new ReadConfig().getprop().getProperty("Supermarket_IP"),
					new ReadConfig().getprop().getProperty("SupermarketDataSource")); // 初始化加盟商户的数据库链接
			return conn;
		} catch (Exception e) {
			logger.error("或得商家下的分店的数据库Connection异常");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

		try {
			Connection conn = getSpuerConn();
			PreparedStatement past = conn.prepareStatement("select * from dbo.User_Table where UserNo like ?");

			past.setString(1, "%" + "123456" + "%");
			System.out.println(past.toString());
			ResultSet rs = past.executeQuery();
			JSONArray array = ResultSet_To_JSON.resultSetToJsonArray(rs);
			System.out.println(array.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

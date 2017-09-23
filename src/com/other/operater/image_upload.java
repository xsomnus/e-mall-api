package com.other.operater;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DB.DB;
import DB.GetConnection;

public class image_upload {

	public static void main(String[] args) {
		getFileName();

	}

	public static void getFileName() {
		String path = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\Simple_onlineManger\\images\\Goods"; // Â·¾¶
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}
		Connection conn = null;
		try {
			conn = GetConnection.getSpuerConn();
			conn.setAutoCommit(false);

			PreparedStatement past = conn.prepareStatement(
					"update posmanagement_main.dbo.t_cStoreGoods set bOnLine='0' where cStoreNo='000' ");
			past.execute();

			List<String> list = new ArrayList<String>();
			File fa[] = f.listFiles();
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (fs.isDirectory()) {
					// System.out.println(fs.getName() + " [Ä¿Â¼]");
				} else {
					list.add(fs.getName());
					System.out.println(fs.getName());
				}
			}
			PreparedStatement past1 = conn.prepareStatement("update posmanagement_main.dbo.t_cStoreGoods set bOnLine='1' where cBarcode =?  ");
			for (int i = 0; i < list.size(); i++) {
				String str = list.get(i);
				System.out.println(str);
				System.out.println(list.get(i).substring(0, str.indexOf(".")));
				past1.setString(1, list.get(i).substring(0, str.indexOf(".")));
				past1.addBatch();
			}
			int[] a = past1.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			DB.closeConn(conn);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}

	}

}

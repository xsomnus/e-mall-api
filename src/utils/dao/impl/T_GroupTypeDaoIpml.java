package utils.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Tool.ResultSet_To_JSON;
import Tool.String_Tool;
import bean.Advertisement;
import bean.Shop_Car;
import bean.T_Goods;
import bean.T_GroupType;

import DB.GetConnection;

import utils.dao.T_GroupTypeDao;

public class T_GroupTypeDaoIpml implements T_GroupTypeDao {
	/**
	 * 方法区
	 */
	
	//广告
	public List<Advertisement> getAdvertisement(){
		Connection conn=GetConnection.getSpuerConn();
		String sql="select * from Advertisement ";
		try{
			PreparedStatement past=conn.prepareStatement(sql);
			ResultSet rs=past.executeQuery();
			JSONArray array=ResultSet_To_JSON.resultSetToJsonArray(rs);
			Gson gson=new Gson();
			List<Advertisement> list = gson.fromJson(array.toString(),new TypeToken<List<Advertisement>>() {}.getType());
			return list;
		}catch (Exception e) {
            e.printStackTrace();
		}
		return null;
	}
	
	
	
	//四个小方框
	public List<T_GroupType> search(){
	List<T_GroupType> list=new ArrayList<T_GroupType>();
	T_GroupType t=null;
	Connection conn=GetConnection.getSpuerConn();
	String sql="select * from T_GroupType where cParentNo='--'";
	try {
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()){
			t=new T_GroupType();
			t.setcGroupTypeName(rs.getString("cGroupTypeName"));
			t.setcGroupTypeNo(rs.getString("cGroupTypeNo"));
			list.add(t);
			}
	}
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return list;
	}

	//策略 --推荐
	@Override
	public List<T_Goods> tuijian() {
		List<T_Goods> list=new ArrayList<T_Goods>();
		T_Goods t=null;
		Connection conn=GetConnection.getSpuerConn();
		String sql="select Recommend_price,cGoodsNo,cGoodsName from dbo.T_goods where  Recommend='1'";
		try {
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				t=new T_Goods();
				t.setcGoodsName(rs.getString("cGoodsName"));
				t.setfNormalPrice(String.valueOf(rs.getInt("Recommend_price")));
				t.setcGoodsNo(rs.getString("cGoodsNo"));
				list.add(t);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
/**
 * 
UseNo	varchar(50)	Checked                 用户编号
Date_time	datetime	Checked                 时间
UserName	varchar(50)	Checked             用户名
cStoreNo	varchar(20)	Checked                 店铺编号
cGoodsNo	varchar(50)	Checked             商品编号
Last_Price	money	Checked                 价格（最终）
Num	   money	Checked                     数量
Last_Money	money	Checked                 钱数
 
 */

	@Override
	public List<Shop_Car> guwuche() {
		List<Shop_Car> list=new ArrayList<Shop_Car>();
		Shop_Car t=null;
		Connection conn=GetConnection.getSpuerConn();
		String sql="select * from Shop_Car";
		try {
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				t=new Shop_Car();
				t.setcGoodsNo(rs.getString("cGoodsNo"));
				t.setcStoreNo(rs.getString("cStoreNo"));
				t.setDate_time(rs.getString("Date_time"));
				int num=rs.getInt("Num");
				Double Last_Price=(double)rs.getInt("Last_Price");
				Double Last_Money=(double)num*Last_Price;
				t.setNum(String.valueOf(num));
				t.setUserName(rs.getString("UserName"));
				t.setLast_Price(String.valueOf(Last_Price));
				t.setLast_Money(String.valueOf(Last_Money));
				t.setUseNo(rs.getString("UseNo"));
				list.add(t);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	}

	
}

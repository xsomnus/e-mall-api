package utils.dao;

import java.util.List;

import org.json.JSONArray;

import bean.T_GroupType;


public interface T_GroupTypeDao {
	
	public List getAdvertisement();
	public List<T_GroupType> search();

	public List tuijian();

	public List guwuche();

	
}

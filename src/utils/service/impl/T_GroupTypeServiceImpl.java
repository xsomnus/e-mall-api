package utils.service.impl;

import java.util.List;

import org.json.JSONArray;

import bean.Advertisement;
import bean.Shop_Car;

import utils.dao.T_GroupTypeDao;
import utils.dao.impl.T_GroupTypeDaoIpml;
import utils.web.service.T_GroupTypeService;

public class T_GroupTypeServiceImpl implements T_GroupTypeService {

	@Override
	public List getAdvertisement(){
		T_GroupTypeDao dao=new T_GroupTypeDaoIpml();
		List array=dao.getAdvertisement();
		return array;
	}
	@Override
	public List<Shop_Car> search() {
		T_GroupTypeDao dao=new T_GroupTypeDaoIpml();
		List list = dao.search();
		return list;
	}

	@Override
	public List tuijian() {
		T_GroupTypeDao dao=new T_GroupTypeDaoIpml();
		List list = dao.tuijian();
		return list;
	}

	@Override
	public List gouwuche() {
		T_GroupTypeDao dao=new T_GroupTypeDaoIpml();
		List list = dao.guwuche();
		return list;
	}
}

package bean;

import java.util.List;

public class City {
	
	private String city;
	private String province;
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	private List<Distrinct> distrincts;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<Distrinct> getDistrincts() {
		return distrincts;
	}
	public void setDistrincts(List<Distrinct> distrincts) {
		this.distrincts = distrincts;
	}
	

}

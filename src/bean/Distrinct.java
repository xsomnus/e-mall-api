package bean;

import java.util.List;

public class Distrinct {
	
	private String distrinct;
	
	private List<Street> streets;
	
	
	public String getDistrinct() {
		return distrinct;
	}
	public void setDistrinct(String distrinct) {
		this.distrinct = distrinct;
	}
	public List<Street> getStreets() {
		return streets;
	}
	public void setStreets(List<Street> streets) {
		this.streets = streets;
	}
	@Override
	public String toString() {
		return "Distrinct [distrinct=" + distrinct + ", streets=" + streets + "]";
	}
	
	

}

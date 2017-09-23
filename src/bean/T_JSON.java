package bean;

import java.util.List;


public class T_JSON {
	
    private String cPloyTypeNo;
    private String cPloyTypeName;
    private List<T_PloyOfSale> t_PloyOfSale;
    
	public String getcPloyTypeNo() {
		return cPloyTypeNo;
	}
	public void setcPloyTypeNo(String cPloyTypeNo) {
		this.cPloyTypeNo = cPloyTypeNo;
	}
	public String getcPloyTypeName() {
		return cPloyTypeName;
	}
	public void setcPloyTypeName(String cPloyTypeName) {
		this.cPloyTypeName = cPloyTypeName;
	}
	public List<T_PloyOfSale> getT_PloyOfSale() {
		return t_PloyOfSale;
	}
	public void setT_PloyOfSale(List<T_PloyOfSale> t_PloyOfSale) {
		this.t_PloyOfSale = t_PloyOfSale;
	}
}

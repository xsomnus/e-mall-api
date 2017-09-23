package Tool;

public class Paging_Index_Sql {

	public static void main(String args[]) {
	//	sql("select * from User_Table");
		sql("AddressID","select * from dbo.User_Table a ,dbo.User_Address b where a.UserNo=b.UserNo",1);
	}

	public static String sql(String sort,String sql_,int Number_of_pages) {

		String sql = "SELECT TOP 10 *  FROM (SELECT ROW_NUMBER() OVER (ORDER BY  "+sort+") AS RowNumber,* FROM  ("
				+ sql_ + " ) as  Z ) as A WHERE RowNumber > 10*("+(Number_of_pages-1)+")";

		System.out.println(sql);
		return sql;

	}
}

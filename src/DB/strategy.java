package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class strategy {
	
	public static String User_Log_in(Connection conn, String UserName, String pass) {
		PreparedStatement past = null;
		ResultSet rs = null;
		String str = null;
		try {
			past = conn.prepareStatement("select Tel,UserNo,ImagePath,Integral,cStoreNo,rank,discount_rate from dbo.User_Table where (UserNo=? or Tel=? Or UserName=? or Email=?) and UserPass=? ");
			past.setString(1, UserName);
			past.setString(2, UserName);
			past.setString(3, UserName);
			past.setString(4, UserName);
			past.setString(5, pass);
			rs = past.executeQuery();
		
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.closeRs_Con(rs, past, conn);
		}
		return null;
	}
}

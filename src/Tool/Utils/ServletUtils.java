package Tool.Utils;


import javax.servlet.http.HttpServletResponse;

/**
 * Created by xwy_brh on 2017/7/15.
 */
public class ServletUtils {

    public static void setResponse(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("content-type", "text/html;charset=UTF-8");
    }

}

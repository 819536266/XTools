package com.xdl.util;

/**
 * @author Bx_Hu
 */
public interface MethodExcludeParam {

    String HTTP_SESSION = "javax.servlet.http.HttpSession";

    String HTTP_SERVLET_REQUEST = "javax.servlet.http.HttpServletRequest";

    String HTTP_SERVLET_RESPONSE = "javax.servlet.http.HttpServletResponse";



    String[] EXCLUDE ={
            HTTP_SESSION,
            HTTP_SERVLET_REQUEST,
            HTTP_SERVLET_RESPONSE
    };


}

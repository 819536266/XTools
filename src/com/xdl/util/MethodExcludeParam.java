package com.xdl.util;

public interface MethodExcludeParam {

    String HTTP_SESSION = "javax.servlet.http.HttpSession";

    String HTTP_SERVLET_REQUEST = "javax.servlet.http.HttpServletRequest";

    String HTTP_SERVLET_RESPONSE = "javax.servlet.http.HttpServletResponse";



    String[] exclude={
            HTTP_SESSION,
            HTTP_SERVLET_REQUEST,
            HTTP_SERVLET_RESPONSE
    };


}

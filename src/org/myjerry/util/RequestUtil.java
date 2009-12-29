package org.myjerry.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	
	public static boolean isHttp11(HttpServletRequest request) {
        return request.getProtocol().equals("HTTP/1.1");
    }

}

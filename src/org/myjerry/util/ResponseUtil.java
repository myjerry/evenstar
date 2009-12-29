package org.myjerry.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class ResponseUtil {
	
	public static final ModelAndView sendFileToDownload(HttpServletResponse response, String fileToDownload, 
			String fileName, String contentType, boolean isHttp11) throws IOException {
        
		response.reset();

        setNoCache(response, isHttp11);
        
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentType(contentType);

        ServletOutputStream out;
        out = response.getOutputStream();
        out.print(fileToDownload);
        out.flush();
        out.close();

        return null;
    }
	
	public static final void setNoCache(HttpServletResponse response, boolean isHttp11) {
        if (isHttp11) {
            response.setHeader("Cache-Control", "no-cache");
        } else {
            response.setHeader("Pragma", "no-cache");
            response.addDateHeader("Expires", 0L);
            response.setHeader("Cache-Control", "no-store");
        }
    }

}

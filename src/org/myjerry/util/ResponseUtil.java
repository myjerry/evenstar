/**
 * myJerry | Evenstar
 * Copyright (C) 2010 myJerry Development Team
 * http://www.myjerry.org
 * 
 * The file is licensed under the the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
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

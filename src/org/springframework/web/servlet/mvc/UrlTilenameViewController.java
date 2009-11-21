/**
 * Matrika India Website Code
 * Copyright (C) 2008 Matrika India Development Team
 * http://www.matrika-india.org
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
 * Author: Sandeep Gupta <sandy.pec@gmail.com>
 */
package org.springframework.web.servlet.mvc;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO: type comment.
 *
 * @author Sandeep on Sep 25, 2008 @ 9:29:54 PM
 *
 */
public class UrlTilenameViewController extends AbstractUrlViewController  {
    
    private static final Log log = LogFactory.getLog(UrlTilenameViewController.class);
    
    private String indexTile;
    
    private boolean toLowercase;
    
    private boolean stripAfterLastDot;
    
    private boolean insertStartingDot;

    /**
     * @param request
     * @return
     * @see org.springframework.web.servlet.mvc.AbstractUrlViewController#getViewNameForRequest(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected String getViewNameForRequest(HttpServletRequest request) {
        String uri = extractOperableUrl(request);
        String tileName = getViewNameForUrlPath(uri);
        
        if(log.isDebugEnabled()) {
            log.debug("uri " + uri + " converted to tilename " + tileName);
        }
        
        return tileName;
    }

    protected String extractOperableUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        ServletContext context = request.getSession().getServletContext();
        File rootFile = new File(context.getRealPath ("/"));
        String rootContext = rootFile.getName() + "/";
        
        if(log.isDebugEnabled()) {
            log.debug("Request uri received is " + uri + " in the application context " + rootContext);
        }
        
        if(uri.startsWith("/")) {
            uri = uri.substring(1);
        }
        if(uri.startsWith(rootContext)) {
            uri = uri.substring(rootContext.length());
        }
        
        return uri;
    }
    
    protected String getViewNameForUrlPath(String uri) {
        if(uri == null || "".equals(uri)) {
            return this.indexTile;
        }
        uri = uri.replaceAll("/", ".");
        if(this.toLowercase) {
            uri = uri.toLowerCase();
        }
        if(this.stripAfterLastDot) {
            int index = uri.lastIndexOf(".");
            if(index != -1) {
                uri = uri.substring(0, index);
            }
        }
        if(this.insertStartingDot) {
            uri = "." + uri;
        }
        return uri;
    }

    /** Returns the indexTile.
     * @return the indexTile.
     */
    public String getIndexTile() {
        return indexTile;
    }

    /** Sets the indexTile to the specified value.
     * @param indexTile indexTile to set.
     */
    public void setIndexTile(String indexTile) {
        this.indexTile = indexTile;
    }

    /** Returns the toLowercase.
     * @return the toLowercase.
     */
    public boolean isToLowercase() {
        return toLowercase;
    }

    /** Sets the toLowercase to the specified value.
     * @param toLowercase toLowercase to set.
     */
    public void setToLowercase(boolean toLowercase) {
        this.toLowercase = toLowercase;
    }

    /** Returns the stripAfterLastDot.
     * @return the stripAfterLastDot.
     */
    public boolean isStripAfterLastDot() {
        return stripAfterLastDot;
    }

    /** Sets the stripAfterLastDot to the specified value.
     * @param stripAfterLastDot stripAfterLastDot to set.
     */
    public void setStripAfterLastDot(boolean stripAfterLastDot) {
        this.stripAfterLastDot = stripAfterLastDot;
    }

    /** Returns the insertStartingDot.
     * @return the insertStartingDot.
     */
    public boolean isInsertStartingDot() {
        return insertStartingDot;
    }

    /** Sets the insertStartingDot to the specified value.
     * @param insertStartingDot insertStartingDot to set.
     */
    public void setInsertStartingDot(boolean insertStartingDot) {
        this.insertStartingDot = insertStartingDot;
    }
}

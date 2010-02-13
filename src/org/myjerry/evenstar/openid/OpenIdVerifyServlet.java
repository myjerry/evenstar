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
package org.myjerry.evenstar.openid;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.util.ajax.JSON;
import org.myjerry.evenstar.model.EvenstarUser;
import org.myjerry.evenstar.service.UserService;

import com.dyuproject.openid.Constants;
import com.dyuproject.openid.OpenIdUser;
import com.dyuproject.openid.RelyingParty;
import com.dyuproject.openid.ext.AxSchemaExtension;
import com.dyuproject.util.http.UrlEncodedParameterMap;

@SuppressWarnings("serial")
public class OpenIdVerifyServlet extends HttpServlet {
	
	private UserService userService;
		
	private static final AxSchemaExtension schemaExtension = 
			new AxSchemaExtension().addExchange("email", "http://axschema.org/contact/email")
									.addExchange("country", "http://axschema.org/contact/country/home")
									.addExchange("language", "http://axschema.org/pref/language")
									.addExchange("fullname", "http://axschema.org/namePerson")
									.addExchange("nickname", "http://axschema.org/namePerson/friendly")
									.addExchange("webpage", "http://axschema.org/contact/web/default")
									.addExchange("image", "http://axschema.org/media/image/default");

	RelyingParty _relyingParty = RelyingParty.getInstance().addListener(schemaExtension).addListener(new RelyingParty.Listener() {
		
		public void onAccess(OpenIdUser user, HttpServletRequest request) {
		}

		public void onAuthenticate(OpenIdUser user, HttpServletRequest request) {
		}

		public void onDiscovery(OpenIdUser user, HttpServletRequest request) {
		}

		public void onPreAuthenticate(OpenIdUser user, HttpServletRequest request, UrlEncodedParameterMap params) {
			String returnTo = params.get(Constants.OPENID_TRUST_ROOT) + request.getContextPath() + "/openid.html";
			params.put(Constants.OPENID_RETURN_TO, returnTo);
			params.put(Constants.OPENID_REALM, returnTo);
			params.put("openid.ns.ui", "http://specs.openid.net/extensions/ui/1.0");
			params.put("openid.ui.mode", "popup");
		}
	});

	public void init(ServletConfig config) throws ServletException {
		Object object = config.getServletContext().getAttribute("userService");
        if(object instanceof UserService) {
            this.userService = (UserService) object;
        }
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if ("true".equals(request.getParameter("logout"))) {
			_relyingParty.invalidate(request, response);
			response.setStatus(200);
			OpenID.removeUser(request);
			return;
		}

		try {
			OpenIdUser user = _relyingParty.discover(request);
			if (user != null) {
				if (user.isAuthenticated() || (user.isAssociated() && RelyingParty.isAuthResponse(request) && _relyingParty.verifyAuth(user, request, response))) {
					OpenID.setUser(request, user);

					// create the user in the database if necessary
					String email = OpenID.getUserEmail(user);
					EvenstarUser evenstarUser = new EvenstarUser();
					evenstarUser.setEmail(email);
					evenstarUser.setUserName(OpenID.getName(user));
					this.userService.addEvenstarUser(evenstarUser);
					
					response.setContentType("text/json");
					response.getWriter().write(JSON.toString(user));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setStatus(401);
	}

}

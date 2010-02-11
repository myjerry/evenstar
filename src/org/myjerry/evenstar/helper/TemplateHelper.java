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
package org.myjerry.evenstar.helper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.myjerry.evenstar.service.BlogLayoutService;
import org.myjerry.util.StringUtils;

public class TemplateHelper {
	
	public static String generateBlogPage(Long blogID, Map<String, Object> model, BlogLayoutService blogLayoutService, VelocityEngine velocityEngine) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
		String template = blogLayoutService.getBlogTemplate(blogID);
		if(StringUtils.isEmpty(template)) {
			template = blogLayoutService.getDefaultBlogTemplate();
		}

		StringWriter result = new StringWriter();
		velocityEngine.evaluate(new VelocityContext(model), result, "log string", template);
		String generatedBlogPage = result.toString();
		
		return generatedBlogPage;
	}

}

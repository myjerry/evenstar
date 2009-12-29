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

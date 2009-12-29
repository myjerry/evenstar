package org.myjerry.web.taglibs.core;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

public class RadioTag extends SimpleTagSupport {
	
	private String value;
	
	private String name;
	
	private String selectedValue;
	
	private Boolean onDefault;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		out.write("<input type=\"radio\" value=\"" + value + "\" name=\"" + name + "\" ");
		if(StringUtils.isNotBlank(selectedValue)) {
			if(selectedValue.equals(value)) {
				out.write("checked=\"checked\" ");
			}
		} else {
			if(onDefault != null && onDefault) {
				out.write("checked=\"checked\" ");
			}
		}
		out.write(" />");
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the selectedValue
	 */
	public String getSelectedValue() {
		return selectedValue;
	}

	/**
	 * @param selectedValue the selectedValue to set
	 */
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	/**
	 * @return the onDefault
	 */
	public Boolean getOnDefault() {
		return onDefault;
	}

	/**
	 * @param onDefault the onDefault to set
	 */
	public void setOnDefault(Boolean onDefault) {
		this.onDefault = onDefault;
	}

}

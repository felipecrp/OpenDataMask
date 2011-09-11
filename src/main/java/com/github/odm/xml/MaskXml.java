package com.github.odm.xml;

import java.util.Properties;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("mask")
public class MaskXml {
	@XStreamAlias("class")
	@XStreamAsAttribute
	private String classname;

	private Properties params;

	public MaskXml() {
		this.params = new Properties();
	}

	public MaskXml(String classname) {
		this();

		this.classname = classname;
	}

	public MaskXml(String classname, Properties params) {
		this(classname);

		this.params = params;
	}

	public String getClassname() {
		return classname;
	}

	public Properties getParams() {
		return params;
	}
}

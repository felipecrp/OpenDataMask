package com.github.odm.xml;

import java.util.ArrayList;
import java.util.List;
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

	@XStreamImplicit
	private List<ColumnXml> columns;
	
	private Properties params;

	public MaskXml(String classname, ColumnXml... columns) {
		this.params = new Properties();
		this.columns = new ArrayList<ColumnXml>();
		
		for (ColumnXml columnXml : columns) {
			this.columns.add(columnXml);
		}
		
		this.classname = classname;
	}

	public MaskXml(String classname, Properties params, ColumnXml... columns) {
		this(classname, columns);

		this.params = params;
	}

	public String getClassname() {
		return classname;
	}

	public Properties getParams() {
		return params;
	}
	
	public List<ColumnXml> getColumns() {
		return columns;
	}
}

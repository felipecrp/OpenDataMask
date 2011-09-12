package com.github.odm.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("column")
public class ColumnXml {
	@XStreamAsAttribute
	private String name;

	public ColumnXml(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}

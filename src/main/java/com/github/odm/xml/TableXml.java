package com.github.odm.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("table")
public class TableXml {
	@XStreamAsAttribute
	private String name;

	@XStreamImplicit
	private List<ColumnXml> columns;

	public TableXml() {
		this.columns = new ArrayList<ColumnXml>();
	}

	public TableXml(String name, ColumnXml... columns) {
		this();

		this.name = name;
		for (ColumnXml columnXml : columns) {
			this.columns.add(columnXml);
		}
	}

	public String getName() {
		return name;
	}

	public List<ColumnXml> getColumns() {
		return columns;
	}
}

package com.github.odm.xml;

import java.util.ArrayList;
import java.util.List;

import com.github.odm.model.Table;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("schema")
public class SchemaXml {
	@XStreamImplicit
	private List<TableXml> tables;

	public SchemaXml() {
		this.tables = new ArrayList<TableXml>();
	}

	private Object readResolve() {
		if (tables == null) {
			tables = new ArrayList<TableXml>();
		}
		return this;
	}

	public SchemaXml(TableXml... tables) {
		this();

		for (TableXml tableXml : tables) {
			this.tables.add(tableXml);
		}
	}

	public List<TableXml> getTables() {
		return tables;
	}
}

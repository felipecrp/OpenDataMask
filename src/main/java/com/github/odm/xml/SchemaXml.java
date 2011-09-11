package com.github.odm.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("schema")
public class SchemaXml {
	@XStreamImplicit
	private List<TableXml> tables;

	public SchemaXml() {
		this.tables = new ArrayList<TableXml>();
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

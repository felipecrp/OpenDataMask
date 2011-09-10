package com.github.odm.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * <p>
 * Object representation of a database or schema
 * </p>
 * 
 * @author felipecrp
 * 
 */
@XStreamAlias("schema")
public class Schema {
	@XStreamImplicit
	private List<Table> tables;

	public Schema() {
		this.tables = new ArrayList<Table>();
	}

	public Table getTable(String tableName) {
		if (tableName == null) {
			return null;
		}

		for (Table table : tables) {
			if (tableName.equalsIgnoreCase(table.getName())) {
				return table;
			}
		}

		Table table = new Table(this, tableName);
		tables.add(table);
		return table;
	}
	
	public List<Table> getTables() {
		return new ArrayList<Table>(tables);
	}

}

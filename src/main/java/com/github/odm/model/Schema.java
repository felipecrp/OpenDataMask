package com.github.odm.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Object representation of a database or schema
 * </p>
 * 
 * @author felipecrp
 * 
 */
public class Schema {
	private Map<String, Table> tables;

	public Schema() {
		this.tables = new HashMap<String, Table>();
	}

	public Collection<Table> getTables() {
		return tables.values();
	}

	public Table getTable(String tableName) {
		Table table = null;

		if (tables.containsKey(tableName)) {
			table = tables.get(tableName);
		} else {
			table = new Table(this, tableName);
			tables.put(tableName, table);
		}

		return table;
	}

}

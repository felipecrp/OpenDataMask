package com.github.odm.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

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
	private Map<String, Table> tablesMap;
	
	

	public Schema() {
		this.tablesMap = new HashMap<String, Table>();
	}

	public Collection<Table> getTables() {
		return tablesMap.values();
	}

	public Table getTable(String tableName) {
		Table table = null;

		if (tablesMap.containsKey(tableName)) {
			table = tablesMap.get(tableName);
		} else {
			table = new Table(this, tableName);
			tablesMap.put(tableName, table);
		}

		return table;
	}

}

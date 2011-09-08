package com.github.odm.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Object representation of a table
 * </p>
 * 
 * @author felipecrp
 * 
 */
public class Table {
	private String name;
	private Map<String, Column> columns;
	private Map<String, List<ForeignKey>> foreignKeys;
	private List<Column> primaryKey;
	private Schema schema;

	private boolean empty;

	public Table(Schema schema, String name) {
		if (schema == null || name == null) {
			throw new IllegalArgumentException(
					"Table must have a not null schema and name");
		}

		this.schema = schema;
		this.name = name;
		this.columns = new HashMap<String, Column>();
		this.primaryKey = new ArrayList<Column>();
		this.foreignKeys = new HashMap<String, List<ForeignKey>>();
		this.empty = false;
	}

	public String getName() {
		return name;
	}

	public Schema getSchema() {
		return schema;
	}

	public Collection<Column> getColumns() {
		return columns.values();
	}

	public Column getColumn(String columnName) {
		Column column = null;

		if (columns.containsKey(columnName)) {
			column = columns.get(columnName);
		} else {
			column = new Column(this, columnName);
			columns.put(columnName, column);
		}

		return column;
	}

	public void addPrimaryColumn(Column column) {
		primaryKey.add(column);
	}

	public void addForeignKey(String foreignKeyName, Column primaryKeyColumn,
			Column foreignKeyColumn) {
		List<ForeignKey> key = null;

		if (foreignKeys.containsKey(foreignKeyName)) {
			key = foreignKeys.get(foreignKeyName);
		} else {
			key = new ArrayList<ForeignKey>();
			foreignKeys.put(foreignKeyName, key);
		}

		key.add(new ForeignKey(primaryKeyColumn, foreignKeyColumn));
	}

	@Override
	public String toString() {
		String toString = "{name: " + name + ", columns: [";

		boolean isFirst = true;
		for (Column column : getColumns()) {
			if (isFirst) {
				isFirst = false;
			} else {
				toString += ", ";
			}

			toString += column;
		}

		toString += ", primaryKey: ";

		toString += primaryKey;

		toString += ", foreignKey: ";

		toString += foreignKeys;

		toString += "}";

		return toString;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}
	
	public Collection<List<ForeignKey>> getForeignKeys() {
		return foreignKeys.values();
	}
	
	public List<Column> getPrimaryKey() {
		return primaryKey;
	}
}

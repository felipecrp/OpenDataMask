package com.github.odm.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

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

	private List<Column> columns;

	private Schema schema;

	private Map<String, List<ForeignKey>> foreignKeys;

	private List<Column> primaryKey;

	@Deprecated
	private boolean empty;

	public Table(Schema schema, String name) {
		if (schema == null || name == null) {
			throw new IllegalArgumentException(
					"Table must have a not null schema and name");
		}

		this.schema = schema;
		this.name = name;
		this.columns = new ArrayList<Column>();
		this.primaryKey = new ArrayList<Column>();
		this.foreignKeys = new HashMap<String, List<ForeignKey>>();
		this.empty = false;
	}

	public void readResolve() {
		this.columns = new ArrayList<Column>();
		this.primaryKey = new ArrayList<Column>();
		this.foreignKeys = new HashMap<String, List<ForeignKey>>();
	}

	public String getName() {
		return name;
	}

	public Schema getSchema() {
		return schema;
	}

	public List<Column> getColumns() {
		return new ArrayList<Column>(columns);
	}

	public Column getColumn(String columnName) {
		if (columnName == null) {
			return null;
		}

		for (Column column : columns) {
			if (columnName.equalsIgnoreCase(column.getName())) {
				return column;
			}
		}

		return null;
	}

	public Column createColumn(String columnName) {
		Column column = new Column(this, columnName);
		columns.add(column);
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

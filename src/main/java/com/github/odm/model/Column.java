package com.github.odm.model;

import java.util.ArrayList;
import java.util.List;

import com.github.odm.mask.Mask;

/**
 * <p>
 * Object representation of a table column
 * </p>
 * 
 * @author felipecrp
 * 
 */
public class Column {
	private String name;

	private Table table;

	private int type;

	private boolean isNullable;

	private List<ForeignKey> foreignKeys;

	public Column(Table table, String name) {
		this.name = name;
		this.isNullable = true;
		this.table = table;
		this.foreignKeys = new ArrayList<ForeignKey>();
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "{name: " + name + "}";
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setNullable(int nullable) {
		if (nullable != 0) {
			isNullable = true;
		} else {
			isNullable = false;
		}
	}

	public boolean isNullable() {
		return isNullable;
	}

	public Table getTable() {
		return table;
	}

	public void addForeignKey(ForeignKey foreignKey) {
		foreignKeys.add(foreignKey);
	}

	public List<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

}

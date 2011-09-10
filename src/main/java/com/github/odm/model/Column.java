package com.github.odm.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.odm.mask.Mask;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * <p>
 * Object representation of a table column
 * </p>
 * 
 * @author felipecrp
 * 
 */
@XStreamAlias("column")
public class Column {
	@XStreamAsAttribute
	private String name;

	@XStreamOmitField
	private Table table;

	@XStreamOmitField
	private int type;

	private List<Mask> masks;

	@XStreamOmitField
	private boolean isNullable;

	@XStreamOmitField
	private List<ForeignKey> foreignKeys;

	public Column(Table table, String name) {
		this.masks = new ArrayList<Mask>();
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

	public void addMask(Mask mask) {
		//if (mask != null && mask.canMask(this)) {
			masks.add(mask);
	//	}
	}

	public List<Mask> getMasks() {
		return masks;
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

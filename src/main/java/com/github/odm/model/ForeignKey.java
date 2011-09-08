package com.github.odm.model;


/**
 * <p>
 * Object representation of a foreign key
 * </p>
 * 
 * @author felipecrp
 * 
 */
public class ForeignKey {
	private Column primaryKeyColumn;
	private Column foreignKeyColumn;

	public ForeignKey(Column primaryKeyColumn, Column foreignKeyColumn) {
		super();
		
		primaryKeyColumn.addForeignKey(this);
		
		this.primaryKeyColumn = primaryKeyColumn;
		this.foreignKeyColumn = foreignKeyColumn;
	}

	public Column getForeignKeyColumn() {
		return foreignKeyColumn;
	}

	public Column getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}
	
	@Override
	public String toString() {
		return "fk: "+ foreignKeyColumn + ", pk: " + primaryKeyColumn + ", table: " + primaryKeyColumn.getTable().getName(); 
	}
}

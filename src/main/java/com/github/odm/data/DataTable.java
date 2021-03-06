package com.github.odm.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.github.odm.mask.Mask;
import com.github.odm.model.Column;
import com.github.odm.model.Table;

/**
 * <p>
 * A data structure for table data
 * </p>
 * 
 * @author felipecrp
 * 
 */
public class DataTable {
	private Table table;
	private Map<String, Object> row;

	public DataTable(Table table) {
		this.table = table;
	}

	public void fetch(ResultSet result) throws SQLException {
		row = new HashMap<String, Object>();

		for (Column column : table.getColumns()) {
			row.put(column.getName(), result.getObject(column.getName()));
		}
	}

	public void mask() {
		for (Mask mask : table.getMasks()) {
			mask.mask(row);
		}
	}

	public Table getTable() {
		return table;
	}

	public Map<String, Object> getRow() {
		return row;
	}

}

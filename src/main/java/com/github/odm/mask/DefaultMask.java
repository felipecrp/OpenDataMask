package com.github.odm.mask;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.github.odm.model.Column;

public abstract class DefaultMask implements Mask {
	private Properties params;
	private List<Column> columns;

	public DefaultMask() {
		columns = new ArrayList<Column>();
		params = new Properties();
	}

	public void config(Properties params) {
		if (params != null) {
			this.params = params;
		}
	}

	public void addColumn(Column column) {
		if (column != null) {
			this.columns.add(column);
		}
	}

	protected List<Column> getColumns() {
		return columns;
	}

	protected Properties getParams() {
		return params;
	}
}

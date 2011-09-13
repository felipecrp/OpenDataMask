package com.github.odm.mask;

import java.util.Map;

import com.github.odm.model.Column;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Set column value to null
 * 
 * @author felipecrp
 */
@XStreamAlias("nullable")
public class NullableMask extends DefaultMask {

	public boolean canMask(Column column) {
		if (column.isNullable()) {
			return true;
		} else {
			return false;
		}
	}

	public void mask(Map<String, Object> row) {
		for (Column column : getColumns()) {
			if (row.containsKey(column.getName())) {
				row.put(column.getName(), null);
			}
		}
	}

}

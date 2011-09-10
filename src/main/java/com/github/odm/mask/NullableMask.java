package com.github.odm.mask;

import com.github.odm.model.Column;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Set column value to null
 * 
 * @author felipecrp
 */
@XStreamAlias("nullable")
public class NullableMask extends DefaultMask {
	public Object mask(Object obj) {
		return null;
	}

	public boolean canMask(Column column) {
		if (column.isNullable()) {
			return true;
		} else {
			return false;
		}
	}

}

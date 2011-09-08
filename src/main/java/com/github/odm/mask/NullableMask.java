package com.github.odm.mask;

import com.github.odm.model.Column;

/**
 * Set column value to null
 * 
 * @author felipecrp
 */
public class NullableMask implements Mask {

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

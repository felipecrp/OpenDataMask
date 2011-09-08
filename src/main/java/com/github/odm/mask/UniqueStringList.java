package com.github.odm.mask;

/**
 * <p>
 * Replace column value for one random string from a custom list
 * </p>
 * <p>
 * Unlike {@link StringList}, this mask doesn't repeat values.
 * </p>
 * 
 * @author felipecrp
 * 
 */
public class UniqueStringList extends StringList {

	@Override
	public Object mask(Object obj) {
		String string = (String) super.mask(null);
		getStrings().remove(string);

		return string;
	}
}

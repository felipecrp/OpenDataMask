package com.github.odm.mask;

import java.util.Map;
import java.util.Properties;

import com.github.odm.model.Column;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <p>
 * Mask a column data
 * </p>
 * 
 * @author felipecrp
 * 
 */
public interface Mask {
	/**
	 * <p>
	 * Mask an object
	 * </p>
	 * 
	 * @param row the table row to be masked
	 * @return the masked object
	 */
	void mask(Map<String, Object> row);


	/**
	 * <p>
	 * Check if the mask can be applied to the column
	 * </p>
	 * 
	 * @param column
	 *            the column
	 * @return true if the column can be masked
	 */
	boolean canMask(Column column);
	
	void config(Properties params);
	
	void addColumn(Column column);

}

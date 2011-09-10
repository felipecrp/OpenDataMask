package com.github.odm.mask;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.odm.model.Column;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * <p>
 * Replace column value for one random string from a custom list
 * </p>
 * 
 * @author felipecrp
 * 
 */
@XStreamAlias("stringList")
public class StringList extends DefaultMask {
	@XStreamImplicit
	private List<String> strings;
	
	@XStreamOmitField
	private Random random;

	public StringList() {
		this.strings = new ArrayList<String>();

		strings.add("AAAA");
		strings.add("BBBB");
		strings.add("CCCC");
		strings.add("DDDD");
		strings.add("EEEE");
		strings.add("FFFF");
		strings.add("GGGG");
		strings.add("HHHH");

		random = new Random();
	}

	public Object mask(Object obj) {
		return strings.get(random.nextInt(strings.size()));
	}

	protected Random getRandom() {
		return random;
	}

	protected List<String> getStrings() {
		return strings;
	}

	public boolean canMask(Column column) {
		if (column.getType() == Types.VARCHAR) {
			return true;
		} else {
			return false;
		}
	}

}

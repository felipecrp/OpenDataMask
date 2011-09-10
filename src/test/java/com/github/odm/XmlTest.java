package com.github.odm;

import org.junit.Test;

import com.github.odm.mask.Mask;
import com.github.odm.mask.NullableMask;
import com.github.odm.mask.StringList;
import com.github.odm.mask.UniqueStringList;
import com.github.odm.model.Column;
import com.github.odm.model.Schema;
import com.github.odm.model.Table;
import com.thoughtworks.xstream.XStream;

public class XmlTest {

	@Test
	public void test() {
		Schema schema = new Schema();
		Table a = schema.getTable("A");
		Table b = schema.getTable("B");

		Column a_a = a.getColumn("A");
		Column a_b = a.getColumn("B");

		Column b_a = b.getColumn("A");
		Column b_b = b.getColumn("B");

		a_a.addMask(new NullableMask());
		a_b.addMask(new StringList());

		b_b.addMask(new StringList());

		XStream stream = new XStream();
		stream.processAnnotations(new Class[] { Schema.class,
				NullableMask.class, StringList.class, UniqueStringList.class });
		System.out.println(stream.toXML(schema));
	}

}

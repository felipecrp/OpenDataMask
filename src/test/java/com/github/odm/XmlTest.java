package com.github.odm;

import org.junit.Test;

import com.github.odm.model.Schema;
import com.thoughtworks.xstream.XStream;

public class XmlTest {

	@Test
	public void test() {
		Schema schema = new Schema();
		schema.getTable("A");
		schema.getTable("B");
		
		XStream stream = new XStream();
		stream.processAnnotations(Schema.class);
		System.out.println(stream.toXML(schema));
	}

}

package com.github.odm;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Test;

import com.github.odm.mask.NullableMask;
import com.github.odm.mask.StringList;
import com.github.odm.xml.ColumnXml;
import com.github.odm.xml.MaskXml;
import com.github.odm.xml.SchemaXml;
import com.github.odm.xml.TableXml;
import com.thoughtworks.xstream.XStream;

public class XmlTest {

	@Test
	public void serialize() throws URISyntaxException, IOException {
		MaskXml mask1 = new MaskXml(NullableMask.class.getName());
		Properties mask2Prop = new Properties();
		mask2Prop.setProperty("list", "AAA,BBB,CCC,DDD,EEE");
		MaskXml mask2 = new MaskXml(StringList.class.getName(), mask2Prop);

		ColumnXml a_a = new ColumnXml("a", mask1);
		ColumnXml a_b = new ColumnXml("b");
		ColumnXml b_a = new ColumnXml("a");
		ColumnXml b_b = new ColumnXml("b", mask2);

		TableXml a = new TableXml("A", a_a, a_b);
		TableXml b = new TableXml("B", b_a, b_b);

		SchemaXml schema = new SchemaXml(a, b);

		XStream stream = new XStream();
		stream.autodetectAnnotations(true);
		System.out.println(stream.toXML(schema));

		File xml = new File(new URI(getClass().getClassLoader().getResource("xml-test.xml").toString()));
		byte[] buffer = new byte[(int) xml.length()];
		BufferedInputStream f = new BufferedInputStream(
				new FileInputStream(xml));
		f.read(buffer);
		String originalXmlString = new String(buffer);
		
		assertEquals(originalXmlString, stream.toXML(schema));
	}

	@Test
	public void deserialize() throws URISyntaxException {
		XStream stream = new XStream();
		stream.processAnnotations(SchemaXml.class);
		File xml = new File(new URI(getClass().getClassLoader().getResource("xml-test.xml").toString()));
		SchemaXml schema = (SchemaXml) stream.fromXML(xml);
		
		assertEquals(2, schema.getTables().size());		
		TableXml table = schema.getTables().get(0);
		assertEquals("A", table.getName());
		
		assertEquals(2, table.getColumns().size());	
		ColumnXml column = table.getColumns().get(0);
		assertEquals("a", column.getName());
		
		MaskXml mask = column.getMasks().get(0);
		assertEquals("com.github.odm.mask.NullableMask", mask.getClassname());
	}
}

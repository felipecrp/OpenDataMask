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

		ColumnXml a_a = new ColumnXml("a");
		ColumnXml a_b = new ColumnXml("b");
		MaskXml mask1 = new MaskXml(NullableMask.class.getName(), a_a, a_b);

		ColumnXml b_a = new ColumnXml("c");
		Properties mask2Prop = new Properties();
		mask2Prop.setProperty("list", "AAA,BBB,CCC,DDD,EEE");
		MaskXml mask2 = new MaskXml(StringList.class.getName(), mask2Prop, b_a);

		TableXml a = new TableXml("A", mask1);
		TableXml b = new TableXml("B", mask2);

		SchemaXml schema = new SchemaXml(a, b);

		XStream stream = new XStream();
		stream.autodetectAnnotations(true);
		System.out.println(stream.toXML(schema));

		File xml = new File(new URI(getClass().getClassLoader()
				.getResource("xml-test.xml").toString()));
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
		File xml = new File(new URI(getClass().getClassLoader()
				.getResource("xml-test.xml").toString()));
		SchemaXml schema = (SchemaXml) stream.fromXML(xml);

		assertEquals(2, schema.getTables().size());
		TableXml table = schema.getTables().get(0);
		assertEquals("A", table.getName());

		assertEquals(1, table.getMasks().size());
		MaskXml mask = table.getMasks().get(0);
		assertEquals("com.github.odm.mask.NullableMask", mask.getClassname());

		assertEquals(2, mask.getColumns().size());
	}
}

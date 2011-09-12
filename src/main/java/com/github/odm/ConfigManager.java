package com.github.odm;

import java.io.File;

import com.github.odm.model.Schema;
import com.github.odm.xml.SchemaXml;
import com.thoughtworks.xstream.XStream;

/**
 * Manage and apply configuration to a schema
 * 
 * @author felipecrp
 * 
 */
public class ConfigManager {

	/**
	 * The XML configuration
	 */
	private File config;

	public ConfigManager(File config) {
		this.config = config;
	}

	/**
	 * Apply configuration to the schema
	 * 
	 * @param schema
	 *            the schema that will be used
	 */
	public void apply(Schema schema) {
		if (schema != null) {
			SchemaXml schemaXml = loadXml();
		}
	}

	/**
	 * Load SchemaXml from xml
	 * 
	 * @return the schemaXml
	 */
	private SchemaXml loadXml() {
		XStream configLoader = new XStream();
		configLoader.processAnnotations(SchemaXml.class);
		SchemaXml schemaXml = (SchemaXml) configLoader.fromXML(config);

		return schemaXml;
	}
}

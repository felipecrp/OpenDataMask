package com.github.odm;

import java.io.File;

import com.github.odm.exception.ConfigException;
import com.github.odm.mask.Mask;
import com.github.odm.model.Column;
import com.github.odm.model.Schema;
import com.github.odm.model.Table;
import com.github.odm.xml.ColumnXml;
import com.github.odm.xml.MaskXml;
import com.github.odm.xml.SchemaXml;
import com.github.odm.xml.TableXml;
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
	 * @throws ConfigException
	 */
	public void apply(Schema schema) throws ConfigException {
		try {
			if (schema != null) {
				SchemaXml schemaXml = loadXml();

				for (TableXml tableXml : schemaXml.getTables()) {
					Table table = schema.getTable(tableXml.getName());
					if (table != null) {
						for (MaskXml maskXml : tableXml.getMasks()) {
							String classname = maskXml.getClassname();
							Mask mask = (Mask) Class.forName(classname)
									.newInstance();
							mask.config(maskXml.getParams());
							boolean canApply = true;
							for (ColumnXml columnXml : maskXml.getColumns()) {
								Column column = table.getColumn(columnXml
										.getName());

								if (column != null) {
									if (mask.canMask(column)) {
										mask.addColumn(column);
									} else {
										canApply = false;
										// log warn
									}
								} else {
									canApply = false;
									// log warn
								}
							}

							if (canApply) {
								table.addMask(mask);
							}
						}

					} else {
						// log warn
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new ConfigException(e);
		} catch (InstantiationException e) {
			throw new ConfigException(e);
		} catch (ClassNotFoundException e) {
			throw new ConfigException(e);
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

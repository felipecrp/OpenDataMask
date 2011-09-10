package com.github.odm;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.github.odm.data.DataTable;
import com.github.odm.mask.NullableMask;
import com.github.odm.mask.StringList;
import com.github.odm.mask.UniqueStringList;
import com.github.odm.model.Column;
import com.github.odm.model.Schema;
import com.github.odm.model.Table;
import com.github.odm.util.QueryUtil;
import com.thoughtworks.xstream.XStream;

public class ODM {

	public static void main(String[] args) {
		try {
			new ODM().run(args);
		} catch (ParseException e) {
			HelpFormatter help = new HelpFormatter();

			help.printHelp("java -jar odm.jar", getCommandOptions(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run(String[] args) throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, ParseException {

		Options options = getCommandOptions();

		PosixParser parser = new PosixParser();
		CommandLine cmd = parser.parse(options, args);

		XStream configLoader = new XStream();
		configLoader.autodetectAnnotations(true);
		configLoader.processAnnotations(new Class[] { Schema.class,
				NullableMask.class, StringList.class, UniqueStringList.class });
		Schema schema = (Schema) configLoader.fromXML(new File(cmd
				.getOptionValue("c")));

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection orignConn = DriverManager.getConnection(
				"jdbc:mysql://localhost/test", "test", "test");

		Connection destConn = DriverManager.getConnection(
				"jdbc:mysql://localhost/test2", "test", "test");

		loadSchema(orignConn, schema);
		loadMask(schema);

		for (Table table : schema.getTables()) {
			QueryUtil.delete(destConn, table);
		}

		for (Table table : schema.getTables()) {

			ResultSet result = QueryUtil.select(orignConn, table);
			while (result.next()) {
				DataTable data = new DataTable(table);
				data.fetch(result);
				data.mask();

				QueryUtil.insert(destConn, data);
			}
		}

		orignConn.close();
		destConn.close();

	}

	/**
	 * <p>
	 * Get the command line options
	 * </p>
	 * 
	 * @return the command line options
	 */
	private static Options getCommandOptions() {
		Options options = new Options();

		Option xmlOpt = new Option("c", "xml configuration file");
		xmlOpt.setRequired(true);
		xmlOpt.setArgs(1);

		options.addOption(xmlOpt);

		return options;
	}

	private void loadMask(Schema schema) {
		Table table = schema.getTable("Empresa");
		Column column = table.getColumn("nome");
		column.addMask(new UniqueStringList());
	}

	private void loadSchema(Connection conn, Schema schema) throws SQLException {
		DatabaseMetaData metadata = conn.getMetaData();
		ResultSet result = metadata.getTables(null, null, "%",
				new String[] { "TABLE" });

		while (result.next()) {
			String tableName = result.getString("TABLE_NAME");
			Table table = schema.getTable(tableName);

			PreparedStatement statement = conn
					.prepareStatement("select * from " + tableName);
			statement.setFetchSize(1);
			ResultSet tableResult = statement.executeQuery();

			ResultSetMetaData tableMetaData = tableResult.getMetaData();
			for (int i = 1; i <= tableMetaData.getColumnCount(); i++) {
				String columnName = tableMetaData.getColumnName(i);
				int type = tableMetaData.getColumnType(i);
				int nullable = tableMetaData.isNullable(i);

				Column column = table.getColumn(columnName);
				column.setType(type);
				column.setNullable(nullable);
			}

			ResultSet primaryKeyResult = metadata.getPrimaryKeys(null, null,
					tableName);
			while (primaryKeyResult.next()) {
				String primaryKeyColumnName = primaryKeyResult
						.getString("COLUMN_NAME");
				Column primaryKeyColumn = table.getColumn(primaryKeyColumnName);
				table.addPrimaryColumn(primaryKeyColumn);
			}

			ResultSet foreignKeyResult = metadata.getExportedKeys(null, null,
					tableName);
			while (foreignKeyResult.next()) {
				String foreignKeyName = foreignKeyResult.getString("FK_NAME");
				String foreingTableName = foreignKeyResult
						.getString("FKTABLE_NAME");
				Table foreingTable = schema.getTable(foreingTableName);

				String foreingColumnName = foreignKeyResult
						.getString("FKCOLUMN_NAME");
				Column foreingColumn = foreingTable
						.getColumn(foreingColumnName);

				String primaryColumnName = foreignKeyResult
						.getString("PKCOLUMN_NAME");
				Column primaryColumn = table.getColumn(primaryColumnName);

				foreingTable.addForeignKey(foreignKeyName, primaryColumn,
						foreingColumn);
			}

		}
	}

}

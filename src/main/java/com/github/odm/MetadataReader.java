package com.github.odm;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.github.odm.model.Column;
import com.github.odm.model.Schema;
import com.github.odm.model.Table;

public class MetadataReader {

	private Connection conn;
	private Schema schema;

	public MetadataReader(Connection conn) {
		this.conn = conn;
		this.schema = new Schema();
	}

	public Schema buildSchema() throws SQLException {
		readColumns();
		readPrimaryKeys();
		readConstraints();
		return schema;
	}

	protected void readColumns() throws SQLException {
		DatabaseMetaData metadata = conn.getMetaData();
		ResultSet result = metadata.getTables(null, null, "%",
				new String[] { "TABLE" });

		while (result.next()) {
			String tableName = result.getString("TABLE_NAME");
			Table table = schema.createTable(tableName);

			PreparedStatement statement = conn
					.prepareStatement("select * from " + tableName);
			statement.setFetchSize(1);
			ResultSet tableResult = statement.executeQuery();

			ResultSetMetaData tableMetaData = tableResult.getMetaData();
			for (int i = 1; i <= tableMetaData.getColumnCount(); i++) {
				String columnName = tableMetaData.getColumnName(i);
				int type = tableMetaData.getColumnType(i);
				int nullable = tableMetaData.isNullable(i);

				Column column = table.createColumn(columnName);
				column.setType(type);
				column.setNullable(nullable);
			}
		}
	}

	protected void readConstraints() {
		// TODO Auto-generated method stub
	}

	protected void readPrimaryKeys() {
		// TODO Auto-generated method stub
	}
	
	protected void readForeignKeys() {
		// TODO Auto-generated method stub
	}
	
	@SuppressWarnings("unused")
	@Deprecated
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

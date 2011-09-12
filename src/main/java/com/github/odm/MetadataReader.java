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
}

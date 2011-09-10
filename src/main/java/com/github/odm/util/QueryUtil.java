package com.github.odm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import com.github.odm.data.DataTable;
import com.github.odm.model.Column;
import com.github.odm.model.ForeignKey;
import com.github.odm.model.Table;

public class QueryUtil {

	// TODO avoid loops
	public static void delete(Connection conn, Table table) throws SQLException {
		if (!table.isEmpty()) {
			Column pk = table.getPrimaryKey().get(0);

			for (ForeignKey fk : pk.getForeignKeys()) {
				delete(conn, fk.getForeignKeyColumn().getTable());
			}

			PreparedStatement delete = conn.prepareStatement("DELETE FROM "
					+ table.getName());
			boolean sucess = delete.execute();
			if (sucess) {
				table.setEmpty(true);
			}
		}

	}

	public static ResultSet select(Connection conn, Table table)
			throws SQLException {
		String select = "SELECT ";
		boolean isFirst = true;
		for (Column column : table.getColumns()) {
			if (isFirst) {
				isFirst = false;
			} else {
				select += ", ";
			}

			select += column.getName();
		}
		select += " FROM " + table.getName();

		PreparedStatement statement = conn.prepareStatement(select);
		ResultSet result = statement.executeQuery();
		return result;
	}

	public static void insert(Connection conn, DataTable data)
			throws SQLException {
		Table table = data.getTable();
		Map<String, Object> row = data.getRow();

		String insert = "INSERT INTO " + table.getName() + " (";
		boolean isFirst = true;
		for (Column column : table.getColumns()) {
			if (isFirst) {
				isFirst = false;
			} else {
				insert += ", ";
			}

			insert += column.getName();
		}

		insert += ") VALUES (";

		isFirst = true;
		for (Column column : table.getColumns()) {
			boolean useQuote = false;
			if (isFirst) {
				isFirst = false;
			} else {
				insert += ", ";
			}

			if (row.get(column.getName()) == null) {
				insert += "null";
			} else {
				switch (column.getType()) {
				case Types.VARCHAR:
					useQuote = true;
					break;

				default:
					break;
				}

				if (useQuote) {
					insert += "'";
				}

				insert += row.get(column.getName());

				if (useQuote) {
					insert += "'";
				}
			}
		}

		insert += ")";

		PreparedStatement statement = conn.prepareStatement(insert);
		// boolean sucess =
		statement.execute();

	}

}

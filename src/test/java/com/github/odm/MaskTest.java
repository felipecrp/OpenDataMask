package com.github.odm;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MaskTest {

	private Connection orignConn;
	private Connection destConn;

	@BeforeClass
	public static void setup() throws URISyntaxException {
		MaskTest test = new MaskTest();
		test.runScript("jdbc:hsqldb:mem:orign", "database.sql");
		test.runScript("jdbc:hsqldb:mem:orign", "data.sql");
		test.runScript("jdbc:hsqldb:mem:dest", "database.sql");
	}

	@Before
	public void connect() throws SQLException {
		orignConn = DriverManager.getConnection("jdbc:hsqldb:mem:orign", "SA",
				"");

		destConn = DriverManager
				.getConnection("jdbc:hsqldb:mem:dest", "SA", "");
	}

	@Test
	public void copy() throws SQLException, URISyntaxException, IOException {

		ODM odm = new ODM();
		File config = new File(new URI(getClass().getClassLoader()
				.getResource("copy.xml").toString()));
		odm.run(config, orignConn, destConn);

		PreparedStatement state = destConn
				.prepareStatement("select * from Student");
		ResultSet result = state.executeQuery();
		while (result.next()) {
			System.out.println(result.getString("id") + ":"
					+ result.getString("name"));
		}

	}

	@After
	public void disconnect() throws SQLException {
		orignConn.close();
		destConn.close();
	}

	//TODO make this static and load resource from static method
	private void runScript(String uri, String script) throws URISyntaxException {
		SQLExec sqlExec = new SQLExec();
		Project project = new Project();
		project.init();
		sqlExec.setProject(project);
		sqlExec.setTaskType("sql");
		sqlExec.setTaskName("sql");
		sqlExec.setDriver("org.hsqldb.jdbc.JDBCDriver");
		sqlExec.setUserid("SA");
		sqlExec.setPassword("");
		sqlExec.setUrl(uri);

		File databaseScript = new File(new URI(getClass().getClassLoader()
				.getResource(script).toString()));
		sqlExec.setSrc(databaseScript);
		sqlExec.execute();
	}

}

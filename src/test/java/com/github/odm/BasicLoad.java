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
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicLoad {

	@Test
	public void test() throws SQLException, URISyntaxException, IOException {
		
		runScript("jdbc:hsqldb:mem:orign", "database.sql");
		runScript("jdbc:hsqldb:mem:orign", "data.sql");
		Connection orignConn = DriverManager.getConnection("jdbc:hsqldb:mem:orign",
				"SA", "");
		
		runScript("jdbc:hsqldb:mem:dest", "database.sql");
		Connection destConn = DriverManager.getConnection("jdbc:hsqldb:mem:dest",
				"SA", "");
		
		ODM odm = new ODM();
		File config = new File(new URI(getClass().getClassLoader().getResource("sample.xml").toString()));
		odm.run(config, orignConn, destConn);
		
		
		PreparedStatement state = destConn.prepareStatement("select * from Student");
		ResultSet result = state.executeQuery();
		while(result.next()) {
			System.out.println(result.getString("id") + ":" + result.getString("name"));
		}
		
		orignConn.close();
		destConn.close();
	}

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
		File databaseScript = new File(new URI(getClass().getClassLoader().getResource(script).toString()));
		sqlExec.setSrc(databaseScript);
		sqlExec.execute();
	}

}

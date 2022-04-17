package ch.mobi.examples.testcontainers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class OracleJdbcDefaultSystemUserTest {

    // will be shared between test methods
    @Container
    private static final OracleContainer oracleAsSystem =
            new OracleContainer("diemobiliar/minimized-oraclexe-image:18.4.0-xe")
                    .withUsername("system")
                    .withPassword("oracle");

    @BeforeAll
    public static void printJdbcUrls() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("JDBC URL SYSTEM:       " + oracleAsSystem.getJdbcUrl());
        System.out.println("===========================================================");
    }

    @Test
    public void testDefaultSystemConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(oracleAsSystem.getJdbcUrl());
        Statement stmt = conn.createStatement();
        stmt.execute("select 1 from dual");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            int one = rs.getInt(1);
            assertEquals(1, one);
        }
    }

    @Test
    public void testDefaultSysAsSysdbaConnection() throws SQLException {
        String hostname = oracleAsSystem.getHost();
        Integer port = oracleAsSystem.getOraclePort();
        String jdbcUrl = "jdbc:oracle:thin:@" + hostname + ":" + port + ":xe";

        var datasource = new SimpleDriverDataSource();
        datasource.setUrl(jdbcUrl);
        datasource.setUsername("sys as sysdba");
        String password = "oracle";
        datasource.setPassword(password);
        datasource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);

        Connection conn = datasource.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("select 1 from dual");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            int one = rs.getInt(1);
            assertEquals(1, one);
        }
    }

}

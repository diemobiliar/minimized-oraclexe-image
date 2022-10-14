package ch.mobi.examples.testcontainers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class OracleJdbcCustomSystemUserTest {

    private static final String CUSTOM_SYSTEM_PWD = "junitpwd";

    // will be shared between test methods
    @Container
    private static final OracleContainer oracleAsSystemWithPwd =
            new OracleContainer("diemobiliar/minimized-oraclexe-image:18.4.0-xe")
                    .withEnv("ORACLE_PWD", CUSTOM_SYSTEM_PWD)
                    .withUsername("system")
                    .withPassword(CUSTOM_SYSTEM_PWD);

    @BeforeAll
    public static void printJdbcUrls() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("JDBC URL SYSTEM & PWD: " + oracleAsSystemWithPwd.getJdbcUrl());
        System.out.println("===========================================================");
    }

    @Test
    public void testCustomSystemConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(oracleAsSystemWithPwd.getJdbcUrl());
        Statement stmt = conn.createStatement();
        stmt.execute("select 2 from dual");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            int two = rs.getInt(1);
            assertEquals(2, two);
        }
    }

}

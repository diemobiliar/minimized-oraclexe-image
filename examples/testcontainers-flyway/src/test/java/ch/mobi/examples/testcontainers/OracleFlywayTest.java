package ch.mobi.examples.testcontainers;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.flywaydb.core.api.MigrationState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.flywaydb.core.api.MigrationState.SUCCESS;

@Testcontainers
public class OracleFlywayTest {

    // will be shared between test methods
    @Container
    private static final OracleContainer oracle =
            new OracleContainer("diemobiliar/minimized-oraclexe-image:18.4.0-xe")
                    .withUsername("AOO_TESTS")
                    .withPassword("AOO_TESTS");

    @BeforeAll
    public static void setupPostgreSQL() {
        System.out.println("-----------------------------------------------------------");
        System.out.println("Oracle JDBC URL: " + oracle.getJdbcUrl());
        System.out.println("===========================================================");
    }

    @Test
    public void testFlywayMigration() {
        Flyway flyway = Flyway.configure()
                .dataSource(oracle.getJdbcUrl(), oracle.getUsername(), oracle.getPassword())
                .load();

        flyway.migrate();

        MigrationInfoService info = flyway.info();

        assertThat(info.applied()).hasSize(1);
        assertThat(info.current().getState()).isEqualTo(SUCCESS);
        assertThat(info.current().getVersion().getVersion()).isEqualTo("1.00");
    }

}

package ru.edu.springdata.containers;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@DataJpaTest
@Sql("/books.sql")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class ContainerDatabaseAbstractTest {

    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:11")
            .withDatabaseName("springboot")
            .withPassword("springboot")
            .withUsername("springboot")
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("init-db.sql"),
                    "/docker-entrypoint-initdb.d/"
            );

    @DynamicPropertySource
    static void setDataSourceProperties(DynamicPropertyRegistry registry) {
        System.out.println(database.getJdbcUrl());
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        registry.add("spring.datasource.schema", String::new);
        registry.add("spring.datasource.data", String::new);
    }
}

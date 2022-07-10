package com.dislinkt.connectionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.neo4j.core.Neo4jTemplate;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class })
public class ConnectionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConnectionServiceApplication.class, args);
    }

}

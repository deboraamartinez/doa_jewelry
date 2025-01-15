package com.doa.doajewelry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Doajewelry Spring Boot application.
 * 
 * <p>
 * This class is responsible for bootstrapping the Spring Boot application.
 * It leverages Spring Boot's auto-configuration and component scanning features
 * to set up the application context and initialize all necessary components.
 * </p>
 */
@SpringBootApplication
public class DoajewelryApplication {

    /**
     * The main method serves as the entry point of the Java application.
     * 
     * <p>
     * It delegates the bootstrapping process to Spring Boot, which sets up the
     * default configuration, starts the embedded server, and performs classpath
     * scanning to discover and initialize beans and components.
     * </p>
     * 
     * @param args Command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        // Initiates the Spring Boot application by calling the run method.
        // This method sets up the Spring application context and starts the embedded server.
        SpringApplication.run(DoajewelryApplication.class, args);
    }

}

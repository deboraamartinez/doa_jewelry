package doa_jewelry;

import doa_jewelry.startup.StartupInitializer;

public class Application {
    public static void main(String[] args) {
        StartupInitializer initializer = new StartupInitializer();
        initializer.initializeData();

        System.out.println("DOA Jewelry Store system initialized.");
    }
}

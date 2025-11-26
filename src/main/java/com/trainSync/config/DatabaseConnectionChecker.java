package com.trainSync.config;

/**
 * Author: Sajal Gupta
 * Date: Nov 6, 2025
 */
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * @author sajalgupta
 */
@Component
public class DatabaseConnectionChecker {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void verifyConnection() {
        System.out.println("🔄 Testing connection to Supabase via Spring DataSource...");

        try (Connection conn = dataSource.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Successfully connected to Supabase (using pooled DataSource)!");
                System.out.println("🔍 Connection Info: " + conn.getMetaData().getURL());
            } else {
                System.out.println("❌ Connection test failed!");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error connecting to Supabase:");
            e.printStackTrace();
        }
    }

}

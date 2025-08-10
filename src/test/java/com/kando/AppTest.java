package com.kando;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Kando CLI App.
 */
public class AppTest {

    @Test
    @DisplayName("Application should initialize without errors")
    public void testAppInitialization() {
        // Basic test to ensure the app can be instantiated
        App app = new App();
        assertNotNull(app, "App instance should not be null");
    }

    @Test
    @DisplayName("Application should have main method")
    public void testMainMethod() {
        // Test that main method exists and can be called
        assertDoesNotThrow(() -> {
            App.main(new String[] { "--help" });
        }, "Main method should not throw exception with help flag");
    }
}

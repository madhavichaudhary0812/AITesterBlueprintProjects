package com.vwo.qa.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class TestConfig {
    private static final Properties DOTENV = loadDotenv();

    private TestConfig() {
    }

    public static String baseUrl() {
        return value("VWO_BASE_URL", "https://app.wingify.com");
    }

    public static String loginUrl() {
        return baseUrl().replaceAll("/+$", "") + "/#/login";
    }

    public static String email() {
        return value("VWO_EMAIL", "").trim();
    }

    public static String password() {
        return value("VWO_PASSWORD", "");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(value("VWO_HEADLESS", "false"));
    }

    public static boolean liveAuthTestsEnabled() {
        return Boolean.parseBoolean(value("VWO_ENABLE_LIVE_AUTH_TESTS", "false"));
    }

    private static String value(String key, String defaultValue) {
        String fromEnvironment = System.getenv(key);
        if (fromEnvironment != null && !fromEnvironment.isBlank()) {
            return fromEnvironment;
        }

        String fromDotenv = DOTENV.getProperty(key);
        return fromDotenv == null || fromDotenv.isBlank() ? defaultValue : fromDotenv;
    }

    private static Properties loadDotenv() {
        Properties properties = new Properties();
        Path[] candidates = {
            Path.of(".env"),
            Path.of("../PlaywrightFramework/.env")
        };

        for (Path candidate : candidates) {
            if (!Files.isRegularFile(candidate)) {
                continue;
            }
            try (Reader reader = Files.newBufferedReader(candidate)) {
                properties.load(reader);
                break;
            } catch (IOException exception) {
                throw new IllegalStateException(
                    "Could not read environment configuration at " + candidate,
                    exception
                );
            }
        }
        return properties;
    }
}

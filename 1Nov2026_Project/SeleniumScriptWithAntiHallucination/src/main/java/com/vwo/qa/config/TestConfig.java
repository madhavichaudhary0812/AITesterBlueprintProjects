package com.vwo.qa.config;

public final class TestConfig {
    private TestConfig() {
    }

    public static String baseUrl() {
        return value("VWO_BASE_URL", "https://app.wingify.com").replaceAll("/+$", "");
    }

    public static String loginUrl() {
        return baseUrl() + "/#/login";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(value("VWO_HEADLESS", "false"));
    }

    private static String value(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}

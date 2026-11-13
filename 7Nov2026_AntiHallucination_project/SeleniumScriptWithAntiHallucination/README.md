# VWO Login - Selenium Anti-Hallucination Suite

This Java Selenium/TestNG project uses Page Object Model, explicit waits, per-test browser lifecycle, and Allure attachments for failure screenshots. It covers the evidence-bounded AH-S01 through AH-S12 checks from `test-cases-antihallucination-stage-2.md`.

The suite uses the specified VWO login URL and supplied XPath locators. It does not submit the login form, use credentials, open external identity-provider flows, or assert Remember me checkbox state.

## Requirements

- Java 17 or later
- Maven 3.8 or later
- Google Chrome

## Run

```bash
mvn test
```

Set `VWO_HEADLESS=false` to run Chrome visibly. The suite defaults to headless mode.

The AH-S12 check verifies only that the configured URL string uses `https:`; it does not verify server-side HTTPS enforcement.

# VWO Login Selenium Framework

Java, Selenium WebDriver, TestNG, and Allure framework for the VWO login page at `https://app.wingify.com/#/login`.

## Requirements

- Java 17 or newer
- Maven 3.8 or newer
- Google Chrome

Selenium Manager resolves the matching Chrome driver automatically.

## Configure credentials

The UI-only tests use sample credentials and do not submit the form. The successful-login test needs an authorized VWO test account.

Copy `.env.example` to `.env` and set `VWO_EMAIL` and `VWO_PASSWORD`. The framework reads this file from `SeleniumFrameWork/.env`; it can also reuse `PlaywrightFramework/.env`. Operating-system environment variables take precedence. Never commit `.env` or paste credentials into source code.

Live authentication-failure tests are disabled by default. Only enable them for an approved test environment/account by setting `VWO_ENABLE_LIVE_AUTH_TESTS=true`; failed production login attempts can cause rate limiting or account lockout.

## Run the suite

From this directory:

```bash
mvn clean test
```

By default Chrome runs headlessly. To watch the browser:

```bash
VWO_HEADLESS=false mvn test
```

Run just the combined email-and-password entry test:

```bash
mvn -Dtest=LoginValidTest#userCanEnterEmailAndPasswordTogether test
```

Run the valid login test with credentials configured:

```bash
mvn -Dtest=LoginValidTest#validCredentialsRedirectToDashboard test
```

Run the Allure report:

```bash
mvn io.qameta.allure:allure-maven:2.15.0:report
open target/site/allure-maven-plugin/index.html
```

For CI or a different test environment, set `VWO_BASE_URL`. Browser screenshots on test failure are saved under `target/screenshots/`; Allure result files are saved under `target/allure-results/`.

Some scenarios remain skipped until their behavior is verified or credentials/test-environment safeguards are provided. A skipped test has not been validated.

# VWO Login Playwright Framework

TypeScript Playwright Test framework for the VWO login page at `https://app.wingify.com/#/login`. It uses the Page Object Model, XPath locators, requirement-tagged test cases, and Allure reporting.

## Requirements

- Node.js and npm
- Playwright-supported browsers (Chromium, Firefox, and WebKit)

## Install

Run these commands from `PlaywrightFramework/`:

```bash
npm ci
npx playwright install
```

## Configure test credentials

The UI tests that inspect the login form do not need an account. Successful login and some authentication scenarios require an authorized VWO test account.

Copy `.env.example` to `.env` and set credentials locally:

```bash
cp .env.example .env
```

```dotenv
VWO_EMAIL=your_authorized_test_account
VWO_PASSWORD=your_test_account_password
```

Never commit `.env` or share account passwords in source control or chat. `.env` is ignored by Git.

## Run tests

Run all configured browser projects:

```bash
npm test
```

Run valid-login or invalid-login specs:

```bash
npm run test:valid
npm run test:invalid
```

Run a specific test case, for example TC-P17, in a visible Chromium browser:

```bash
npm run test:headed -- --project=chromium --workers=1 --grep TC-P17
```

Run the valid-credentials dashboard check with an authorized account:

```bash
npm run test:headed -- --project=chromium --workers=1 --grep TC-P08
```

Run tests in Playwright debug mode:

```bash
npm run test:debug -- --project=chromium
```

The mobile responsive test is assigned to the `mobile-safari` project. Authentication tests that require credentials are skipped when the required environment variables are absent. Some negative, reset-password, SSO, and keyboard-submit scenarios remain skipped until their behavior is verified and they can be run safely with suitable test data. A skipped test has not been validated.

## Allure report

Generate and open the HTML report after running tests:

```bash
npm run allure:generate
npm run allure:open
```

Test results, traces, and reports are generated locally and excluded from Git.


RICE-POT Prompt — Playwright (TypeScript) Generic Template
How to use: Replace every {{PLACEHOLDER}} before sending this prompt. Delete any optional section that does not apply. Attach the requirement document (PRD, user story, or acceptance criteria) if you have one.

Placeholder	Example
{{APP_NAME}}	Salesforce, VWO, OrangeHRM
{{APP_URL}}	https://login.salesforce.com/?locale=in
{{MODULE}}	Login page, Lead creation, Checkout
{{PAGE_CLASS}}	LoginPage, LeadPage, CheckoutPage
{{REQUIREMENT_SOURCE}}	Attached PRD / user story / acceptance criteria / "None — use the Context section"
{{DOMAIN_EXPERTISE}}	Salesforce CRM, A/B testing platforms, e-commerce
{{POSITIVE_SCENARIOS}}	List of happy-path checks
{{NEGATIVE_SCENARIOS}}	List of failure-path checks
{{FEATURES_IN_SCOPE}}	Elements and flows on the page
{{USER_JOURNEY}}	Numbered steps a user takes
{{PROJECT_NAME}}	Salesforce-Playwright-Automation
R — ROLE
You are a Senior QA Automation Architect with 15+ years of experience in software testing, test automation, and enterprise-level automation framework development.

You are an expert in:

Playwright with TypeScript.
Enterprise-level UI automation.
Page Object Model (POM).
Playwright fixtures and reusable utilities.
Cross-browser testing.
Authentication and session management.
Functional, negative, regression, and accessibility testing.
Allure reporting.
CI/CD integration.
{{DOMAIN_EXPERTISE}}.
You understand real-world QA processes, test design techniques, defect reporting, test coverage, and automation best practices.

I — INSTRUCTIONS
Create a complete, production-ready Playwright automation framework using TypeScript for the {{APP_NAME}} {{MODULE}}.

Application URL: {{APP_URL}}

Primary objective: Automate and thoroughly validate the {{APP_NAME}} {{MODULE}} using {{REQUIREMENT_SOURCE}}.

1. Framework Requirements
[Critical] Use Playwright with TypeScript and the Playwright Test Runner.
[Mandatory] Follow the Page Object Model (POM) with reusable page classes and methods.
Implement Playwright fixtures wherever appropriate.
Use correct asynchronous programming with async/await.
[Critical] Implement structured exception handling.
Use web-first assertions (toBeVisible(), toHaveURL(), toHaveAttribute(), and so on).
Use condition-based waiting through Playwright's built-in auto-waiting and expect assertions.
[DoNOTuse] Thread.sleep, page.waitForTimeout(), or any other fixed wait.
Use environment variables for credentials and sensitive information.
Support Chromium, Firefox, and WebKit.
Implement correct browser and context lifecycle management.
2. Locator Strategy
[Mandatory] Use XPath locators only.
[Don't] Don't use CSS selectors, getByRole, getByText, getByLabel, getByPlaceholder, getByTestId, or any other non-XPath locator strategy.
Attributes such as @id, @name, or @type may be used inside an XPath expression, but never as a standalone locator strategy.
Use verified XPath expressions based on the actual application DOM.
Avoid brittle absolute XPath expressions; prefer stable, relative XPath expressions.
Avoid auto-generated or randomized attribute values (hashed class names, random data-qa values).
Validate locator uniqueness before interacting with elements; many apps keep hidden views in the DOM that reuse the same type or name.
Do not invent XPath attributes or UI elements.
3. Page Object Model
Create a reusable {{PAGE_CLASS}} class.

Mandatory implementation:

Use a dedicated Page Object file.
Encapsulate all page locators as readonly Locator fields.
Initialize the page and locators in the constructor.
Create one reusable action method per user action, plus methods that return UI state (messages, values) for the tests to assert.
Create a BasePage class with the shared page, navigation, and error-wrapping helpers. Page classes extend BasePage and do not redeclare page.
Keep page constants (paths, expected URLs) as readonly fields on the page class.
Expose locators as public readonly fields so tests can use web-first assertions on them directly (await expect(pageObject.field).toBeVisible()).
[Don't] Don't write isXxxVisible(): Promise<boolean> methods for tests to assert with expect(bool).toBe(true). That skips Playwright's auto-retrying assertions and makes tests flaky.
Implement appropriate exception handling.
Keep test assertions in the test files, not in the page object.
4. Test Coverage
Positive test scenarios {{POSITIVE_SCENARIOS}}

Negative test scenarios {{NEGATIVE_SCENARIOS}}

Additional scenarios (only if present in the actual application)

Keyboard navigation.
Responsive layout.
Loading states.
Accessibility attributes (labels, ARIA).
Do not assume that every listed scenario is available in the current application.

5. TestNG Equivalent
Playwright does not use TestNG annotations. Use the Playwright Test Runner equivalents:

TestNG	Playwright Test
@Test	test()
class / @Test(groups)	test.describe()
@BeforeMethod	test.beforeEach()
@AfterMethod	test.afterEach()
@BeforeClass / @BeforeTest	test.beforeAll()
@AfterClass / @AfterTest	test.afterAll()
Assert.*	expect()
Implement the setup and teardown logic these require.

6. Allure Reporting
[Mandatory] Integrate Allure reporting with Playwright:

Install and configure the allure-playwright reporter.
Generate individual test results with names and descriptions.
Include execution status and failure details.
Capture screenshots on failure.
Attach relevant logs and diagnostic information.
Generate an HTML Allure report.
7. Exception Handling
Implement structured error handling with try–catch blocks where appropriate. Handle:

Locator timeouts.
Navigation failures.
Authentication failures.
Unexpected application behavior.
Assertion failures.
Browser execution failures.
Do not silently suppress errors. Preserve the original error and add meaningful diagnostic information.

C — CONTEXT
The application under test is {{APP_NAME}}.

URL: {{APP_URL}}

Requirement source: {{REQUIREMENT_SOURCE}}

Features in scope
{{FEATURES_IN_SCOPE}}

Expected user journey
{{USER_JOURNEY}}

Only automate functionality that is accessible and verifiable in the UI. Do not claim that UI automation proves backend behavior such as encryption, compliance, or security hardening.

E — EXAMPLE
Example structure for the Playwright Page Object Model:

import { Page, Locator } from '@playwright/test';

export class {{PAGE_CLASS}} {
    readonly page: Page;
    readonly firstInput: Locator;
    readonly secondInput: Locator;
    readonly submitButton: Locator;

    constructor(page: Page) {
        this.page = page;
        this.firstInput = page.locator('//input[@id="first-field-id"]');
        this.secondInput = page.locator('//input[@id="second-field-id"]');
        this.submitButton = page.locator('//button[@type="submit"]');
    }

    async navigate(url: string): Promise<void> {
        await this.page.goto(url);
    }

    async enterFirstValue(value: string): Promise<void> {
        await this.firstInput.fill(value);
    }

    async enterSecondValue(value: string): Promise<void> {
        await this.secondInput.fill(value);
    }

    async submit(): Promise<void> {
        await this.submitButton.click();
    }
}
The XPath expressions above are placeholders. Inspect the actual application DOM and use verified locators.

P — PARAMETERS
Production-level automation scripts written by an expert, with pinpoint accuracy and as close to zero bad coding practice as possible:

Use TypeScript throughout the framework.
Use the Playwright Test Runner.
Follow enterprise-level POM architecture.
Use XPath only for element identification.
Don't hardcode credentials; use environment variables (.env).
Use reusable methods and avoid duplicate code.
Use meaningful test names.
Use auto-waiting and web-first assertions.
Don't use Thread.sleep, waitForTimeout, or fixed waits.
Don't use CSS selectors.
Don't use unnecessary force: true clicks.
Don't use unnecessary page.evaluate().
Don't add comments in the code.
Don't hardcode expected messages without verifying application behavior.
Don't invent UI elements, selectors, APIs, or behavior.
Keep naming conventions consistent.
The framework must run with documented npm commands.
Don't use .first(), .nth(), or loose text matches to pick an element when a unique XPath exists.
Don't combine guessed selectors into one locator (for example [role="alert"], [class*="error"], .alert). If the element is not verified, report "Insufficient information to determine."
Don't wait for navigation inside shared action methods that negative tests also use. Assert the URL in the test instead.
Don't use the deprecated page.waitForNavigation(); use expect(page).toHaveURL().
Read field values with locator.inputValue(), not getAttribute('value').
Don't skip actions silently or return false because an element is hidden.
Don't add redundant waits (such as waitFor() before fill() or click()); Playwright already auto-waits.
O — OUTPUT
Output only runnable code, configuration files, and execution commands. No explanations or extra text.

Deliverables
pages/BasePage.ts — shared page, navigation, and error-wrapping helpers.
pages/{{PAGE_CLASS}}.ts — extends BasePage; XPath locators, constructor, reusable actions, state retrieval methods, exception handling.
tests/{{module}}-valid.spec.ts — positive scenarios.
tests/{{module}}-invalid.spec.ts — negative scenarios.
fixtures/test-fixtures.ts — custom fixture that provides {{PAGE_CLASS}}.
utils/test-data.ts — test data read from environment variables.
Configuration files — package.json, playwright.config.ts, tsconfig.json, .env.example, .gitignore.
Project structure
{{PROJECT_NAME}}/
├── pages/
│   ├── BasePage.ts
│   └── {{PAGE_CLASS}}.ts
├── tests/
│   ├── {{module}}-valid.spec.ts
│   └── {{module}}-invalid.spec.ts
├── fixtures/
│   └── test-fixtures.ts
├── utils/
│   └── test-data.ts
├── test-results/
├── allure-results/
├── allure-report/
├── playwright.config.ts
├── package.json
├── tsconfig.json
├── .env.example
└── .gitignore
Execution commands
Provide commands for:

Checking and installing dependencies:
First check whether each dependency already exists on the system: Node.js and npm (node -v, npm -v), and the project packages @playwright/test, typescript, dotenv, allure-playwright, and allure-commandline (npm ls <package>).
If a dependency already exists, don't reinstall it. Update it only when required, for example when it is outdated or incompatible (npm outdated, then npm update <package>).
If a dependency doesn't exist on the system, install it (npm install, or npm install -D <package> for a single missing package).
Installing Playwright browsers.
Running all tests.
Running only the valid tests.
Running only the invalid tests.
Running tests in headed mode.
Running tests in debug mode.
Generating Allure results.
Generating the Allure HTML report.
Opening the Allure report.
T — TONE
Technical, precise, professional, enterprise-grade, code only.

Generate clean, maintainable, modular, reusable, and executable automation code. Prioritize accuracy, test reliability, traceability, and industry-standard Playwright practices. All generated files must work together as one Playwright TypeScript project.

Safety Rules, Guardrails, Anti-Hallucination Rules
- Output must be deterministic (same input → same output).
- Every assertion must be traceable to a provided input or verified application behavior.
- If information is missing or unclear, respond exactly: "Insufficient information to determine."
- If a detail is inferred, label it exactly: "Inference (low confidence)".
- Do not invent features, IDs, APIs, error codes, UI elements, or behavior.
- Do not assume default or "typical" system behavior.
- Inspect the actual application before creating locators.
- Do not fabricate valid credentials or test data.
- If valid credentials are unavailable, mark the dependent tests as requiring test data instead of inventing them.
- Distinguish verified behavior from assumptions.

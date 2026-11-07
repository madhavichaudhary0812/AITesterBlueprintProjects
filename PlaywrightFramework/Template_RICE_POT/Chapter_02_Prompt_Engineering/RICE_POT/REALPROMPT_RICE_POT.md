
RICE-POT Prompt — Playwright (TypeScript) VWO Login Automation
Attach with this prompt: Product Requirements Document: VWO Login Dashboard (PDF). Note: The PRD refers to app.vwo.com. The application under test is https://app.wingify.com/#/login. Treat them as the same login product and test only the Wingify URL.

R — ROLE
You are a Senior QA Automation Architect with 15+ years of experience in software testing, test automation, and enterprise-level automation framework development.

You are an expert in:

Playwright with TypeScript.
Enterprise-level UI automation.
Page Object Model (POM).
Playwright fixtures and reusable utilities.
Cross-browser testing.
Authentication and session management (email/password, SSO, social login, passkeys).
Functional, negative, regression, and accessibility testing (WCAG 2.1 AA).
Allure reporting.
CI/CD integration.
VWO (Visual Website Optimizer) login and dashboard functionality.
You understand real-world QA processes, requirement traceability, test design techniques, defect reporting, test coverage, and automation best practices.

I — INSTRUCTIONS
Create a complete, production-ready Playwright automation framework using TypeScript for the VWO login page.

Application URL: https://app.wingify.com/#/login

Primary objective: Automate and thoroughly validate the VWO login page against the attached PRD. Every test must map to a PRD requirement ID from the traceability matrix in the Context section.

1. Framework Requirements
[Critical] Use Playwright with TypeScript and the Playwright Test Runner.
[Mandatory] Follow the Page Object Model (POM) with reusable page classes and methods.
Implement Playwright fixtures wherever appropriate.
Use correct asynchronous programming with async/await.
[Critical] Implement structured exception handling.
Use web-first assertions (toBeVisible(), toBeFocused(), toHaveURL(), toHaveAttribute(), and so on).
Use condition-based waiting through Playwright's built-in auto-waiting and expect assertions.
[DoNOTuse] Thread.sleep, page.waitForTimeout(), or any other fixed wait.
Use environment variables for credentials (VWO_EMAIL, VWO_PASSWORD).
Support Chromium, Firefox, and WebKit, plus one mobile viewport project for responsive checks.
Implement correct browser and context lifecycle management. Use a fresh BrowserContext per test so Remember Me and session tests do not leak state.
Tag each test with its PRD requirement ID (for example @REQ-AUTH-01) and add it as an Allure label.
2. Locator Strategy
[Mandatory] Use XPath locators only.
[Don't] Don't use CSS selectors, getByRole, getByText, getByLabel, getByPlaceholder, getByTestId, or any other non-XPath locator strategy.
Attributes such as @id, @type, or @onclick may be used inside an XPath expression, but never as a standalone locator strategy.
[Critical] The VWO login page keeps hidden views (Forgot Password, SSO, Set New Password) in the DOM. They reuse type="email", type="password" and name="username". Generic XPath such as //input[@type="email"] matches several elements. Always use the unique attributes listed in the verified locator table below.
[Don't] Don't use data-qa values or utility class names such as W(100%). They look auto-generated and may change between releases.
Validate locator uniqueness before interacting with elements.
Do not invent XPath attributes or UI elements.
3. Page Object Model
Create a reusable LoginPage class.

Mandatory implementation:

Use a dedicated Page Object file.
Encapsulate all login page locators as readonly Locator fields.
Initialize the page and locators in the constructor.
Implement reusable action methods for:
Navigation to the login page.
Email entry.
Password entry.
Password visibility toggle.
Remember Me selection.
Sign-in submission.
Combined login(email, password, rememberMe?) method.
Opening Forgot Password.
Opening Sign in using SSO.
Error and validation message retrieval.
Clicking Sign in with Google and Sign in with Passkey (entry point only).
Submitting the form with the Enter key from the password field.
Clearing the email and password fields.
Reading placeholders, the current field value (inputValue()), and the password field type.
Create a BasePage class with the shared page, navigation, and error-wrapping helpers. LoginPage extends BasePage and does not redeclare page.
Keep page constants (login path, dashboard path) as readonly fields on the page class.
Expose locators as public readonly fields so tests can use web-first assertions on them directly (await expect(loginPage.email).toBeVisible()).
[Don't] Don't write isXxxVisible(): Promise<boolean> methods for tests to assert with expect(bool).toBe(true). That skips Playwright's auto-retrying assertions and makes tests flaky.
Implement appropriate exception handling.
Keep test assertions in the test files, not in the page object.
4. Test Coverage
Positive test scenarios (login-valid.spec.ts)

ID	Scenario	PRD ref
TC-P01	Login page loads at the application URL	REQ-UI-01
TC-P02	Email, password, Remember me and Sign in are visible	REQ-AUTH-01
TC-P03	Email field accepts input and has type="email"	REQ-VAL-02
TC-P04	Password field accepts input	REQ-AUTH-01
TC-P05	Password is masked by default and the toggle reveals it	REQ-SEC-04
TC-P06	Remember me checkbox can be checked and unchecked	REQ-AUTH-03
TC-P07	Clicking the "Remember me" label toggles the checkbox	REQ-UX-03
TC-P08	Valid credentials sign in and redirect to the dashboard	REQ-AUTH-01, REQ-INT-01
TC-P09	Forgot Password opens the password reset view	REQ-PWD-01
TC-P10	Start a FREE TRIAL link points to the free trial page	REQ-REG-01
TC-P11	Sign in using SSO opens the SSO view	REQ-AUTH-05
TC-P12	Sign in with Google button is visible	REQ-AUTH-06
TC-P13	Login page is usable at a mobile viewport	REQ-UX-01
TC-P14	All interactive elements are reachable with the Tab key and Enter submits the form	REQ-A11Y-03
TC-P15	Email placeholder is Enter email ID and password placeholder is Enter password	REQ-UI-01
TC-P16	Forgot Password button shows the text Forgot Password?	REQ-PWD-01
Negative test scenarios (login-invalid.spec.ts)

ID	Scenario	PRD ref
TC-N01	Valid-format email that is not registered + any password	REQ-VAL-04
TC-N02	Registered email + wrong password	REQ-VAL-04
TC-N03	Both email and password invalid	REQ-VAL-04
TC-N04	Empty email, filled password	REQ-VAL-01
TC-N05	Filled email, empty password	REQ-VAL-01
TC-N06	Both fields empty	REQ-VAL-01
TC-N07	Invalid email format (for example user@, user.com)	REQ-VAL-02
TC-N08	Validation feedback appears on blur	REQ-VAL-01
TC-N09	Error message is shown after failed authentication	REQ-VAL-04
TC-N10	Failed authentication does not reach #/dashboard	REQ-SEC-03
TC-N11	Password value is not exposed while masked	REQ-SEC-04
Conditional scenarios (automate only if verified in the live application)

ID	Scenario	PRD ref
TC-C01	Loading state while authentication is in progress	REQ-UX-04
TC-C02	First input field is auto-focused on page load	REQ-UX-02
TC-C03	Light / Dark Mode announcement or toggle	REQ-UI-03
TC-C04	Page is served over HTTPS	REQ-SEC-02
TC-C05	Rate limiting message after repeated failed attempts	REQ-SEC-05
If a conditional scenario is not present in the live application, mark it test.skip() with the reason "Not present in application — PRD ". Do not fake it.

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
Add the test case ID and PRD requirement ID as Allure labels.
Include execution status and failure details.
Capture screenshots on failure.
Attach relevant logs and diagnostic information (trace on first retry).
Generate an HTML Allure report.
7. Exception Handling
Implement structured error handling with try–catch blocks where appropriate. Handle:

Locator timeouts.
Navigation failures.
Authentication failures.
Unexpected application behavior (for example a CAPTCHA, MFA or rate-limit screen).
Assertion failures.
Browser execution failures.
Do not silently suppress errors. Preserve the original error and add meaningful diagnostic information.

C — CONTEXT
The application under test is the VWO login page.

URL: https://app.wingify.com/#/login

Requirement source: Product Requirements Document: VWO Login Dashboard (attached).

PRD traceability matrix
Req ID	PRD requirement	Automate in UI?
REQ-AUTH-01	Email and password login with secure validation	Yes
REQ-AUTH-02	Session management with configurable timeout	Partial — verify no dashboard access without login only
REQ-AUTH-03	Remember Me for persistent sessions	Yes
REQ-AUTH-04	Optional 2FA	Only if test account has 2FA
REQ-AUTH-05	Enterprise SSO	Entry point only (Sign in using SSO view)
REQ-AUTH-06	Social login (Google, Microsoft)	Entry point only — do not log in to third-party providers
REQ-VAL-01	Field validation on blur	Yes
REQ-VAL-02	Email format verification	Yes
REQ-VAL-03	Password strength indicators	No — applies to set/reset password, not login
REQ-VAL-04	Clear error messages on failed authentication	Yes
REQ-PWD-01	Forgot Password flow	Navigation to reset view only — do not send reset emails
REQ-REG-01	Free trial signup link	Yes (link target)
REQ-UI-01	Clean login form with VWO branding	Yes (page loads, key elements visible)
REQ-UI-03	Light and Dark Mode announcement	Conditional
REQ-UX-01	Responsive, mobile-optimized design	Yes (mobile viewport)
REQ-UX-02	Auto-focus on first input	Conditional
REQ-UX-03	Clickable labels	Yes
REQ-UX-04	Loading states during authentication	Conditional
REQ-A11Y-01	Screen reader support (ARIA labels)	Partial — check aria-label on icon buttons
REQ-A11Y-03	Full keyboard navigation	Yes
REQ-SEC-02	HTTPS enforcement	Yes (URL protocol)
REQ-SEC-03	Secure session handling	Partial — no dashboard after failed login
REQ-SEC-04	Password protection	Yes (masking)
REQ-SEC-05	Rate limiting	Conditional — never hammer production
REQ-INT-01	Transition to dashboard after login	Yes (needs valid credentials)
Out of scope for UI automation
Do not write tests that claim to verify these PRD items: end-to-end encryption, password hashing, GDPR/CCPA compliance, 99.9% uptime, concurrent user load, CDN, multi-region deployment, analytics tracking, login success rate KPIs, biometric authentication, and other future enhancements.

Verified locators (inspected on the live page, 2026-11-07)
Element	XPath	Status
Email input	//input[@id="login-username"]	Verified
Password input	//input[@id="login-password"]	Verified
Show/hide password	//button[@onclick="login.togglePasswordVisibility()"]	Verified
Remember me label	//label[@for="checkbox-remember"]	Verified
Remember me checkbox	//input[@id="checkbox-remember"]	Inference (low confidence) — from the label's for attribute
Sign in button	//button[@id="js-login-btn"]	Verified
Forgot Password?	//button[@onclick="login.gotoForgotPasswordView()"]	Verified
Sign in with Google	//button[@id="js-google-signin-btn"]	Verified
Sign in using SSO	//button[@onclick="login.goToSSOView()"]	Verified
Sign in with Passkey	//button[@onclick="login.loginUsingPasskey()"]	Verified
Start a FREE TRIAL	//a[contains(@href,"wingify.com/free-trial")]	Verified (opens in a new tab)
Error / validation message	Not yet identified	Insufficient information to determine — inspect after a failed login
The button label in the application is "Sign in", not "Login".

User-provided element details (confirmed)
Use these exact values for attribute and text assertions:

Element	XPath	type	name	Placeholder / visible text
Email input	//input[@id="login-username"]	email	username	Placeholder: Enter email ID
Password input	//input[@id="login-password"]	password	password	Placeholder: Enter password
Forgot Password	//button[@onclick="login.gotoForgotPasswordView()"]	button	—	Text: Forgot Password?
<input type="email" class="text-input W(100%)" name="username" vwo-html-translate-attr="placeholder" vwo-html-translate-placeholder="login:enterEmailID" id="login-username" data-qa="hocewoqisi" placeholder="Enter email ID">
<input type="password" class="text-input W(100%) Pend(36px)" vwo-html-translate-attr="placeholder" vwo-html-translate-placeholder="login:enterPassword" name="password" id="login-password" data-qa="jobodapuxe" placeholder="Enter password">
<button type="button" class="btn btn--link btn--primary Fw(medium)" onclick="login.gotoForgotPasswordView()" data-qa="juvuwepeyi" vwo-html-translate="login:forgotPassword">Forgot Password?</button>
The placeholder and label text are translated (vwo-html-translate-*). Assert these values only for the English locale.

Expected user journey
User navigates to the VWO login page.
User enters an email address.
User enters a password.
User optionally selects Remember me.
User clicks Sign in.
Application validates the credentials.
Successful authentication redirects the user to the VWO dashboard (#/dashboard).
Invalid authentication shows an error message.
E — EXAMPLE
Example structure for the Playwright Page Object Model, using the verified locators:

import { Page, Locator } from '@playwright/test';

export class LoginPage {
    readonly page: Page;
    readonly email: Locator;
    readonly password: Locator;
    readonly passwordToggle: Locator;
    readonly rememberMe: Locator;
    readonly rememberMeLabel: Locator;
    readonly signInButton: Locator;
    readonly forgotPassword: Locator;
    readonly ssoButton: Locator;
    readonly freeTrialLink: Locator;

    constructor(page: Page) {
        this.page = page;
        this.email = page.locator('//input[@id="login-username"]');
        this.password = page.locator('//input[@id="login-password"]');
        this.passwordToggle = page.locator('//button[@onclick="login.togglePasswordVisibility()"]');
        this.rememberMe = page.locator('//input[@id="checkbox-remember"]');
        this.rememberMeLabel = page.locator('//label[@for="checkbox-remember"]');
        this.signInButton = page.locator('//button[@id="js-login-btn"]');
        this.forgotPassword = page.locator('//button[@onclick="login.gotoForgotPasswordView()"]');
        this.ssoButton = page.locator('//button[@onclick="login.goToSSOView()"]');
        this.freeTrialLink = page.locator('//a[contains(@href,"wingify.com/free-trial")]');
    }

    async navigate(): Promise<void> {
        await this.page.goto('/#/login');
    }

    async enterEmail(email: string): Promise<void> {
        await this.email.fill(email);
    }

    async enterPassword(password: string): Promise<void> {
        await this.password.fill(password);
    }

    async setRememberMe(checked: boolean): Promise<void> {
        await this.rememberMe.setChecked(checked);
    }

    async clickSignIn(): Promise<void> {
        await this.signInButton.click();
    }

    async login(email: string, password: string, rememberMe = false): Promise<void> {
        await this.enterEmail(email);
        await this.enterPassword(password);
        await this.setRememberMe(rememberMe);
        await this.clickSignIn();
    }
}
Re-verify these locators against the live DOM before generating the final code. If the Remember me input is visually hidden, check it by clicking rememberMeLabel instead of forcing a click.

P — PARAMETERS
Production-level automation scripts written by an expert, with pinpoint accuracy and as close to zero bad coding practice as possible:

Use TypeScript throughout the framework.
Use the Playwright Test Runner.
Follow enterprise-level POM architecture.
Use XPath only for element identification.
Don't hardcode credentials; use VWO_EMAIL and VWO_PASSWORD from .env.
Set baseURL to https://app.wingify.com in playwright.config.ts.
Use reusable methods and avoid duplicate code.
Use meaningful test names that include the test case ID (for example TC-N02 Registered email with wrong password shows error).
Use auto-waiting and web-first assertions.
Don't use Thread.sleep, waitForTimeout, or fixed waits.
Don't use CSS selectors.
Don't use unnecessary force: true clicks.
Don't use unnecessary page.evaluate().
Don't add comments in the code.
Don't hardcode expected error messages without verifying application behavior.
Don't invent UI elements, selectors, APIs, or authentication behavior.
Don't send real password-reset emails, sign in to Google or SSO providers, or run brute-force loops against production.
Keep negative tests to a small number of failed attempts so the test account is not locked.
Keep naming conventions consistent.
The framework must run with documented npm commands.
Don't use .first(), .nth(), or loose text matches such as /sign in/i to pick an element. "Sign in" also matches "Sign in with Google" and "Sign in using SSO"; use the unique XPath instead.
Don't combine guessed selectors into one locator (for example [role="alert"], [class*="error"], .alert) for error messages or headings. If the element is not verified, report "Insufficient information to determine."
Don't wait for navigation inside the shared login() method. Negative tests reuse it and no navigation happens. Assert the URL in the test instead.
Don't use the deprecated page.waitForNavigation(); use expect(page).toHaveURL().
Read field values with locator.inputValue(), not getAttribute('value'), which returns the initial HTML value and not what the user typed.
Don't skip actions silently. If the Remember me input is not visible, click its label; never return early or return false because an element is hidden.
Don't add redundant waits (such as waitFor() before fill() or click()); Playwright already auto-waits.
O — OUTPUT
Output only runnable code, configuration files, and execution commands. No explanations or extra text.

Deliverables
pages/BasePage.ts — shared page, navigation, and error-wrapping helpers.
pages/LoginPage.ts — extends BasePage; XPath locators, constructor, reusable actions, message retrieval methods, exception handling.
tests/login-valid.spec.ts — TC-P01 to TC-P16.
tests/login-invalid.spec.ts — TC-N01 to TC-N11.
fixtures/test-fixtures.ts — custom fixture that provides LoginPage.
utils/test-data.ts — credentials from environment variables and invalid test data sets.
Configuration files — package.json, playwright.config.ts, tsconfig.json, .env.example, .gitignore.
Project structure
VWO-Playwright-Automation/
├── pages/
│   ├── BasePage.ts
│   └── LoginPage.ts
├── tests/
│   ├── login-valid.spec.ts
│   └── login-invalid.spec.ts
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
Running only valid login tests.
Running only invalid login tests.
Running a single test by ID (for example --grep TC-N02).
Running tests in headed mode.
Running tests in debug mode.
Generating Allure results.
Generating the Allure HTML report.
Opening the Allure report.
T — TONE
Technical, precise, professional, enterprise-grade, code only.

Generate clean, maintainable, modular, reusable, and executable automation code. Prioritize accuracy, test reliability, requirement traceability, and industry-standard Playwright practices. All generated files must work together as one Playwright TypeScript project.

Safety Rules, Guardrails, Anti-Hallucination Rules
- Output must be deterministic (same input → same output).
- Every assertion must be traceable to a PRD requirement ID or verified application behavior.
- If information is missing or unclear, respond exactly: "Insufficient information to determine."
- If a detail is inferred, label it exactly: "Inference (low confidence)".
- Do not invent features, IDs, APIs, error codes, UI elements, or behavior.
- Do not assume default or "typical" system behavior.
- Do not assume that every PRD feature is implemented in the live application.
- Inspect the actual VWO login page before creating locators.
- Do not fabricate valid login credentials.
- Do not claim successful login without verified test credentials.
- If valid credentials are unavailable, mark TC-P08 as requiring test data instead of inventing credentials.
- Distinguish verified behavior from assumptions.

# Stage 1 — Selenium RICE-POT Baseline Test Cases

**Status:** Draft for review. These are test-case proposals, not executed tests.

**Application in scope:** `https://app.wingify.com/#/login`

**Source note:** The supplied Selenium RICE-POT file describes Salesforce (`login.salesforce.com`) rather than VWO. For this baseline, its Selenium framework approach (Java, TestNG, Page Object Model, XPath, and Allure) is applied to the VWO application and scenario matrix in the VWO Playwright RICE-POT prompt and its attached VWO login PRD. This adaptation is based on the user's requested VWO scope. The app.vwo.com reference in the PRD is treated according to the VWO prompt's instruction to test only the Wingify URL.

**Baseline method:** Stage 1 uses the RICE-POT scenario proposals without applying the separate `anti-hallucination.rules.md` review process. Expected results below are prompt/PRD expectations, not claims that the behavior has been verified in the live application.

**Shared execution notes:** Start each applicable case from a fresh login page/session. Use authorized test-account credentials only for positive authentication. Do not send password-reset emails or authenticate through Google/SSO providers. Conditional cases should be run only when the related UI behavior is present. Use a test environment and controlled test data for authentication failures.

## Positive cases

| ID | Scenario | PRD requirement | Steps | Expected result proposed by RICE-POT |
|---|---|---|---|---|
| TC-P01 | Login page loads at the application URL | REQ-UI-01 | Navigate to the application login URL. | Login page is displayed at the login route. |
| TC-P02 | Email, password, Remember me, and Sign in controls are visible | REQ-AUTH-01 | Open the login page; inspect the four controls. | All four controls are visible. |
| TC-P03 | Email field accepts input and has email type | REQ-VAL-02 | Enter a valid-format sample email; inspect the field type and value. | Field accepts the value and has `type=email`. |
| TC-P04 | Password field accepts input | REQ-AUTH-01 | Enter a non-production sample password. | Password field accepts the entered value. |
| TC-P05 | Password is masked and visibility toggle reveals it | REQ-SEC-04 | Enter a sample password; inspect the default state; toggle visibility on and off. | Password is masked by default; toggle reveals and remasks it. |
| TC-P06 | Remember me can be checked and unchecked | REQ-AUTH-03 | Select Remember me, then clear the selection. | Checkbox selection changes to checked and back to unchecked. |
| TC-P07 | Remember me label toggles its checkbox | REQ-UX-03 | Click the Remember me label twice, inspecting state after each click. | Each label click toggles the checkbox. |
| TC-P08 | Valid credentials sign in and redirect to dashboard | REQ-AUTH-01, REQ-INT-01 | Enter authorized valid test-account credentials and submit. | Authentication succeeds and the browser reaches `#/dashboard`. |
| TC-P09 | Forgot Password opens the password-reset view | REQ-PWD-01 | Click Forgot Password. | Password-reset view opens; do not submit a reset request or send an email. |
| TC-P10 | Start a FREE TRIAL link points to the free-trial page | REQ-REG-01 | Inspect the link destination without completing registration. | Link points to the free-trial page. |
| TC-P11 | Sign in using SSO opens the SSO view | REQ-AUTH-05 | Click the SSO entry point. | SSO view opens; do not authenticate with an external identity provider. |
| TC-P12 | Sign in with Google button is visible | REQ-AUTH-06 | Open the login page and inspect the Google entry point. | Google sign-in button is visible; do not continue to Google authentication. |
| TC-P13 | Login page is usable at a mobile viewport | REQ-UX-01 | Open the login page at a mobile-sized viewport; inspect the core controls. | Login form is usable and core controls remain available. |
| TC-P14 | Interactive elements are keyboard reachable and Enter submits | REQ-A11Y-03 | Navigate controls using Tab; enter authorized credentials; submit with Enter from the password field. | Interactive elements are reachable by keyboard and Enter submits the form. |
| TC-P15 | Email and password placeholders match the English UI | REQ-UI-01 | Inspect both field placeholders in the English locale. | Email placeholder is `Enter email ID`; password placeholder is `Enter password`. |
| TC-P16 | Forgot Password button displays its expected text | REQ-PWD-01 | Inspect the Forgot Password button text. | Button text is `Forgot Password?`. |

## Negative cases

| ID | Scenario | PRD requirement | Steps | Expected result proposed by RICE-POT |
|---|---|---|---|---|
| TC-N01 | Valid-format unregistered email and password | REQ-VAL-04 | In an approved test environment, submit a valid-format unregistered address and test password. | Authentication is rejected and an error is shown. |
| TC-N02 | Registered email with wrong password | REQ-VAL-04 | In an approved test environment, submit a registered test email and incorrect password. | Authentication is rejected and an error is shown. |
| TC-N03 | Invalid email and password | REQ-VAL-04 | In an approved test environment, submit invalid test credentials. | Authentication is rejected and an error is shown. |
| TC-N04 | Empty email and filled password | REQ-VAL-01 | Leave email empty, enter a sample password, and exercise the form validation. | Empty email is validated and feedback is shown. |
| TC-N05 | Filled email and empty password | REQ-VAL-01 | Enter a valid-format sample email, leave password empty, and exercise validation. | Empty password is validated and feedback is shown. |
| TC-N06 | Both credential fields are empty | REQ-VAL-01 | Leave email and password empty and exercise validation. | Required-field validation is shown. |
| TC-N07 | Invalid email format | REQ-VAL-02 | Enter invalid-format samples such as `user@` or `user.com`; exercise validation. | Invalid email format is rejected or validation feedback is shown. |
| TC-N08 | Validation feedback appears on blur | REQ-VAL-01 | Enter a value requiring validation and move focus away from the field. | Validation feedback appears on blur. |
| TC-N09 | Failed authentication displays an error message | REQ-VAL-04 | In an approved test environment, make one controlled failed authentication attempt. | An authentication error message is displayed. |
| TC-N10 | Failed authentication does not reach dashboard | REQ-SEC-03 | In an approved test environment, submit one controlled invalid login attempt; inspect the route. | The browser does not reach `#/dashboard`. |
| TC-N11 | Password value is not exposed while masked | REQ-SEC-04 | Enter a sample password without toggling visibility. | Password input remains masked. |

## Conditional cases

| ID | Scenario | PRD requirement | Steps | Expected result proposed by RICE-POT |
|---|---|---|---|---|
| TC-C01 | Loading state while authentication is in progress | REQ-UX-04 | Observe an authentication attempt in a safe test environment. | A loading state appears during authentication, if present. |
| TC-C02 | First input is auto-focused on page load | REQ-UX-02 | Load the login page and inspect keyboard focus. | Email input receives focus on load, if implemented. |
| TC-C03 | Light/dark-mode announcement or toggle | REQ-UI-03 | Inspect the login page for a mode announcement or toggle. | Mode announcement or toggle is present, if implemented. |
| TC-C04 | Login page is served over HTTPS | REQ-SEC-02 | Load the application login URL and inspect its scheme. | Login page URL uses HTTPS. |
| TC-C05 | Rate-limiting message after repeated failures | REQ-SEC-05 | Only in an explicitly approved non-production test environment, use the authorized rate-limit test procedure. | Rate-limiting feedback appears if implemented; do not perform repeated attempts against production. |

## Review checkpoint

This is the Stage 1 baseline only. It contains **32 proposed cases**: 16 positive, 11 negative, and 5 conditional. Authentication outcomes, field-validation feedback, reset/SSO views, and conditional features have not all been independently verified here. Review this baseline before proceeding to Stage 2.

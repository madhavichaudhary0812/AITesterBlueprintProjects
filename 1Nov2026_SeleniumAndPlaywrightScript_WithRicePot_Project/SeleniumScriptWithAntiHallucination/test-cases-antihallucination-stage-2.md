# Stage 2 — Selenium Test Cases Using Anti-Hallucination Rules

## Verified Facts:

Sources used: `anti-hallucination.rules.md`; the user-provided VWO Playwright RICE-POT prompt and its scenario/locator tables; the user-provided Selenium RICE-POT prompt; and the user's previously supplied notification HTML.

- The requested application is the VWO login page at `https://app.wingify.com/#/login`. The VWO Playwright prompt explicitly says to test the Wingify URL, although it says the PRD refers to `app.vwo.com`.
- The user confirmed that `app.vwo.com` and `app.wingify.com/#/login` are the same application; use the Wingify URL for the test target.
- The VWO prompt lists the following locators as verified on the live page on 2026-11-07:
  - Email: `//input[@id="login-username"]`
  - Password: `//input[@id="login-password"]`
  - Password visibility toggle: `//button[@onclick="login.togglePasswordVisibility()"]`
  - Remember me label: `//label[@for="checkbox-remember"]`
  - Sign in: `//button[@id="js-login-btn"]`
  - Forgot Password: `//button[@onclick="login.gotoForgotPasswordView()"]`
  - Google sign-in: `//button[@id="js-google-signin-btn"]`
  - SSO sign-in: `//button[@onclick="login.goToSSOView()"]`
  - Passkey sign-in: `//button[@onclick="login.loginUsingPasskey()"]`
  - Free-trial link: `//a[contains(@href,"wingify.com/free-trial")]`
- The supplied field details specify the email type and placeholder as `email` and `Enter email ID`, and the password type and placeholder as `password` and `Enter password`. The supplied Forgot Password button text is `Forgot Password?`; the supplied sign-in button label is `Sign in`.
- The VWO prompt describes the valid-login destination as `#/dashboard`, says password-reset testing must not send an email, and limits Google/SSO checks to the entry point rather than third-party authentication.
- The VWO prompt marks the Remember me checkbox locator as **Inference (low confidence)**, rather than verified.
- The user supplied this notification markup and text: element ID `js-notification-box-msg`; text `Your email, password, IP address or location did not match`.
- The Selenium RICE-POT prompt describes Salesforce, not VWO. It does not provide VWO-specific requirements or selectors.
- Previously shared credentials are not copied into these documents or reused. Whether that account is authorized for VWO username/password sign-in is unverified.

## Missing / Unknown Information:

- The complete contents of the attached VWO PRD have not been independently extracted and reviewed for this stage; the available VWO prompt contains a PRD traceability matrix and scenario list.
- Whether Gmail addresses are rejected specifically as non-business email addresses is not established by the supplied notification. The text reports an email, password, IP-address, or location mismatch; it does not identify which condition caused the message.
- Authorized valid VWO test credentials and a safe test environment for authentication attempts are not provided as test data for this stage. Dashboard success and authentication-error tests therefore require those inputs before execution.
- The reset-password and SSO view routes, contents, and stable assertions are not specified.
- The Remember me checkbox locator/behavior remains **Inference (low confidence)** in the source prompt; only the label locator is listed as verified.
- Validation feedback text, elements, and behavior for empty or malformed values, including blur behavior, are unspecified.
- Exact mobile viewport(s) and measurable responsive pass criteria are unspecified.
- Keyboard tab order and which controls must be reachable are not enumerated.
- Loading-state, auto-focus, theme, rate-limit, and session-timeout behavior is conditional or lacks verified UI details. No attempt threshold for rate-limit testing is supplied.
- Selenium's supplied RICE-POT prompt targets Salesforce, so VWO-specific requirements must come from the VWO prompt/PRD and not be inferred from Salesforce.

## Generated Output:

The following are Stage 2 test-case candidates derived only from the verified facts above. They are specifications, not executed tests. For Selenium, implement with Java/TestNG; do not treat the XPath strings below as newly verified DOM observations.

| ID | Scenario | Steps | Expected result supported by supplied input | Status |
|---|---|---|---|---|
| AH-S01 | Open the specified VWO login URL | Navigate to `https://app.wingify.com/#/login`. | The navigation target is the exact URL supplied by the user and VWO prompt. Whether it renders the expected login page must be confirmed by execution. | Candidate; page behavior needs execution evidence. |
| AH-S02 | Inspect email field details | Locate the email field with the supplied XPath; inspect its type and placeholder. | `type=email`; placeholder `Enter email ID`. | Candidate; values are explicitly supplied. |
| AH-S03 | Inspect password field details | Locate the password field with the supplied XPath; inspect its type and placeholder. | `type=password`; placeholder `Enter password`. | Candidate; values are explicitly supplied. |
| AH-S04 | Inspect Sign in control | Locate the Sign in button with the supplied XPath and inspect its label. | Label is `Sign in`. | Candidate; locator and label are explicitly supplied. |
| AH-S05 | Inspect Remember me label | Locate the supplied label XPath. | The label is the Remember me control label; do not assert checkbox state using the low-confidence checkbox locator. | Candidate; label locator is explicitly marked verified. |
| AH-S06 | Verify password masking toggle | Enter a sample password; inspect the field type; click the supplied toggle twice and inspect the type after each click. | Password is masked by default; the toggle reveals the value and then masks it again, as specified by TC-P05 and REQ-SEC-04 in the supplied VWO prompt. | Candidate; expected behavior is supplied, but runtime behavior needs execution evidence. |
| AH-S07 | Inspect Forgot Password control | Locate the supplied button XPath and inspect its text. | Text is `Forgot Password?`; do not assert reset-view route/content or send a reset email. | Candidate; button text is explicitly supplied. |
| AH-S08 | Inspect Google sign-in entry point | Locate the supplied Google button XPath. | The Google sign-in entry point is identified; do not proceed to Google authentication. | Candidate; visibility should be confirmed by execution. |
| AH-S09 | Inspect SSO sign-in entry point | Locate the supplied SSO button XPath. | The SSO entry point is identified; do not infer the destination view or authenticate with an identity provider. | Candidate; visibility should be confirmed by execution. |
| AH-S10 | Inspect passkey sign-in entry point | Locate the supplied passkey button XPath. | The passkey entry point is identified; do not infer or execute passkey authentication. | Candidate; visibility should be confirmed by execution. |
| AH-S11 | Inspect free-trial link target | Locate the supplied free-trial XPath and read its `href`. | The supplied locator is constrained to an `href` containing `wingify.com/free-trial`. | Candidate; actual runtime attribute should be confirmed by execution. |
| AH-S12 | Check the specified URL scheme | Inspect the supplied application URL's scheme. | The specified URL begins with `https://`. This verifies the configured URL string only, not server-side HTTPS enforcement or redirect behavior. | Candidate; scope limited to the provided URL. |
| AH-S13 | Check the user-reported authentication notification | Only after an explicitly approved, controlled authentication-failure scenario, locate the supplied notification element ID and read its text. | If this reported notification is shown, its text is `Your email, password, IP address or location did not match`. This does not prove a Gmail/business-email restriction. | Blocked until a safe test environment and authorized test data are supplied. |

Not generated as executable pass/fail cases in this stage: successful dashboard login; Remember me checkbox state; reset/SSO destination assertions; detailed invalid-field or blur validation; keyboard order; mobile layout thresholds; loading, focus, theme, rate-limit, and session-timeout checks. For each, the needed expected behavior or test data is **Insufficient information to determine.**

## Self-Validation Check:

- Every locator and literal asserted above is present in the supplied VWO prompt or user-provided notification HTML.
- The Salesforce Selenium prompt was not used as evidence for VWO application behavior.
- No account credentials were copied into the documents or reused. No Gmail-specific rejection cause, unverified selector, reset/SSO destination, validation message, mobile threshold, or rate-limit threshold was invented.
- The password-toggle result comes from the explicit TC-P05 scenario in the supplied VWO prompt; it is not inferred solely from the locator.
- The URL-scheme case is explicitly limited to the supplied URL string; it does not claim to verify server enforcement.
- This is Stage 2 only. Gap counts, missing-information remediation, before/after comparison, and effectiveness evaluation are reserved for later review stages.

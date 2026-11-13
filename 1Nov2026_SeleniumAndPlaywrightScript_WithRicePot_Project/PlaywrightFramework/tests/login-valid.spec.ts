import { test, expect } from '../fixtures/test-fixtures';
import { getValidCredentials } from '../utils/test-data';

test.beforeEach(async ({ loginPage }) => {
  await loginPage.navigate();
});

test('TC-P01 Login page loads at the application URL @REQ-UI-01', async ({
  page
}) => {
  await expect(page).toHaveURL(/#\/login$/);
  await expect(page).toHaveTitle('Login - Wingify');
});

test('TC-P02 Email, password, Remember me and Sign in are visible @REQ-AUTH-01', async ({
  loginPage
}) => {
  await expect(loginPage.email).toBeVisible();
  await expect(loginPage.password).toBeVisible();
  await expect(loginPage.rememberMeLabel).toBeVisible();
  await expect(loginPage.signInButton).toBeVisible();
});

test('TC-P03 Email field accepts input and has type email @REQ-VAL-02', async ({
  loginPage
}) => {
  await expect(loginPage.email).toHaveAttribute('type', 'email');
  await loginPage.enterEmail('qa@example.com');
  await expect(loginPage.email).toHaveValue('qa@example.com');
});

test('TC-P04 Password field accepts input @REQ-AUTH-01', async ({
  loginPage
}) => {
  await loginPage.enterPassword('Sample-not-a-real-password');
  await expect(loginPage.password).toHaveValue('Sample-not-a-real-password');
});

test('TC-P17 User can enter email and password together @REQ-AUTH-01 @REQ-VAL-02', async ({
  loginPage
}) => {
  const email = 'qa.demo@example.com';
  const password = 'NotARealPassword-123!';

  await loginPage.enterEmail(email);
  await loginPage.enterPassword(password);

  await expect(loginPage.email).toHaveValue(email);
  await expect(loginPage.password).toHaveValue(password);
  await expect(loginPage.password).toHaveAttribute('type', 'password');
});

test('TC-P05 Password is masked by default and toggle reveals it @REQ-SEC-04', async ({
  loginPage
}) => {
  await loginPage.enterPassword('Sample-not-a-real-password');
  await expect(loginPage.password).toHaveAttribute('type', 'password');
  await loginPage.togglePasswordVisibility();
  await expect(loginPage.password).toHaveAttribute('type', 'text');
  await loginPage.togglePasswordVisibility();
  await expect(loginPage.password).toHaveAttribute('type', 'password');
});

test('TC-P06 Remember me checkbox can be checked and unchecked @REQ-AUTH-03', async ({
  loginPage
}) => {
  await loginPage.setRememberMe(true);
  await expect(loginPage.rememberMe).toBeChecked();
  await loginPage.setRememberMe(false);
  await expect(loginPage.rememberMe).not.toBeChecked();
});

test('TC-P07 Clicking Remember me label toggles the checkbox @REQ-UX-03', async ({
  loginPage
}) => {
  await loginPage.rememberMeLabel.click();
  await expect(loginPage.rememberMe).toBeChecked();
  await loginPage.rememberMeLabel.click();
  await expect(loginPage.rememberMe).not.toBeChecked();
});

test('TC-P08 Valid credentials sign in and redirect to dashboard @REQ-AUTH-01 @REQ-INT-01', async ({
  loginPage,
  page
}) => {
  const credentials = getValidCredentials();
  test.skip(
    !credentials,
    'Requires an authorized VWO test account in VWO_EMAIL and VWO_PASSWORD.'
  );
  if (!credentials) {
    return;
  }

  await loginPage.login(credentials.email, credentials.password);
  await expect(page).toHaveURL(/#\/dashboard(?:$|[/?])/);
});

test('TC-P09 Forgot Password opens the password reset view @REQ-PWD-01', async () => {
  test.skip(
    true,
    'The reset view has no verified locator or route; do not infer application behavior.'
  );
});

test('TC-P10 Start a FREE TRIAL link points to the free trial page @REQ-REG-01', async ({
  loginPage
}) => {
  await expect(loginPage.freeTrialLink).toHaveAttribute(
    'href',
    /wingify\.com\/free-trial/
  );
});

test('TC-P11 Sign in using SSO opens the SSO view @REQ-AUTH-05', async () => {
  test.skip(
    true,
    'The SSO view has no verified locator or route; the test does not open an external identity provider.'
  );
});

test('TC-P12 Sign in with Google button is visible @REQ-AUTH-06', async ({
  loginPage
}) => {
  await expect(loginPage.googleSignInButton).toBeVisible();
});

test('TC-P13 Login page is usable at a mobile viewport @REQ-UX-01', async ({
  loginPage
}, testInfo) => {
  test.skip(
    testInfo.project.name !== 'mobile-safari',
    'Responsive check runs in the mobile-safari project.'
  );
  await expect(loginPage.email).toBeVisible();
  await expect(loginPage.password).toBeVisible();
  await expect(loginPage.signInButton).toBeVisible();
});

test('TC-P14 Keyboard navigation and Enter submits the form @REQ-A11Y-03', async () => {
  test.skip(
    true,
    'Submitting the form requires an explicitly approved test account and is covered by TC-P08.'
  );
});

test('TC-P15 Email and password placeholders match the English UI @REQ-UI-01', async ({
  loginPage
}) => {
  await expect(loginPage.email).toHaveAttribute('placeholder', 'Enter email ID');
  await expect(loginPage.password).toHaveAttribute('placeholder', 'Enter password');
});

test('TC-P16 Forgot Password button shows the expected text @REQ-PWD-01', async ({
  loginPage
}) => {
  await expect(loginPage.forgotPassword).toHaveText('Forgot Password?');
});

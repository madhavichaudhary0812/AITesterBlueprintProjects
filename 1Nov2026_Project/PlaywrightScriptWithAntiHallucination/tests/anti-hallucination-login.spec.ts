import { test, expect } from '../fixtures/test-fixtures';
import { LoginPage } from '../pages/LoginPage';

test.beforeEach(async ({ loginPage }) => {
  await loginPage.navigate();
});

test('AH-P01 Opens the specified VWO login URL', async ({ loginPage }) => {
  expect(await loginPage.currentUrl()).toBe(LoginPage.loginUrl);
});

test('AH-P02 Email field has the supplied type and placeholder', async ({
  loginPage
}) => {
  expect(await loginPage.emailType()).toBe('email');
  expect(await loginPage.emailPlaceholder()).toBe('Enter email ID');
});

test('AH-P03 Password field has the supplied type and placeholder', async ({
  loginPage
}) => {
  expect(await loginPage.passwordType()).toBe('password');
  expect(await loginPage.passwordPlaceholder()).toBe('Enter password');
});

test('AH-P04 Sign in control has the supplied label', async ({
  loginPage
}) => {
  expect(await loginPage.signInText()).toBe('Sign in');
});

test('AH-P05 Remember me label is present without inferring checkbox state', async ({
  loginPage
}) => {
  await expect(loginPage.rememberMeLabel).toBeVisible();
});

test('AH-P06 Password visibility toggle changes the supplied password field type', async ({
  loginPage
}) => {
  await loginPage.enterSamplePassword();
  expect(await loginPage.passwordType()).toBe('password');
  await loginPage.togglePasswordVisibility();
  expect(await loginPage.passwordType()).toBe('text');
  await loginPage.togglePasswordVisibility();
  expect(await loginPage.passwordType()).toBe('password');
});

test('AH-P07 Forgot Password control has the supplied text', async ({
  loginPage
}) => {
  expect(await loginPage.forgotPasswordText()).toBe('Forgot Password?');
});

test('AH-P08 Google sign-in entry point is visible', async ({
  loginPage
}) => {
  await expect(loginPage.googleSignInButton).toBeVisible();
});

test('AH-P09 SSO entry point is visible without opening it', async ({
  loginPage
}) => {
  await expect(loginPage.ssoSignInButton).toBeVisible();
});

test('AH-P10 Passkey entry point is visible without starting authentication', async ({
  loginPage
}) => {
  await expect(loginPage.passkeySignInButton).toBeVisible();
});

test('AH-P11 Free trial link href contains the supplied destination fragment', async ({
  loginPage
}) => {
  await expect(loginPage.freeTrialLink.first()).toHaveAttribute(
    'href',
    /wingify\.com\/free-trial/
  );
});

test('AH-P12 Configured login URL uses the supplied HTTPS scheme', async () => {
  expect(new URL(LoginPage.loginUrl).protocol).toBe('https:');
});

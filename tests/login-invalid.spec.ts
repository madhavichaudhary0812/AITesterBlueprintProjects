import { test, expect } from '../fixtures/test-fixtures';
import { getValidCredentials } from '../utils/test-data';

test.beforeEach(async ({ loginPage }) => {
  await loginPage.navigate();
});

test('TC-N01 Unregistered email and password are rejected @REQ-VAL-04', async () => {
  test.skip(
    true,
    'Authentication failures are not submitted to the production login without an approved non-production test account.'
  );
});

test('TC-N02 Registered email with wrong password shows an error @REQ-VAL-04', async () => {
  test.skip(
    true,
    'Authentication failures are not submitted to the production login without an approved non-production test account.'
  );
});

test('TC-N03 Invalid email and password show an error @REQ-VAL-04', async () => {
  test.skip(
    true,
    'Authentication failures are not submitted to the production login without an approved non-production test account.'
  );
});

test('TC-N04 Empty email with filled password is validated @REQ-VAL-01', async () => {
  test.skip(
    true,
    'Validation feedback and its locator have not been verified; avoid submitting an unverified production request.'
  );
});

test('TC-N05 Filled email with empty password is validated @REQ-VAL-01', async () => {
  test.skip(
    true,
    'Validation feedback and its locator have not been verified; avoid submitting an unverified production request.'
  );
});

test('TC-N06 Empty credentials are validated @REQ-VAL-01', async () => {
  test.skip(
    true,
    'Validation feedback and its locator have not been verified; avoid submitting an unverified production request.'
  );
});

test('TC-N07 Invalid email formats show validation @REQ-VAL-02', async () => {
  test.skip(true, 'Validation feedback and its locator have not been verified.');
});

test('TC-N08 Validation feedback appears on blur @REQ-VAL-01', async () => {
  test.skip(true, 'Validation feedback and its locator have not been verified.');
});

test('TC-N09 Gmail login shows the reported authentication error @REQ-VAL-04', async ({
  loginPage
}) => {
  const credentials = getValidCredentials();
  const isGmailAddress = credentials?.email.toLowerCase().endsWith('@gmail.com');
  test.skip(
    !credentials || !isGmailAddress,
    'Requires a Gmail test account in VWO_EMAIL and its password in VWO_PASSWORD.'
  );
  if (!credentials || !isGmailAddress) {
    return;
  }

  await loginPage.login(credentials.email, credentials.password);
  await expect(loginPage.notificationMessage).toHaveText(
    'Your email, password, IP address or location did not match'
  );
});

test('TC-N10 Failed authentication does not reach the dashboard @REQ-SEC-03', async () => {
  test.skip(
    true,
    'Authentication failures are not submitted to the production login without an approved non-production test account.'
  );
});

test('TC-N11 Password value is not exposed while masked @REQ-SEC-04', async ({
  loginPage
}) => {
  const password = 'Sample-not-a-real-password';
  await loginPage.enterPassword(password);
  await expect(loginPage.password).toHaveAttribute('type', 'password');
  await expect(loginPage.password).toHaveValue(password);
});

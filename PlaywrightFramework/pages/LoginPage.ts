import type { Locator, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class LoginPage extends BasePage {
  readonly loginPath = '/#/login';
  readonly dashboardPath = '/#/dashboard';

  readonly email: Locator;
  readonly password: Locator;
  readonly passwordToggle: Locator;
  readonly rememberMe: Locator;
  readonly rememberMeLabel: Locator;
  readonly signInButton: Locator;
  readonly notificationMessage: Locator;
  readonly forgotPassword: Locator;
  readonly ssoButton: Locator;
  readonly googleSignInButton: Locator;
  readonly passkeySignInButton: Locator;
  readonly freeTrialLink: Locator;

  constructor(page: Page) {
    super(page);
    this.email = page.locator('//input[@id="login-username"]');
    this.password = page.locator('//input[@id="login-password"]');
    this.passwordToggle = page.locator(
      '//button[@onclick="login.togglePasswordVisibility()"]'
    );
    this.rememberMe = page.locator('//input[@id="checkbox-remember"]');
    this.rememberMeLabel = page.locator('//label[@for="checkbox-remember"]');
    this.signInButton = page.locator('//button[@id="js-login-btn"]');
    this.notificationMessage = page.locator(
      '//div[@id="js-notification-box-msg"]'
    );
    this.forgotPassword = page.locator(
      '//button[@onclick="login.gotoForgotPasswordView()"]'
    );
    this.ssoButton = page.locator('//button[@onclick="login.goToSSOView()"]');
    this.googleSignInButton = page.locator(
      '//button[@id="js-google-signin-btn"]'
    );
    this.passkeySignInButton = page.locator(
      '//button[@onclick="login.loginUsingPasskey()"]'
    );
    this.freeTrialLink = page.locator(
      '//a[normalize-space(.)="Start a FREE TRIAL" and contains(@href,"wingify.com/free-trial")]'
    );
  }

  async navigate(): Promise<void> {
    await this.navigateTo(this.loginPath);
  }

  async enterEmail(email: string): Promise<void> {
    await this.withErrorContext('Enter email', () => this.email.fill(email));
  }

  async enterPassword(password: string): Promise<void> {
    await this.withErrorContext('Enter password', () =>
      this.password.fill(password)
    );
  }

  async togglePasswordVisibility(): Promise<void> {
    await this.withErrorContext('Toggle password visibility', () =>
      this.passwordToggle.click()
    );
  }

  async setRememberMe(checked: boolean): Promise<void> {
    await this.withErrorContext('Set Remember me', async () => {
      if (await this.rememberMe.isVisible()) {
        await this.rememberMe.setChecked(checked);
        return;
      }

      const currentlyChecked = await this.rememberMe.isChecked();
      if (currentlyChecked !== checked) {
        await this.rememberMeLabel.click();
      }
    });
  }

  async clickSignIn(): Promise<void> {
    await this.withErrorContext('Submit sign-in form', () =>
      this.signInButton.click()
    );
  }

  async login(
    email: string,
    password: string,
    rememberMe = false
  ): Promise<void> {
    await this.withErrorContext('Enter login credentials', async () => {
      await this.enterEmail(email);
      await this.enterPassword(password);
      await this.setRememberMe(rememberMe);
    });
    await this.clickSignIn();
  }

  async openForgotPassword(): Promise<void> {
    await this.withErrorContext('Open Forgot Password', () =>
      this.forgotPassword.click()
    );
  }

  async openSso(): Promise<void> {
    await this.withErrorContext('Open SSO sign-in', () =>
      this.ssoButton.click()
    );
  }

  async clickGoogleSignIn(): Promise<void> {
    await this.withErrorContext('Open Google sign-in', () =>
      this.googleSignInButton.click()
    );
  }

  async clickPasskeySignIn(): Promise<void> {
    await this.withErrorContext('Open passkey sign-in', () =>
      this.passkeySignInButton.click()
    );
  }

  async submitWithEnter(): Promise<void> {
    await this.withErrorContext('Submit sign-in form with Enter', () =>
      this.password.press('Enter')
    );
  }

  async clearCredentials(): Promise<void> {
    await this.withErrorContext('Clear login credentials', async () => {
      await this.email.clear();
      await this.password.clear();
    });
  }

  async getEmailPlaceholder(): Promise<string | null> {
    return this.withErrorContext('Read email placeholder', () =>
      this.email.getAttribute('placeholder')
    );
  }

  async getPasswordPlaceholder(): Promise<string | null> {
    return this.withErrorContext('Read password placeholder', () =>
      this.password.getAttribute('placeholder')
    );
  }

  async getEmailValue(): Promise<string> {
    return this.withErrorContext('Read email value', () =>
      this.email.inputValue()
    );
  }

  async getPasswordValue(): Promise<string> {
    return this.withErrorContext('Read password value', () =>
      this.password.inputValue()
    );
  }

  async getPasswordType(): Promise<string | null> {
    return this.withErrorContext('Read password field type', () =>
      this.password.getAttribute('type')
    );
  }

  async getNativeEmailValidationMessage(): Promise<string> {
    return this.withErrorContext('Read native email validation message', () =>
      this.email.evaluate((element) => (element as HTMLInputElement).validationMessage)
    );
  }

  async readMessage(locator: Locator): Promise<string> {
    return this.withErrorContext('Read supplied message locator', () =>
      locator.innerText()
    );
  }
}

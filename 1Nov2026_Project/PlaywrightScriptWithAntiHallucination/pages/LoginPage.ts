import type { Locator, Page } from '@playwright/test';
import { BasePage } from './BasePage';

export class LoginPage extends BasePage {
  static readonly loginUrl = 'https://app.wingify.com/#/login';

  readonly email: Locator;
  readonly password: Locator;
  readonly passwordToggle: Locator;
  readonly rememberMeLabel: Locator;
  readonly signInButton: Locator;
  readonly forgotPassword: Locator;
  readonly googleSignInButton: Locator;
  readonly ssoSignInButton: Locator;
  readonly passkeySignInButton: Locator;
  readonly freeTrialLink: Locator;

  constructor(page: Page) {
    super(page);
    this.email = page.locator('//input[@id="login-username"]');
    this.password = page.locator('//input[@id="login-password"]');
    this.passwordToggle = page.locator(
      '//button[@onclick="login.togglePasswordVisibility()"]'
    );
    this.rememberMeLabel = page.locator(
      '//label[@for="checkbox-remember"]'
    );
    this.signInButton = page.locator('//button[@id="js-login-btn"]');
    this.forgotPassword = page.locator(
      '//button[@onclick="login.gotoForgotPasswordView()"]'
    );
    this.googleSignInButton = page.locator(
      '//button[@id="js-google-signin-btn"]'
    );
    this.ssoSignInButton = page.locator(
      '//button[@onclick="login.goToSSOView()"]'
    );
    this.passkeySignInButton = page.locator(
      '//button[@onclick="login.loginUsingPasskey()"]'
    );
    this.freeTrialLink = page.locator(
      '//a[contains(@href,"wingify.com/free-trial")]'
    );
  }

  async navigate(): Promise<void> {
    await this.withErrorContext('Navigate to VWO login page', async () => {
      await this.page.goto(LoginPage.loginUrl);
      await this.email.waitFor({ state: 'visible' });
      await this.signInButton.waitFor({ state: 'visible' });
    });
  }

  async currentUrl(): Promise<string> {
    return this.page.url();
  }

  async emailType(): Promise<string | null> {
    return this.email.getAttribute('type');
  }

  async emailPlaceholder(): Promise<string | null> {
    return this.email.getAttribute('placeholder');
  }

  async passwordType(): Promise<string | null> {
    return this.password.getAttribute('type');
  }

  async passwordPlaceholder(): Promise<string | null> {
    return this.password.getAttribute('placeholder');
  }

  async enterSamplePassword(): Promise<void> {
    await this.withErrorContext('Enter non-authenticating sample password', () =>
      this.password.fill('Sample-not-a-real-password')
    );
  }

  async togglePasswordVisibility(): Promise<void> {
    await this.withErrorContext('Toggle password visibility', () =>
      this.passwordToggle.click()
    );
  }

  async signInText(): Promise<string> {
    return (await this.signInButton.innerText()).trim();
  }

  async isRememberMeLabelVisible(): Promise<boolean> {
    return this.rememberMeLabel.isVisible();
  }

  async forgotPasswordText(): Promise<string> {
    return (await this.forgotPassword.innerText()).trim();
  }

  async isGoogleSignInVisible(): Promise<boolean> {
    return this.googleSignInButton.isVisible();
  }

  async isSsoSignInVisible(): Promise<boolean> {
    return this.ssoSignInButton.isVisible();
  }

  async isPasskeySignInVisible(): Promise<boolean> {
    return this.passkeySignInButton.isVisible();
  }

  async freeTrialHref(): Promise<string | null> {
    return this.freeTrialLink.getAttribute('href');
  }
}

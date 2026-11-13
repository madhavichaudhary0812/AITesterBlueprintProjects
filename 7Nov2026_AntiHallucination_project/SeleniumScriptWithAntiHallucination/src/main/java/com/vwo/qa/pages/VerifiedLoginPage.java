package com.vwo.qa.pages;

import com.vwo.qa.config.TestConfig;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class VerifiedLoginPage extends BasePage {
    public static final String LOGIN_URL = TestConfig.loginUrl();

    @FindBy(xpath = "//input[@id='login-username']")
    private WebElement email;

    @FindBy(xpath = "//input[@id='login-password']")
    private WebElement password;

    @FindBy(xpath = "//button[@onclick='login.togglePasswordVisibility()']")
    private WebElement passwordToggle;

    @FindBy(xpath = "//label[@for='checkbox-remember']")
    private WebElement rememberMeLabel;

    @FindBy(xpath = "//button[@id='js-login-btn']")
    private WebElement signInButton;

    @FindBy(xpath = "//button[@onclick='login.gotoForgotPasswordView()']")
    private WebElement forgotPassword;

    @FindBy(xpath = "//button[@id='js-google-signin-btn']")
    private WebElement googleSignIn;

    @FindBy(xpath = "//button[@onclick='login.goToSSOView()']")
    private WebElement ssoSignIn;

    @FindBy(xpath = "//button[@onclick='login.loginUsingPasskey()']")
    private WebElement passkeySignIn;

    @FindBy(xpath = "//a[contains(@href,'wingify.com/free-trial')]")
    private List<WebElement> freeTrialLinks;

    public VerifiedLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        navigateTo(LOGIN_URL);
        perform("Wait for login form", () -> {
            wait.until(ExpectedConditions.visibilityOf(email));
            wait.until(ExpectedConditions.visibilityOf(signInButton));
            return null;
        });
    }

    public String currentUrl() {
        return perform("Read current URL", driver::getCurrentUrl);
    }

    public String emailType() {
        return perform("Read email type", () -> email.getAttribute("type"));
    }

    public String emailPlaceholder() {
        return perform("Read email placeholder", () -> email.getAttribute("placeholder"));
    }

    public String passwordType() {
        return perform("Read password type", () -> password.getAttribute("type"));
    }

    public String passwordPlaceholder() {
        return perform(
            "Read password placeholder",
            () -> password.getAttribute("placeholder")
        );
    }

    public void enterSamplePassword() {
        perform("Enter non-authenticating sample password", () -> {
            password.clear();
            password.sendKeys("Sample-not-a-real-password");
            return null;
        });
    }

    public void togglePasswordVisibility() {
        click(passwordToggle, "password visibility toggle");
    }

    public String signInText() {
        return perform("Read Sign in label", () -> signInButton.getText().trim());
    }

    public boolean isRememberMeLabelVisible() {
        return perform(
            "Check Remember me label visibility",
            () -> wait.until(ExpectedConditions.visibilityOf(rememberMeLabel)).isDisplayed()
        );
    }

    public String forgotPasswordText() {
        return perform(
            "Read Forgot Password label",
            () -> forgotPassword.getText().trim()
        );
    }

    public boolean isGoogleSignInVisible() {
        return perform(
            "Check Google sign-in visibility",
            () -> wait.until(ExpectedConditions.visibilityOf(googleSignIn)).isDisplayed()
        );
    }

    public boolean isSsoSignInVisible() {
        return perform(
            "Check SSO sign-in visibility",
            () -> wait.until(ExpectedConditions.visibilityOf(ssoSignIn)).isDisplayed()
        );
    }

    public boolean isPasskeySignInVisible() {
        return perform(
            "Check passkey sign-in visibility",
            () -> wait.until(ExpectedConditions.visibilityOf(passkeySignIn)).isDisplayed()
        );
    }

    public String freeTrialHref() {
        return perform("Read free trial link", () -> {
            WebElement visibleLink = wait.until(currentDriver ->
                freeTrialLinks.stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst()
                    .orElse(null)
            );
            return visibleLink.getAttribute("href");
        });
    }
}

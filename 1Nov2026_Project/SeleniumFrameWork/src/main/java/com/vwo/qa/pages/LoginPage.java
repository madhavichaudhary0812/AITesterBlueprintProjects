package com.vwo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.vwo.qa.config.TestConfig;
import java.util.List;

public final class LoginPage extends BasePage {
    public static final String LOGIN_PATH = "/#/login";
    public static final String DASHBOARD_PATH = "/#/dashboard";

    @FindBy(xpath = "//input[@id='login-username']")
    private WebElement email;

    @FindBy(xpath = "//input[@id='login-password']")
    private WebElement password;

    @FindBy(xpath = "//button[@onclick='login.togglePasswordVisibility()']")
    private WebElement passwordToggle;

    @FindBy(xpath = "//input[@id='checkbox-remember']")
    private WebElement rememberMe;

    @FindBy(xpath = "//label[@for='checkbox-remember']")
    private WebElement rememberMeLabel;

    @FindBy(xpath = "//button[@id='js-login-btn']")
    private WebElement signInButton;

    @FindBy(xpath = "//button[@onclick='login.gotoForgotPasswordView()']")
    private WebElement forgotPassword;

    @FindBy(xpath = "//button[@onclick='login.goToSSOView()']")
    private WebElement ssoButton;

    @FindBy(xpath = "//button[@id='js-google-signin-btn']")
    private WebElement googleSignInButton;

    @FindBy(xpath = "//button[@onclick='login.loginUsingPasskey()']")
    private WebElement passkeySignInButton;

    @FindBy(xpath = "//a[contains(@href,'wingify.com/free-trial')]")
    private List<WebElement> freeTrialLinks;

    @FindBy(xpath = "//div[@id='js-notification-box-msg']")
    private WebElement notificationMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        navigateTo(TestConfig.loginUrl());
        perform("Wait for login form", () -> {
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(email));
            new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(signInButton));
            return null;
        });
    }

    public void enterEmail(String value) {
        enterText(email, value, "email");
    }

    public void enterPassword(String value) {
        enterText(password, value, "password");
    }

    public void enterCredentials(String emailValue, String passwordValue) {
        enterEmail(emailValue);
        enterPassword(passwordValue);
    }

    public void submit() {
        click(signInButton, "Sign in");
    }

    public void login(String emailValue, String passwordValue) {
        enterCredentials(emailValue, passwordValue);
        submit();
    }

    public void togglePasswordVisibility() {
        click(passwordToggle, "password visibility toggle");
    }

    public void setRememberMe(boolean checked) {
        perform("Set Remember me to " + checked, () -> {
            if (rememberMe.isSelected() != checked) {
                click(rememberMeLabel, "Remember me label");
            }
            return null;
        });
    }

    public void clickForgotPassword() {
        click(forgotPassword, "Forgot Password");
    }

    public void clickSso() {
        click(ssoButton, "Sign in using SSO");
    }

    public void clickGoogleSignIn() {
        click(googleSignInButton, "Sign in with Google");
    }

    public void clickPasskeySignIn() {
        click(passkeySignInButton, "Sign in with Passkey");
    }

    public void submitWithEnter() {
        perform("Submit with Enter from password field", () -> {
            password.sendKeys(org.openqa.selenium.Keys.ENTER);
            return null;
        });
    }

    public String emailType() {
        return perform("Read email input type", () -> email.getAttribute("type"));
    }

    public String emailPlaceholder() {
        return perform(
            "Read email placeholder",
            () -> email.getAttribute("placeholder")
        );
    }

    public String passwordPlaceholder() {
        return perform(
            "Read password placeholder",
            () -> password.getAttribute("placeholder")
        );
    }

    public String passwordType() {
        return perform(
            "Read password input type",
            () -> password.getAttribute("type")
        );
    }

    public String emailValue() {
        return perform("Read email value", () -> email.getAttribute("value"));
    }

    public String passwordValue() {
        return perform("Read password value", () -> password.getAttribute("value"));
    }

    public boolean isRememberMeSelected() {
        return perform("Read Remember me state", rememberMe::isSelected);
    }

    public boolean isEmailVisible() {
        return perform("Check email visibility", email::isDisplayed);
    }

    public boolean isPasswordVisible() {
        return perform("Check password visibility", password::isDisplayed);
    }

    public boolean isRememberMeLabelVisible() {
        return perform("Check Remember me visibility", rememberMeLabel::isDisplayed);
    }

    public boolean isSignInVisible() {
        return perform("Check Sign in visibility", signInButton::isDisplayed);
    }

    public boolean isGoogleSignInVisible() {
        return perform("Check Google sign-in visibility", googleSignInButton::isDisplayed);
    }

    public boolean isFreeTrialVisible() {
        return perform(
            "Check free trial link visibility",
            () -> findVisibleFreeTrialLink().isDisplayed()
        );
    }

    public String freeTrialHref() {
        return perform(
            "Read free trial link",
            () -> findVisibleFreeTrialLink().getAttribute("href")
        );
    }

    public String forgotPasswordText() {
        return perform("Read Forgot Password text", forgotPassword::getText);
    }

    public String notificationText() {
        return perform(
            "Read authentication notification",
            () -> new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(notificationMessage))
                .getText()
        );
    }

    public String currentUrl() {
        return perform("Read current URL", driver::getCurrentUrl);
    }

    private WebElement findVisibleFreeTrialLink() {
        return new WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(currentDriver ->
                freeTrialLinks.stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst()
                    .orElse(null)
            );
    }
}

package com.vwo.qa.tests;

import com.vwo.qa.config.TestConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.time.Duration;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("VWO Login")
@Feature("Login page")
public final class LoginValidTest extends BaseTest {
    @Test(description = "TC-P01 Login page loads at the application URL @REQ-UI-01")
    @Story("REQ-UI-01")
    @Description("Verifies the login route and page title.")
    public void loginPageLoadsAtApplicationUrl() {
        assertTrue(loginPage.currentUrl().endsWith("#/login"));
        assertEquals(driver.getTitle(), "Login - Wingify");
    }

    @Test(description = "TC-P02 Login controls are visible @REQ-AUTH-01")
    @Story("REQ-AUTH-01")
    @Description("Checks that the email, password, Remember me, and Sign in controls are visible.")
    public void loginControlsAreVisible() {
        assertTrue(loginPage.isEmailVisible());
        assertTrue(loginPage.isPasswordVisible());
        assertTrue(loginPage.isRememberMeLabelVisible());
        assertTrue(loginPage.isSignInVisible());
    }

    @Test(description = "TC-P03 Email accepts input and uses email type @REQ-VAL-02")
    @Story("REQ-VAL-02")
    @Description("Checks the email field type and accepts a non-authenticating sample value.")
    public void emailFieldAcceptsInput() {
        assertEquals(loginPage.emailType(), "email");
        loginPage.enterEmail("qa@example.com");
        assertEquals(loginPage.emailValue(), "qa@example.com");
    }

    @Test(description = "TC-P04 Password field accepts input @REQ-AUTH-01")
    @Story("REQ-AUTH-01")
    @Description("Checks password entry using a non-authenticating sample value.")
    public void passwordFieldAcceptsInput() {
        loginPage.enterPassword("Sample-not-a-real-password");
        assertEquals(loginPage.passwordValue(), "Sample-not-a-real-password");
    }

    @Test(description = "TC-P17 User can enter email and password together @REQ-AUTH-01 @REQ-VAL-02")
    @Story("REQ-AUTH-01")
    @Description("Enters sample email and password together without submitting the form.")
    public void userCanEnterEmailAndPasswordTogether() {
        loginPage.enterCredentials("qa.demo@example.com", "NotARealPassword-123!");
        assertEquals(loginPage.emailValue(), "qa.demo@example.com");
        assertEquals(loginPage.passwordValue(), "NotARealPassword-123!");
        assertEquals(loginPage.passwordType(), "password");
    }

    @Test(description = "TC-P05 Password is masked and visibility toggle works @REQ-SEC-04")
    @Story("REQ-SEC-04")
    @Description("Verifies password masking and the verified visibility toggle.")
    public void passwordMaskingCanBeToggled() {
        loginPage.enterPassword("Sample-not-a-real-password");
        assertEquals(loginPage.passwordType(), "password");
        loginPage.togglePasswordVisibility();
        assertEquals(loginPage.passwordType(), "text");
        loginPage.togglePasswordVisibility();
        assertEquals(loginPage.passwordType(), "password");
    }

    @Test(description = "TC-P06 Remember me can be checked and unchecked @REQ-AUTH-03")
    @Story("REQ-AUTH-03")
    @Description("Checks Remember me by clicking its visible label.")
    public void rememberMeCanBeCheckedAndUnchecked() {
        loginPage.setRememberMe(true);
        assertTrue(loginPage.isRememberMeSelected());
        loginPage.setRememberMe(false);
        assertFalse(loginPage.isRememberMeSelected());
    }

    @Test(description = "TC-P07 Remember me label toggles the checkbox @REQ-UX-03")
    @Story("REQ-UX-03")
    @Description("Verifies the visible Remember me label toggles its checkbox.")
    public void rememberMeLabelTogglesCheckbox() {
        loginPage.setRememberMe(false);
        loginPage.setRememberMe(true);
        assertTrue(loginPage.isRememberMeSelected());
        loginPage.setRememberMe(false);
        assertFalse(loginPage.isRememberMeSelected());
    }

    @Test(description = "TC-P08 Valid credentials redirect to dashboard @REQ-AUTH-01 @REQ-INT-01")
    @Story("REQ-INT-01")
    @Description("Signs in with authorized test credentials and checks the dashboard route.")
    public void validCredentialsRedirectToDashboard() {
        requireCredentials();
        loginPage.login(TestConfig.email(), TestConfig.password());
        wait.withTimeout(Duration.ofSeconds(20))
            .until(ExpectedConditions.urlContains("#/dashboard"));
        assertTrue(loginPage.currentUrl().contains("#/dashboard"));
    }

    @Test(description = "TC-P09 Forgot Password opens the reset view @REQ-PWD-01")
    @Story("REQ-PWD-01")
    @Description("Skipped until the reset view content and stable assertions are verified.")
    public void forgotPasswordOpensResetView() {
        throw new SkipException(
            "Reset view behavior and stable assertions have not yet been verified."
        );
    }

    @Test(description = "TC-P10 Free trial link points to the registration page @REQ-REG-01")
    @Story("REQ-REG-01")
    @Description("Checks the verified free trial link target without opening the external page.")
    public void freeTrialLinkPointsToRegistrationPage() {
        assertTrue(loginPage.isFreeTrialVisible());
        assertTrue(loginPage.freeTrialHref().contains("wingify.com/free-trial"));
    }

    @Test(description = "TC-P11 SSO entry point opens the SSO view @REQ-AUTH-05")
    @Story("REQ-AUTH-05")
    @Description("Skipped until the SSO view is verified; does not open an external identity provider.")
    public void ssoEntryPointOpensSsoView() {
        throw new SkipException(
            "SSO view behavior and stable assertions have not yet been verified."
        );
    }

    @Test(description = "TC-P12 Google sign-in entry point is visible @REQ-AUTH-06")
    @Story("REQ-AUTH-06")
    @Description("Checks the Google sign-in entry point without opening the provider.")
    public void googleSignInEntryPointIsVisible() {
        assertTrue(loginPage.isGoogleSignInVisible());
    }

    @Test(description = "TC-P13 Login form is usable at mobile viewport @REQ-UX-01")
    @Story("REQ-UX-01")
    @Description("Resizes Chrome to a mobile-sized viewport and checks core form controls.")
    public void loginFormIsUsableAtMobileViewport() {
        driver.manage().window().setSize(new Dimension(390, 844));
        assertTrue(loginPage.isEmailVisible());
        assertTrue(loginPage.isPasswordVisible());
        assertTrue(loginPage.isSignInVisible());
    }

    @Test(description = "TC-P14 Keyboard navigation and Enter submits form @REQ-A11Y-03")
    @Story("REQ-A11Y-03")
    @Description("Skipped until submission can be tested with an approved test account.")
    public void keyboardNavigationAndEnterSubmission() {
        throw new SkipException(
            "Enter submission needs authorized test credentials to verify its result."
        );
    }

    @Test(description = "TC-P15 Login placeholders match the English UI @REQ-UI-01")
    @Story("REQ-UI-01")
    @Description("Checks the verified English placeholders.")
    public void placeholdersMatchEnglishUi() {
        assertEquals(loginPage.emailPlaceholder(), "Enter email ID");
        assertEquals(loginPage.passwordPlaceholder(), "Enter password");
    }

    @Test(description = "TC-P16 Forgot Password button text is correct @REQ-PWD-01")
    @Story("REQ-PWD-01")
    @Description("Checks the verified Forgot Password button text.")
    public void forgotPasswordButtonTextIsCorrect() {
        assertEquals(loginPage.forgotPasswordText(), "Forgot Password?");
    }

    @Test(description = "TC-C04 Login page uses HTTPS @REQ-SEC-02")
    @Story("REQ-SEC-02")
    @Description("Checks the application login page protocol.")
    public void loginPageUsesHttps() {
        assertTrue(loginPage.currentUrl().startsWith("https://"));
    }

    private void requireCredentials() {
        if (TestConfig.email().isBlank() || TestConfig.password().isBlank()) {
            throw new SkipException(
                "Set VWO_EMAIL and VWO_PASSWORD in the local environment or .env file."
            );
        }
    }
}

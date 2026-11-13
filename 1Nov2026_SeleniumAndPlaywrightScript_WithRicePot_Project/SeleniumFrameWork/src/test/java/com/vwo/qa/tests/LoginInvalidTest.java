package com.vwo.qa.tests;

import com.vwo.qa.config.TestConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.SkipException;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Epic("VWO Login")
@Feature("Login validation and authentication errors")
public final class LoginInvalidTest extends BaseTest {
    @Test(description = "TC-N01 Unregistered email authentication is rejected @REQ-VAL-04")
    @Story("REQ-VAL-04")
    @Description("Skipped by default to avoid unapproved authentication failures against production.")
    public void unregisteredEmailAuthenticationIsRejected() {
        requireLiveAuthenticationOptIn();
        loginPage.login("vwo.selenium.unregistered@example.com", "NotARealPassword-123!");
        assertEquals(
            loginPage.notificationText(),
            "Your email, password, IP address or location did not match"
        );
    }

    @Test(description = "TC-N02 Wrong password for registered email shows error @REQ-VAL-04")
    @Story("REQ-VAL-04")
    @Description("Skipped by default; needs a dedicated test account and explicit production-test opt-in.")
    public void wrongPasswordForRegisteredEmailShowsError() {
        requireLiveAuthenticationOptIn();
        if (TestConfig.email().isBlank()) {
            throw new SkipException("Set VWO_EMAIL to an authorized test account.");
        }
        loginPage.login(TestConfig.email(), "Intentionally-Wrong-Password-123!");
        assertEquals(
            loginPage.notificationText(),
            "Your email, password, IP address or location did not match"
        );
    }

    @Test(description = "TC-N03 Invalid credentials show an error @REQ-VAL-04")
    @Story("REQ-VAL-04")
    @Description("Skipped by default to avoid unapproved authentication failures against production.")
    public void invalidCredentialsShowError() {
        requireLiveAuthenticationOptIn();
        loginPage.login("invalid.selenium.user@example.com", "NotARealPassword-123!");
        assertEquals(
            loginPage.notificationText(),
            "Your email, password, IP address or location did not match"
        );
    }

    @Test(description = "TC-N04 Empty email with password is validated @REQ-VAL-01")
    @Story("REQ-VAL-01")
    @Description("Skipped until field-validation behavior is verified in the target application.")
    public void emptyEmailWithPasswordIsValidated() {
        throw new SkipException("Field-validation feedback has not been verified.");
    }

    @Test(description = "TC-N05 Email with empty password is validated @REQ-VAL-01")
    @Story("REQ-VAL-01")
    @Description("Skipped until field-validation behavior is verified in the target application.")
    public void emailWithEmptyPasswordIsValidated() {
        throw new SkipException("Field-validation feedback has not been verified.");
    }

    @Test(description = "TC-N06 Empty credentials are validated @REQ-VAL-01")
    @Story("REQ-VAL-01")
    @Description("Skipped until field-validation behavior is verified in the target application.")
    public void emptyCredentialsAreValidated() {
        throw new SkipException("Field-validation feedback has not been verified.");
    }

    @Test(description = "TC-N07 Invalid email formats show validation @REQ-VAL-02")
    @Story("REQ-VAL-02")
    @Description("Skipped until validation feedback behavior is verified.")
    public void invalidEmailFormatShowsValidation() {
        throw new SkipException("Invalid email validation behavior has not been verified.");
    }

    @Test(description = "TC-N08 Validation feedback appears on blur @REQ-VAL-01")
    @Story("REQ-VAL-01")
    @Description("Skipped until a stable, verified validation assertion is available.")
    public void validationFeedbackAppearsOnBlur() {
        throw new SkipException("Blur validation behavior has not been verified.");
    }

    @Test(description = "TC-N09 Gmail login shows the reported authentication error @REQ-VAL-04")
    @Story("REQ-VAL-04")
    @Description("Checks the reported failure message; requires credentials and explicit live-auth opt-in.")
    public void gmailLoginShowsReportedAuthenticationError() {
        requireLiveAuthenticationOptIn();
        requireCredentials();
        if (!TestConfig.email().toLowerCase().endsWith("@gmail.com")) {
            throw new SkipException("VWO_EMAIL must be a Gmail address for this scenario.");
        }
        loginPage.login(TestConfig.email(), TestConfig.password());
        assertEquals(
            loginPage.notificationText(),
            "Your email, password, IP address or location did not match"
        );
    }

    @Test(description = "TC-N10 Failed login does not reach dashboard @REQ-SEC-03")
    @Story("REQ-SEC-03")
    @Description("Requires explicit live-auth opt-in and one controlled invalid attempt.")
    public void failedLoginDoesNotReachDashboard() {
        requireLiveAuthenticationOptIn();
        loginPage.login("vwo.selenium.invalid@example.com", "NotARealPassword-123!");
        assertFalse(loginPage.currentUrl().contains("#/dashboard"));
        assertEquals(
            loginPage.notificationText(),
            "Your email, password, IP address or location did not match"
        );
    }

    @Test(description = "TC-N11 Password stays masked by default @REQ-SEC-04")
    @Story("REQ-SEC-04")
    @Description("Verifies a sample password is entered into a masked field without form submission.")
    public void passwordStaysMaskedByDefault() {
        String samplePassword = "Sample-not-a-real-password";
        loginPage.enterPassword(samplePassword);
        assertEquals(loginPage.passwordType(), "password");
        assertEquals(loginPage.passwordValue(), samplePassword);
    }

    private void requireLiveAuthenticationOptIn() {
        if (!TestConfig.liveAuthTestsEnabled()) {
            throw new SkipException(
                "Set VWO_ENABLE_LIVE_AUTH_TESTS=true only for an approved test environment/account."
            );
        }
    }

    private void requireCredentials() {
        if (TestConfig.email().isBlank() || TestConfig.password().isBlank()) {
            throw new SkipException(
                "Set VWO_EMAIL and VWO_PASSWORD in the local environment or .env file."
            );
        }
    }
}

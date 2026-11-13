package com.vwo.qa.tests;

import com.vwo.qa.pages.VerifiedLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.net.URI;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Epic("VWO Login")
@Feature("Evidence-bounded login page checks")
public final class VerifiedLoginPageTest extends BaseTest {
    @Test(description = "AH-S01 Opens the specified VWO login URL")
    @Story("AH-S01")
    @Description("Opens and checks the exact user-specified login URL without submitting the form.")
    public void opensSpecifiedLoginUrl() {
        assertEquals(loginPage.currentUrl(), VerifiedLoginPage.LOGIN_URL);
    }

    @Test(description = "AH-S02 Email field has the supplied type and placeholder")
    @Story("AH-S02")
    public void emailFieldMatchesSuppliedDetails() {
        assertEquals(loginPage.emailType(), "email");
        assertEquals(loginPage.emailPlaceholder(), "Enter email ID");
    }

    @Test(description = "AH-S03 Password field has the supplied type and placeholder")
    @Story("AH-S03")
    public void passwordFieldMatchesSuppliedDetails() {
        assertEquals(loginPage.passwordType(), "password");
        assertEquals(loginPage.passwordPlaceholder(), "Enter password");
    }

    @Test(description = "AH-S04 Sign in control has the supplied label")
    @Story("AH-S04")
    public void signInControlMatchesSuppliedLabel() {
        assertEquals(loginPage.signInText(), "Sign in");
    }

    @Test(description = "AH-S05 Remember me label is present without inferring checkbox state")
    @Story("AH-S05")
    public void rememberMeLabelIsVisible() {
        assertTrue(loginPage.isRememberMeLabelVisible());
    }

    @Test(description = "AH-S06 Password visibility toggle changes the supplied field type")
    @Story("AH-S06")
    @Description("Uses only a fake sample password and never submits the form.")
    public void passwordVisibilityToggleChangesFieldType() {
        loginPage.enterSamplePassword();
        assertEquals(loginPage.passwordType(), "password");
        loginPage.togglePasswordVisibility();
        assertEquals(loginPage.passwordType(), "text");
        loginPage.togglePasswordVisibility();
        assertEquals(loginPage.passwordType(), "password");
    }

    @Test(description = "AH-S07 Forgot Password control has the supplied text")
    @Story("AH-S07")
    public void forgotPasswordControlMatchesSuppliedText() {
        assertEquals(loginPage.forgotPasswordText(), "Forgot Password?");
    }

    @Test(description = "AH-S08 Google sign-in entry point is visible")
    @Story("AH-S08")
    public void googleSignInEntryPointIsVisible() {
        assertTrue(loginPage.isGoogleSignInVisible());
    }

    @Test(description = "AH-S09 SSO entry point is visible without opening it")
    @Story("AH-S09")
    public void ssoEntryPointIsVisible() {
        assertTrue(loginPage.isSsoSignInVisible());
    }

    @Test(description = "AH-S10 Passkey entry point is visible without starting authentication")
    @Story("AH-S10")
    public void passkeyEntryPointIsVisible() {
        assertTrue(loginPage.isPasskeySignInVisible());
    }

    @Test(description = "AH-S11 Free trial link href contains the supplied destination fragment")
    @Story("AH-S11")
    public void freeTrialLinkMatchesSuppliedDestination() {
        assertTrue(loginPage.freeTrialHref().contains("wingify.com/free-trial"));
    }

    @Test(description = "AH-S12 Configured login URL uses the supplied HTTPS scheme")
    @Story("AH-S12")
    public void configuredLoginUrlUsesHttps() {
        assertEquals(URI.create(VerifiedLoginPage.LOGIN_URL).getScheme(), "https");
    }
}

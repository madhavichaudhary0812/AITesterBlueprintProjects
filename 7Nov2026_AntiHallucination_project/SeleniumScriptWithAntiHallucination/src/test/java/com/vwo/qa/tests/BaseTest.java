package com.vwo.qa.tests;

import com.vwo.qa.config.TestConfig;
import com.vwo.qa.pages.VerifiedLoginPage;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    // Hide Selenium CDP version-mismatch warnings (harmless: these tests do not use DevTools).
    private static final java.util.logging.Logger DEVTOOLS_LOGGER =
        java.util.logging.Logger.getLogger("org.openqa.selenium.devtools");
    private static final java.util.logging.Logger CHROMIUM_LOGGER =
        java.util.logging.Logger.getLogger("org.openqa.selenium.chromium");

    static {
        DEVTOOLS_LOGGER.setLevel(java.util.logging.Level.SEVERE);
        CHROMIUM_LOGGER.setLevel(java.util.logging.Level.SEVERE);
    }

    protected WebDriver driver;
    protected VerifiedLoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        if (TestConfig.headless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1440,1000");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        try {
            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
            loginPage = new VerifiedLoginPage(driver);
            loginPage.open();
        } catch (RuntimeException exception) {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
            throw new IllegalStateException(
                "Could not start Chrome or load the VWO login page.",
                exception
            );
        }
    }

    @AfterMethod(alwaysRun = true)
    public void stopBrowser(ITestResult result) {
        if (driver == null) {
            return;
        }
        if (!result.isSuccess()) {
            attachFailureScreenshot(result);
        }
        try {
            driver.quit();
        } catch (RuntimeException exception) {
            if (result.isSuccess()) {
                result.setThrowable(
                    new IllegalStateException("Could not close browser cleanly.", exception)
                );
                result.setStatus(ITestResult.FAILURE);
            } else {
                System.err.println("Browser cleanup also failed: " + exception.getMessage());
            }
        } finally {
            driver = null;
        }
    }

    private void attachFailureScreenshot(ITestResult result) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(
                "Failure screenshot",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
            );
            Path directory = Path.of("target", "screenshots");
            Files.createDirectories(directory);
            String safeName = result.getMethod().getMethodName()
                .replaceAll("[^A-Za-z0-9._-]", "_");
            Files.write(directory.resolve(safeName + ".png"), screenshot);
        } catch (RuntimeException | IOException exception) {
            System.err.println("Could not capture failure screenshot: " + exception.getMessage());
        }
    }
}

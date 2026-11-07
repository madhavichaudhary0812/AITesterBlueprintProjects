package com.vwo.qa.pages;

import java.time.Duration;
import java.util.function.Supplier;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected final WebDriver driver;
    private final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void navigateTo(String url) {
        perform("Navigate to " + url, () -> {
            driver.get(url);
            return null;
        });
    }

    protected void click(WebElement element, String description) {
        perform("Click " + description, () -> {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            return null;
        });
    }

    protected void enterText(WebElement element, String value, String description) {
        perform("Enter " + description, () -> {
            WebElement readyElement = wait.until(
                ExpectedConditions.visibilityOf(element)
            );
            readyElement.clear();
            readyElement.sendKeys(value);
            return null;
        });
    }

    protected <T> T perform(String action, Supplier<T> operation) {
        try {
            return operation.get();
        } catch (TimeoutException exception) {
            throw new IllegalStateException(action + " timed out.", exception);
        } catch (RuntimeException exception) {
            throw new IllegalStateException(action + " failed.", exception);
        }
    }
}

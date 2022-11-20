package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UIHelpers {
    private final EdgeDriver driver;
    private final WebDriverWait wait;

    public UIHelpers(EdgeDriver _driver) {
        driver = _driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setByElementText(By element, String value) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).sendKeys(value);
    }

    public void setWebElementText(WebElement element, String value) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(value);
    }

    public void clickElement(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
        driver.findElement(element).click();
    }

    public void setDropdownElement(By element, String value) throws InterruptedException {
        Actions keyDown = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));

        if (!value.isBlank()) {
            driver.findElement(element).click();
            Thread.sleep(200);
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
            keyDown.click();
        }
    }

    public void takeScreenshot(By element, String testCaseName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            CaptureHelpers.captureScreenshot(driver, testCaseName);
        }
        catch (Exception exception) {

        }
    }
}

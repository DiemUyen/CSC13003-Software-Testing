package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
        int index = 1;
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));

        if (!value.isBlank()) {
            driver.findElement(element).click();
            Thread.sleep(200);
            var elements = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@role='listbox']//div"))));
            do {
                var temp = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@role='listbox']//div"))));
                if (temp.get(index).getText().equals(value)) {
                    temp.get(index).click();
                    return;
                }
                index++;
            } while(index < elements.size());
        }
    }

    public void setDynamicDropdownElement(WebElement element, String value) throws InterruptedException {
        String name = value;
        int index = 0;
        Actions actions = new Actions(driver);

        if (!value.isBlank()) {
            element.sendKeys(name.substring(0, 4));
            List<WebElement> elements;
            elements = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@role='listbox']//div"))));
            do {
                List<WebElement> temp;
                Thread.sleep(4000);
                actions.moveToElement(driver.findElement(By.xpath("//div[@role='listbox']"))).build().perform();
                temp = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@role='listbox']//div"))));
                String text = temp.get(index).getText();
                if (value.equals(text)) {
                    for (int j = 0; j <= index; j++)
                        actions.sendKeys(Keys.DOWN).build().perform();
                    actions.click().build().perform();
                    return;
                }
                index++;
            } while(index < elements.size());

            element.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
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

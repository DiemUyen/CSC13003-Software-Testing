package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
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
        Actions keyDown = new Actions(driver);
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

        element.sendKeys(name.toLowerCase().substring(0, 1));
        //List<WebElement> valueList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='listbox']")));
        List<WebElement> valueList = driver.findElements(By.xpath("//li"));
        List<WebElement> children = new ArrayList<>();
        for (WebElement item: valueList) {
            try {
                children = wait.until(ExpectedConditions.visibilityOfAllElements(item.findElements(By.tagName("li"))));
            }
            catch (Exception exception) {
                System.out.println(item);
            }

            for( WebElement child : children) {
                String text = child.getText();
                if (child.getText().contains(value)) {
                    //child.click();
                }
            }
        }

        /*Actions keyDown = new Actions(driver);

        if (!value.isBlank()) {
            //Thread.sleep(200);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            element.sendKeys(name.toLowerCase().substring(0, 1));
            List<WebElement> valueList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@role='listbox']")));
            //valueList.get(0).click();
            keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER)).build().perform();
            keyDown.click();
        }*/
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

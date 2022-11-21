package testcases;

import helpers.ExcelHelpers;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AddPerformanceTrackerPage;
import pages.LogInPage;

import java.time.Duration;

public class PerformanceTrackerTest {
    private EdgeDriver driver;
    private ExcelHelpers excelHelpers;
    private WebDriverWait wait;
    private LogInPage logInPage;
    private AddPerformanceTrackerPage performanceTrackerPage;
    private final String logInUrl = "http://localhost/orangehrm-5.1";
    private final String addPerformanceTrackerUrl = "http://localhost/orangehrm-5.1/web/index.php/performance/addPerformanceTracker";

    @BeforeTest
    public void setUpBrowser() {
        driver = new EdgeDriver();
        //driver.manage().window().maximize();
        excelHelpers = new ExcelHelpers();
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(0));
    }

    @BeforeClass
    public void logInPage() throws Exception {
        logInPage = new LogInPage(driver);
        driver.get(logInUrl);
        logInPage.logIn("19120426-DiemUyen", "Kcpm_cq193_k1");
        Thread.sleep(1000);
    }

    @Test
    public void addPerformanceTrackerTest() throws Exception {
        excelHelpers.setExcelFilePath("src/test/resources/data_source.xlsx", "Performance Tracker");
        int rowCount = excelHelpers.getRowCount();
        System.out.println(rowCount);
        for (int i = 1; i <= rowCount; i++) {
            driver.navigate().to(addPerformanceTrackerUrl);
            performanceTrackerPage = new AddPerformanceTrackerPage(this.driver);

            String trackerName = excelHelpers.getCellData("Tracker Name", i);
            String employeeName = excelHelpers.getCellData("Employee Name", i);
            String reviewersName = excelHelpers.getCellData("Reviewers Name", i);
            String expectedResult = excelHelpers.getCellData("Expected Result", i);
            performanceTrackerPage.addPerformanceTracker(trackerName, employeeName, reviewersName);
            Thread.sleep(3000);
        }
    }

    @AfterClass
    public void closeBrowser() {
        driver.close();
    }
}

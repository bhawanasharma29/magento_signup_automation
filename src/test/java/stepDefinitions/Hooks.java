package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

    private static WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Navigation should be handled in @Given step, not here
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (driver != null && scenario.isFailed()) {
                takeScreenshot(scenario, "FAILED");
            }
        } catch (Exception e) {
            System.out.println("Failed to take final screenshot: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @AfterStep
    public void takeScreenshotAfterEachStep(Scenario scenario) {
        try {
            if (driver != null && isPageLoaded(driver) && !scenario.isFailed()) {
                takeScreenshot(scenario, "STEP");
            }
        } catch (Exception e) {
            System.out.println("Error capturing step screenshot: " + e.getMessage());
        }
    }

    private void takeScreenshot(Scenario scenario, String tag) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String fileName = scenario.getName().replaceAll(" ", "_") + "_" + tag + "_" + System.currentTimeMillis() + ".png";
        File dest = new File("Screenshots/" + fileName);
        FileUtils.copyFile(src, dest);
        System.out.println("📸 Screenshot saved: " + fileName);
    }

    private boolean isPageLoaded(WebDriver driver) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript("return document.readyState").toString().equals("complete");
        } catch (Exception e) {
            System.out.println("Could not check page load state: " + e.getMessage());
            return false;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}

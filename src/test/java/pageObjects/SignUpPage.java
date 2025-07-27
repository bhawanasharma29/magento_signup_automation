package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import stepDefinitions.Hooks;

import java.time.Duration;
import java.util.List;

public class SignUpPage {

    WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(linkText = "Create an Account")
    WebElement createAccountLink;

    @FindBy(id = "firstname")
    WebElement firstNameInput;

    @FindBy(id = "lastname")
    WebElement lastNameInput;

    @FindBy(id = "email_address")
    WebElement emailInput;

    @FindBy(id = "password")
    WebElement passwordInput;

    @FindBy(id = "password-confirmation")
    WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@title='Create an Account']")
    WebElement createAccountButton;

    // ========== Page Actions ==========

    public void clickCreateAccountLink() {
        try {
            closeAdIfPresent(); // try to close ad if it's there

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(createAccountLink));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", createAccountLink);
            System.out.println("Clicked on 'Create an Account' link.");
        } catch (Exception e) {
            System.out.println("Failed to click 'Create Account' link: " + e.getMessage());
        }
    }

    public void waitForSignUpForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
    }

    public void enterAccountDetails() {
        waitForSignUpForm();
        firstNameInput.sendKeys("Bhawana");
        lastNameInput.sendKeys("Sharma");
        emailInput.sendKeys("bhawana" + System.currentTimeMillis() + "@mail.com"); // dynamic email
        passwordInput.sendKeys("Test@1234");
        confirmPasswordInput.sendKeys("Test@1234");
        handleAdIfPresent();
        createAccountButton.click();
    }

    public void verifyDashboard() {
        // You can add assertion logic here, maybe check title or some dashboard element
        System.out.println("Account created successfully. Dashboard loaded.");
    }
    
    public boolean isLastNameFieldVisible() {
        return lastNameInput.isDisplayed();  
    }


    public void closeAdIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[id*='ad_iframe']")));

            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[role='button'], .close-button, .close-ad")));
            closeBtn.click();

            driver.switchTo().defaultContent();
            System.out.println("Ad closed.");
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            System.out.println("No ad to close.");
        }
    }
    
    public void handleAdIfPresent() {
        try {
            // Wait max 3 seconds for iframe to appear
            WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(3));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe[id^='aswift']")));

            // Now you're inside the ad iframe — try to close it if there's a button
            List<WebElement> closeButtons = Hooks.getDriver().findElements(By.cssSelector("div[role='button'], .close-button, .dismiss-button"));
            if (!closeButtons.isEmpty()) {
                closeButtons.get(0).click();
                System.out.println("Ad closed.");
            }

            // Switch back to default content
            Hooks.getDriver().switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("No ad present or ad close failed. Skipping ad handling.");
            // Always switch back in case of any error
            try {
                Hooks.getDriver().switchTo().defaultContent();
            } catch (Exception ignored) {}
        }
    }

}

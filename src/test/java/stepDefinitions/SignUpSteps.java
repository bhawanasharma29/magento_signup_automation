package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import io.cucumber.java.en.*;
import pageObjects.SignUpPage;

public class SignUpSteps {

    WebDriver driver;
    SignUpPage signUpPage;

    public SignUpSteps() {
        driver = Hooks.getDriver(); // fetch WebDriver
        signUpPage = new SignUpPage(driver);
    }

    @Given("I am on the Magento home page")
    public void i_am_on_magento_home_page() {
        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().window().maximize();
        System.out.println("Magento home page opened.");
    }

    @When("I navigate to the Create Account page")
    public void i_navigate_to_create_account_page() {
        signUpPage.clickCreateAccountLink();
    }

    @And("I fill in the account details and submit")
    public void i_fill_in_account_details_and_submit() {
        signUpPage.enterAccountDetails();
    }

    @Then("I should see the account dashboard")
    public void i_should_see_account_dashboard() {
        signUpPage.verifyDashboard();
    }
    
    @Given("I am on the Create Account Page")
    public void i_am_on_the_create_account_page() {
        driver.get("https://magento.softwaretestingboard.com/customer/account/create/");
    }
    
    @When("user enters Last name {string}")
    public void user_enters_last_name(String lastName) {
        WebElement lastNameField = driver.findElement(By.id("lastname"));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }
    
    @And("user submit the details")
    public void user_submit_the_details() {
        WebElement submitButton = driver.findElement(By.cssSelector("button[title='Create an Account']"));
        submitButton.click();
    }
    
    
    
    @Then("last name field should be visible")
    public void verifyLastNameFieldIsVisible() {
        Assert.assertTrue(signUpPage.isLastNameFieldVisible());
        System.out.println("Last name field is visible");
    }
}

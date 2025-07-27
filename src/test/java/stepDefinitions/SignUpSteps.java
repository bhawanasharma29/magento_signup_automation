package stepDefinitions;

import org.openqa.selenium.WebDriver;

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

    @When("I fill in the account details and submit")
    public void i_fill_in_account_details_and_submit() {
        signUpPage.enterAccountDetails();
    }

    @Then("I should see the account dashboard")
    public void i_should_see_account_dashboard() {
        signUpPage.verifyDashboard();
    }
}

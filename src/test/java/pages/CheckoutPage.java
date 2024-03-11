package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends CommonPage {

    private final By PAGE_LABEL = By.className("title");
    private final By INPUT_FIRSTNAME = By.id("first-name");
    private final By INPUT_LASTNAME = By.id("last-name");
    private final By INPUT_POSTAL_CODE = By.id("postal-code");
    private final By BUTTON_CONTINUE = By.id("continue");

    private final String FIRST_NAME = "Bob";
    private final String LAST_NAME = "Smith";
    private final String POSTAL_CODE = "LV-1012";


    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getPageLabel() {
        return findElement(PAGE_LABEL).getText();
    }

    public void fillInCheckoutInfo() {
        findElement(INPUT_FIRSTNAME).sendKeys(FIRST_NAME);
        findElement(INPUT_LASTNAME).sendKeys(LAST_NAME);
        findElement(INPUT_POSTAL_CODE).sendKeys(POSTAL_CODE);
    }

    public void clickContinueButton() {
        findElement(BUTTON_CONTINUE).click();
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends CommonPage {

    private final By USERNAME = By.id("user-name");
    private final By PASSWORD = By.id("password");
    private final By BUTTON_LOGIN = By.id("login-button");

    private final String STANDARD_USERNAME = "standard_user";
    private final String STANDARD_PWD = "secret_sauce";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void authorize() {
        findElement(USERNAME).sendKeys(STANDARD_USERNAME);
        findElement(PASSWORD).sendKeys(STANDARD_PWD);
        findElement(BUTTON_LOGIN).click();
    }

    public void loginButtonVisible() {
        findElement(BUTTON_LOGIN);
    }

}

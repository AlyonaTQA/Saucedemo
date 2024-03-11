package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderConfirmPage extends CommonPage {

    private final By PAGE_LABEL = By.className("title");
    private final By COMPLETE_HEADER = By.cssSelector(".complete-header");
    private final By BUTTON_BURGER_MENU = By.id("react-burger-menu-btn");
    private final By BUTTON_LOGOUT = By.id("logout_sidebar_link");

    public OrderConfirmPage(WebDriver driver) {
        super(driver);
    }

    public String getPageLabel() {
        return findElement(PAGE_LABEL).getText();
    }

    public String getCompleteHeaderText() {
        return findElement(COMPLETE_HEADER).getText();
    }

    public void logout() {
        findElement(BUTTON_BURGER_MENU).click();
        findElement(BUTTON_LOGOUT).click();
    }


}

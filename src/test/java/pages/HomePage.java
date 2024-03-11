package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends CommonPage {

    private final By PAGE_LABEL = By.className("title");
    private final String productLocator = ".//div[@class = 'inventory_item_name ' and text()='%s']";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getPageLabel() {
        return findElement(PAGE_LABEL).getText();
    }

    public void clickOnProductByName(String text) {
        String formatLocator = String.format(productLocator, text);
        findElement(By.xpath(formatLocator)).click();
    }

}

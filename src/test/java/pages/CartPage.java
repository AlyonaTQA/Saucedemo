package pages;

import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends CommonPage {
    private final By PAGE_LABEL = By.className("title");
    private final By CART_ITEM = By.className("cart_item");
    private final By BUTTON_REMOVE = By.xpath("//div[contains(@class, 'cart_item')]//button[text()='Remove']");
    private final String removeButtonLocator = "(//div[contains(@class, 'cart_item')]//button[text()='Remove'])['%d']";
    private final By BUTTON_CHECKOUT = By.id("checkout");
    private final By BUTTON_CONTINUE_SOPPING = By.xpath("//div[contains(@class, 'cart_footer')]//button[text()='Continue Shopping']");

    private final String ITEM_NAME = ".inventory_item_name";
    private final String ITEM_DESCRIPTION = ".inventory_item_desc";
    private final String ITEM_PRICE = ".inventory_item_price";
    private final String CART_QUANTITY = ".cart_quantity";

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getPageLabel() {
        return findElement(PAGE_LABEL).getText();
    }

    public Product getProductByIndex(int index) {
        WebElement cartItem = findElements(CART_ITEM).get(index);
        Product product = new Product();
        product.setName(cartItem.findElement(By.cssSelector(ITEM_NAME)).getText());
        product.setDescription(cartItem.findElement(By.cssSelector(ITEM_DESCRIPTION)).getText());
        String priceText = cartItem.findElement(By.cssSelector(ITEM_PRICE)).getText();
        product.setPrice(Double.parseDouble(priceText.replace("$", "")));
        product.setQuantity(Integer.parseInt(cartItem.findElement(By.cssSelector(CART_QUANTITY)).getText()));

        return product;
    }

    public void clickCheckoutButton() {
        findElement(BUTTON_CHECKOUT).click();
    }

    public void clickRemoveButton() {
        findElement(BUTTON_REMOVE).click();
    }

    public void clickRemoveButton(int number) {
        String formatLocator = String.format(removeButtonLocator, number);
        findElement(By.xpath(formatLocator)).click();
    }

    public void clickContinueShoppingButton() {
        findElement(BUTTON_CONTINUE_SOPPING).click();
    }
}

package pages;

import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends CommonPage {
    private final By BUTTON_BACK = By.id("back-to-products");
    private final By BUTTON_ADD_TO_CART = By.id("add-to-cart-sauce-labs-bolt-t-shirt");
    private final By SHOPPING_CART_BADGE = By.className("shopping_cart_badge");
    private final By SHOPPING_CART = By.className("shopping_cart_link");
    private final By PRODUCT_CONTAINER = By.xpath(".//div[@class = 'inventory_details_desc_container']");

    private final String DETAILS_NAME = ".inventory_details_name";
    private final String DETAILS_DESCRIPTION = ".inventory_details_desc";
    private final String DETAILS_PRICE = ".inventory_details_price";

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getBackButtonText() {
        return findElement(BUTTON_BACK).getText();
    }

    public void clickAddToCartButton() {
        findElement(BUTTON_ADD_TO_CART).click();
    }

    public String getShoppingCartBadge() {
        return findElement(SHOPPING_CART_BADGE).getText();
    }

    public void clickShoppingCart() {
        findElement(SHOPPING_CART).click();
    }

    public Product getProductModel() {
        Product product = new Product();
        WebElement element = findElement(PRODUCT_CONTAINER);
        product.setName(element.findElement(By.cssSelector(DETAILS_NAME)).getText());
        product.setDescription(element.findElement(By.cssSelector(DETAILS_DESCRIPTION)).getText());
        String priceText = element.findElement(By.cssSelector(DETAILS_PRICE)).getText();
        product.setPrice(Double.parseDouble(priceText.replace("$", "")));

        return product;
    }

}

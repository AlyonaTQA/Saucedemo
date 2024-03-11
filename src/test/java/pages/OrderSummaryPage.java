package pages;

import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrderSummaryPage extends CommonPage {

    private final By PAGE_LABEL = By.className("title");
    private final By CART_ITEM = By.className("cart_item");
    private final By TOTAL_PRICE = By.cssSelector(".summary_total_label");
    private final By BUTTON_FINISH = By.id("finish");

    private final String ITEM_NAME = ".inventory_item_name";
    private final String ITEM_DESCRIPTION = ".inventory_item_desc";
    private final String ITEM_PRICE = ".inventory_item_price";
    private final String CART_QUANTITY = ".cart_quantity";
    private final String SUMMARY_TAX_LABEL = ".summary_info .summary_tax_label";

    public OrderSummaryPage(WebDriver driver) {
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

    public Double getTotalPriceAsDouble() {
        return Double.parseDouble(findElement(TOTAL_PRICE).getText().replace("Total: $", ""));
    }

    public void clickFinishButton() {
        findElement(BUTTON_FINISH).click();
    }

    public Double getTaxAsDouble() {
        String taxValue = findElement(By.cssSelector(SUMMARY_TAX_LABEL)).getText()
                .replace("Tax: $", "");
        return Double.parseDouble(taxValue);
    }
}

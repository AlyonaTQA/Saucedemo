import model.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import static org.assertj.core.api.Assertions.*;


public class SauceDemoTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderSummaryPage orderSummaryPage;
    private OrderConfirmPage orderConfirmPage;

    private final String APP_URL = "https://www.saucedemo.com/";

    private final String HOMEPAGE_LABEL = "Products";
    private final String PRODUCT_NAME = "Sauce Labs Bolt T-Shirt";
    private final String PRODUCT_PAGE_LABEL = "Back to products";
    private final String CART_PAGE_LABEL = "Your Cart";
    private final String CHECKOUT_PAGE_LABEL = "Checkout: Your Information";
    private final String ORDER_SUMMARY_PAGE_LABEL = "Checkout: Overview";
    private final String ORDER_CONFIRM_PAGE_LABEL = "Checkout: Complete!";
    private final String COMPLETE_HEADER_TEXT = "Thank you for your order!";

    @BeforeMethod
    public void before() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        orderSummaryPage = new OrderSummaryPage(driver);
        orderConfirmPage = new OrderConfirmPage(driver);
    }

    @Test(description = "Test successful login")
    public void testProductPurchase() {
        //1. Navigate to the Sauce Labs Sample Application (https://www.saucedemo.com/) in Incognito mode.
        driver.get(APP_URL);
        //2. Enter valid credentials to log in.
        loginPage.authorize();
        //3. Verify that the login is successful and the user is redirected to the products page.
        assertThat(homePage.getPageLabel()).isEqualTo(HOMEPAGE_LABEL);
        //4. Select a T-shirt by clicking on its image or name.
        homePage.clickOnProductByName(PRODUCT_NAME);
        //5. Verify that the T-shirt details page is displayed.
        assertThat(productPage.getBackButtonText()).isEqualTo(PRODUCT_PAGE_LABEL);
        //6. Click the "Add to Cart" button.
        Product tShirt = productPage.getProductModel();
        productPage.clickAddToCartButton();
        tShirt.setQuantity(1);
        //7. Verify that the T-shirt is added to the cart successfully.
        assertThat(productPage.getShoppingCartBadge()).isEqualTo("1");
        //8. Navigate to the cart by clicking the cart icon or accessing the cart page directly.
        productPage.clickShoppingCart();
        //9. Verify that the cart page is displayed.
        assertThat(cartPage.getPageLabel()).isEqualTo(CART_PAGE_LABEL);
        //10. Review the items in the cart and ensure that the T-shirt is listed with the correct details (name, price,
        //quantity, etc.).
        assertThat(tShirt.equals(cartPage.getProductByIndex(0)))
                .as("Product is listed with the correct details").isTrue();
        //11. Click the "Checkout" button.
        cartPage.clickCheckoutButton();
        //12. Verify that the checkout information page is displayed.
        assertThat(checkoutPage.getPageLabel()).isEqualTo(CHECKOUT_PAGE_LABEL);
        //13. Enter the required checkout information (e.g., name, shipping address, payment details).
        checkoutPage.fillInCheckoutInfo();
        //14. Click the "Continue" button.
        checkoutPage.clickContinueButton();
        //15. Verify that the order summary page is displayed, showing the T-shirt details and the total amount.
        assertThat(orderSummaryPage.getPageLabel()).isEqualTo(ORDER_SUMMARY_PAGE_LABEL);
        assertThat(tShirt.equals(orderSummaryPage.getProductByIndex(0))).isTrue();
        Double taxAmount = orderSummaryPage.getTaxAsDouble();
        assertThat(Double.sum(tShirt.getPrice(), taxAmount)).isEqualTo(orderSummaryPage.getTotalPriceAsDouble());
        //16. Click the "Finish" button.
        orderSummaryPage.clickFinishButton();
        //17. Verify that the order confirmation page is displayed, indicating a successful purchase.
        assertThat(orderConfirmPage.getPageLabel()).isEqualTo(ORDER_CONFIRM_PAGE_LABEL);
        assertThat(orderConfirmPage.getCompleteHeaderText()).isEqualTo(COMPLETE_HEADER_TEXT);
        //18. Logout from the application.
        orderConfirmPage.logout();
        //19. Verify that the user is successfully logged out and redirected to the login page.
        loginPage.loginButtonVisible();
    }

    @AfterMethod
    public void after() {
        driver.close();
        driver.quit();
    }
}

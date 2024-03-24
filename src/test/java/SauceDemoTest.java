import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import static org.assertj.core.api.Assertions.*;

@Slf4j
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
    private final String PRODUCT_NAME_TSHIRT = "Sauce Labs Bolt T-Shirt";
    private final String PRODUCT_NAME_BACKPACK = "Sauce Labs Backpack";
    private final String PRODUCT_NAME_JACKET = "Sauce Labs Fleece Jacket";
    private final String PRODUCT_NAME_ONESIE = "Sauce Labs Onesie";
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

    @Test(description = "Test successful purchase")
    public void testProductPurchase() {
        logger.info("Navigate to the Sauce Labs Sample Application (https://www.saucedemo.com/) in Incognito mode.");
        driver.get(APP_URL);
        logger.info("Enter valid credentials to log in.");
        loginPage.authorize();
        logger.info("Verify that the login is successful and the user is redirected to the products page.");
        assertThat(homePage.getPageLabel()).isEqualTo(HOMEPAGE_LABEL);
        logger.info("Select a T-shirt by clicking on its image or name.");
        homePage.clickOnProductByName(PRODUCT_NAME_TSHIRT);
        logger.info("Verify that the T-shirt details page is displayed.");
        assertThat(productPage.getBackButtonText()).isEqualTo(PRODUCT_PAGE_LABEL);
        logger.info("Get product model");
        Product tShirt = productPage.getProductModel();
        logger.info("Click the 'Add to Cart' button.");
        productPage.clickAddToCartButton();
        logger.info("Set quantity");
        tShirt.setQuantity(1);
        logger.info("Verify that the T-shirt is added to the cart successfully.");
        assertThat(productPage.getShoppingCartBadge()).isEqualTo("1");
        logger.info("Navigate to the cart by clicking the cart icon or accessing the cart page directly.");
        productPage.clickShoppingCart();
        logger.info("Verify that the cart page is displayed.");
        assertThat(cartPage.getPageLabel()).isEqualTo(CART_PAGE_LABEL);
        logger.info("Review the items in the cart and ensure that the T-shirt is listed with the correct details (name, price,quantity, etc.).");
        assertThat(tShirt.equals(cartPage.getProductByIndex(0)))
                .as("Product is listed with the correct details").isTrue();
        logger.info("Click the 'Checkout' button.");
        cartPage.clickCheckoutButton();
        logger.info("Verify that the checkout information page is displayed.");
        assertThat(checkoutPage.getPageLabel()).isEqualTo(CHECKOUT_PAGE_LABEL);
        logger.info("Enter the required checkout information (e.g., name, shipping address, payment details).");
        checkoutPage.fillInCheckoutInfo();
        logger.info("Click the 'Continue' button.");
        checkoutPage.clickContinueButton();
        logger.info("Verify that the order summary page is displayed.");
        assertThat(orderSummaryPage.getPageLabel()).isEqualTo(ORDER_SUMMARY_PAGE_LABEL);
        logger.info("Check the T-shirt details");
        assertThat(tShirt.equals(orderSummaryPage.getProductByIndex(0))).isTrue();
        logger.info("Check total amount.");
        Double taxAmount = orderSummaryPage.getTaxAsDouble();
        assertThat(Double.sum(tShirt.getPrice(), taxAmount)).isEqualTo(orderSummaryPage.getTotalPriceAsDouble());
        logger.info("Click the 'Finish' button.");
        orderSummaryPage.clickFinishButton();
        logger.info("Verify that the order confirmation page is displayed.");
        assertThat(orderConfirmPage.getPageLabel()).isEqualTo(ORDER_CONFIRM_PAGE_LABEL);
        logger.info("Check that successful purchase is done.");
        assertThat(orderConfirmPage.getCompleteHeaderText()).isEqualTo(COMPLETE_HEADER_TEXT);
        logger.info("Logout from the application.");
        orderConfirmPage.logout();
        logger.info("Verify that the user is successfully logged out and redirected to the login page.");
        loginPage.loginButtonVisible();
    }

    @Test(description = "Test remove product from cart")
    public void testProductDelete() {
        logger.info("Navigate to the Sauce Labs Sample Application (https://www.saucedemo.com/) in Incognito mode.");
        driver.get(APP_URL);

        logger.info("Enter valid credentials to log in.");
        loginPage.authorize();

        logger.info("Verify that the login is successful and the user is redirected to the products page.");
        assertThat(homePage.getPageLabel()).isEqualTo(HOMEPAGE_LABEL);

        logger.info("Select a product by clicking on its image or name.");
        homePage.clickOnProductByName(PRODUCT_NAME_BACKPACK);

        logger.info("Verify that the product details page is displayed.");
        assertThat(productPage.getBackButtonText()).isEqualTo(PRODUCT_PAGE_LABEL);

        logger.info("Click the 'Add to Cart' button.");
        productPage.clickAddToCartButton();

        logger.info("Verify that the product is added to the cart successfully.");
        assertThat(productPage.getShoppingCartBadge()).isEqualTo("1");

        logger.info("Navigate to the cart by clicking the cart icon or accessing the cart page directly.");
        productPage.clickShoppingCart();
        assertThat(cartPage.getPageLabel()).isEqualTo(CART_PAGE_LABEL);

        logger.info("Remove product from cart.");
        cartPage.clickRemoveButton();

        logger.info("Get back to the home page.");
        cartPage.clickContinueShoppingButton();
    }

    @Test(description = "Add two products, remove one")
    public void testProductAddDelete(){
        logger.info("Navigate to the Sauce Labs Sample Application (https://www.saucedemo.com/) in Incognito mode.");
        driver.get(APP_URL);

        logger.info("Enter valid credentials to log in.");
        loginPage.authorize();

        logger.info("Verify that the login is successful and the user is redirected to the products page.");
        assertThat(homePage.getPageLabel()).isEqualTo(HOMEPAGE_LABEL);

        logger.info("Select a product by clicking on its image or name.");
        homePage.clickOnProductByName(PRODUCT_NAME_JACKET);

        logger.info("Verify that the product details page is displayed.");
        assertThat(productPage.getBackButtonText()).isEqualTo(PRODUCT_PAGE_LABEL);

        logger.info("Click the 'Add to Cart' button.");
        productPage.clickAddToCartButton();

        logger.info("Go back to home page by clicking on 'Back to products'");
        productPage.clickOnBackButton();

        logger.info("Select a product by clicking on its image or name.");
        homePage.clickOnProductByName(PRODUCT_NAME_ONESIE);

        logger.info("Verify that the product details page is displayed.");
        assertThat(productPage.getBackButtonText()).isEqualTo(PRODUCT_PAGE_LABEL);

        logger.info("Click the 'Add to Cart' button.");
        productPage.clickAddToCartButton();

        logger.info("Verify that the product is added to the cart successfully.");
        assertThat(productPage.getShoppingCartBadge()).isEqualTo("2");

        logger.info("Navigate to the cart by clicking the cart icon or accessing the cart page directly.");
        productPage.clickShoppingCart();
        assertThat(cartPage.getPageLabel()).isEqualTo(CART_PAGE_LABEL);

        logger.info("Remove second product from cart.");
        cartPage.clickRemoveButton(2);

        //check if product is one

        logger.info("Click the 'Checkout' button.");
        cartPage.clickCheckoutButton();

        logger.info("Verify that the checkout information page is displayed.");
        assertThat(checkoutPage.getPageLabel()).isEqualTo(CHECKOUT_PAGE_LABEL);

        logger.info("Enter the required checkout information (e.g., name, shipping address, payment details).");
        checkoutPage.fillInCheckoutInfo();

        logger.info("Click the 'Continue' button.");
        checkoutPage.clickContinueButton();

        logger.info("Click the 'Finish' button.");
        orderSummaryPage.clickFinishButton();
    }

//    @AfterMethod
//    public void after() {
//        driver.close();
//        driver.quit();
//    }
}
//test add to cart 3 items, delete 1 and 3 - list of products - on cart page list of cart items

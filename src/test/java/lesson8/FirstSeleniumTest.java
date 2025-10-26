package lesson8;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;



public class FirstSeleniumTest {


    WebDriver driver;

    @BeforeEach
    public void BeforeEach() {

        driver = new ChromeDriver();
        Duration timeToWait = Duration.ofSeconds(15);
        driver.manage().timeouts().implicitlyWait(timeToWait);
        driver.get("https://demo.prestashop.com/#/en/front");
        By iframe = By.id("framelive");

        WebElement inframeObject = driver.findElement(iframe);
        driver.switchTo().frame(inframeObject);

    }

    @Test
    public void addToCartTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By locator = By.cssSelector(".ui-autocomplete-input");
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        WebElement product = driver.findElement(locator);

        product.sendKeys("sweater");
        product.click();

        By menuLocator = By.cssSelector("li.ui-menu-item");
        WebElement choiceProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(menuLocator));
        choiceProduct.click();

        By addToCartLocator = By.cssSelector(".product-add-to-cart");
        WebElement addToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartLocator));
        addToCart.click();

        By modalLocator = By.cssSelector("#blockcart-modal");
        WebElement modalConfirmation = wait.until(ExpectedConditions.elementToBeClickable(modalLocator));

        By confirmationTextLocator = By.cssSelector("#myModalLabel");
        WebElement confirmationTextElement =wait.until(ExpectedConditions.elementToBeClickable(confirmationTextLocator));
        String confirmationText = confirmationTextElement .getText();
        System.out.println("Confirmation Text:" + confirmationText );

        String expectedText ="Product successfully added to your shopping cart";

        Assertions.assertTrue(confirmationText.contains(expectedText), "Text not found");

    }

    @Test
    public void addToNewsletter() {

        By locator3 = By.cssSelector("div.input-wrapper input");
        WebElement email = driver.findElement(locator3);
        email.sendKeys("ehalec1991@gmail.com");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        By subscribeLocator = By.cssSelector("input.btn.btn-primary.float-xs-right.hidden-xs-down");
        WebElement firstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(subscribeLocator));
        firstResult.click();

        By addlocator = By.cssSelector(".alert");
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(addlocator));
        String confirmationText = confirmationMessage.getText();
        System.out.println("Potwierdzenie: " + confirmationText);
        Assertions.assertTrue(confirmationText.contains("You have successfully subscribed to this newsletter"));
    }

    @AfterEach
    public void afrerEach() {

        if (driver != null) {
            driver.quit();
        }
    }
}
package dev.creator54;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";
    WebDriverWait wait;

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait (driver, Duration.ofSeconds (10));
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            /*
             * Click on the "Add new address" button, enter the addressString in the address
             * text box and click on the "ADD" button to save the address
             */
            WebElement addNewAddressButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-btn")));
            addNewAddressButton.click();

            WebElement AddressBox = wait.until(ExpectedConditions.visibilityOfElementLocated (By.className("MuiOutlinedInput-input")));
            AddressBox.clear();
            AddressBox.sendKeys(addresString);

            List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("css-177pwqq")));
            for (WebElement button : buttons) {
                if (button.getText().equals("ADD")) {
                    button.click();
                    wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(String.format(
                            "//*[@class='MuiTypography-root MuiTypography-body1 css-yg30e6' and text()='%s']",
                            addresString))));
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        try {
            /*
             * Iterate through all the address boxes to find the address box with matching
             * text, addressToSelect and click on it
             */
            WebElement parentBox = wait.until(ExpectedConditions.visibilityOfElementLocated (By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div/div[1]")));
            List<WebElement> allBoxes = parentBox.findElements(By.className("not-selected"));

            for (WebElement box : allBoxes) {
                if (box.findElement(By.className("css-yg30e6")).getText().replaceAll(" ", "")
                        .equals(addressToSelect.replaceAll(" ", ""))) {
                    box.findElement(By.tagName("input")).click();
                    return true;
                }
            }
            System.out.println("Unable to find the given address");
            return false;
        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("css-177pwqq")));
            for (WebElement element : elements) {
                if (element.getText().equals("PLACE ORDER")) {
                    element.click();
                    wait = new WebDriverWait (driver,Duration.ofSeconds(3));
                    wait.until (ExpectedConditions.urlContains ("/thanks"));
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
//            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        try {
            WebElement alertMessage = wait.until(ExpectedConditions.visibilityOfElementLocated (By.id("notistack-snackbar")));
            if (alertMessage.isDisplayed()) {
                if (alertMessage.getText().equals("You do not have enough balance in your wallet for this purchase")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }
}

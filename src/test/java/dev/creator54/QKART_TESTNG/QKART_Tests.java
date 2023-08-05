package dev.creator54.QKART_TESTNG;
import com.google.common.base.Verify;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import dev.creator54.QKART_TESTNG.pages.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class QKART_Tests {
    RemoteWebDriver  driver = null;
    ExtentReportsSingleton  extentReportsSingleton = null;
    ExtentTest test = null;

    @BeforeSuite(alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        System.out.println("Creating Driver");
        driver = DriverSingleton.getInstance().getDriver ();
        driver.manage ().window ().maximize ();
        System.out.println("Driver Created");

        extentReportsSingleton = new ExtentReportsSingleton ().getInstance();
        test = extentReportsSingleton.getExtentTest ();
    }

    @AfterSuite(alwaysRun = true)
    public void closeDriver() {
        DriverSingleton.quitDriver();
        extentReportsSingleton.endReport();
    }

    public static String takeScreenshot(WebDriver driver, String description, String testCaseName) {
        try {
            File screenshotsDir = new File("./screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }

            String fileName = String.format("%s_%s.png", testCaseName, description);
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotsDir, fileName);
            FileUtils.copyFile(srcFile, destFile);

            return destFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

     /*
     * Testcase01: Verify the functionality of Login button on the Home page
     */
    @Test(description="Verify registration happens correctly",priority = 1,groups = {"Sanity_Test"})
    @Parameters({"TC1_Username","TC1_Password"})
    public void TestCase01(String username, String password) throws InterruptedException, IOException {
        // Step 1: User Registration
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean registrationStatus = registration.registerUser(username, password, true);

        Assert.assertTrue(registrationStatus, "Test Case 1: User Registration Failed");

        // Save the last generated username
        String lastGeneratedUserName = registration.lastGeneratedUsername;

        // Step 2: User Login
        Login login = new Login(driver);
        login.navigateToLoginPage();
        Boolean loginStatus = login.PerformLogin(lastGeneratedUserName, password);

        Assert.assertTrue(loginStatus, "Test Case 1: User Login Failed");

        // Step 3: User Logout
        Home home = new Home(driver);
        Boolean logoutStatus = home.PerformLogout();

        Assert.assertTrue(logoutStatus, "Test Case 1: User Logout Failed");
        test.log(LogStatus.PASS, "Testcase01 -> Verify the functionality of Login button on the Home page : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase01")));
    }

    /*
     * Verify that an existing user is not allowed to re-register on QKart
     */
    @Test(description="Verify re-registering an already registered user fails",priority = 2,groups={"Sanity_Test"})
    @Parameters({"TC2_Username","TC2_Password"})
    public void TestCase02(String username, String password) throws InterruptedException, IOException {
        // Step 1: User Registration
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean registrationStatus = registration.registerUser(username, password, true);

        Assert.assertTrue(registrationStatus, "Test Case 2: User Registration Failed");

        // Save the last generated username
        String lastGeneratedUserName = registration.lastGeneratedUsername;

        // Step 2: User Reregistration
        registration.navigateToRegisterPage();
        Boolean reregistrationStatus = registration.registerUser(lastGeneratedUserName, password, false);

        Assert.assertFalse(reregistrationStatus, "Test Case 2: User Reregistration Succeeded");

        // Step 3: User Logout
        Home home = new Home(driver);
        Boolean logoutStatus = home.PerformLogout();

        Assert.assertTrue(logoutStatus, "Test Case 2: User Logout Failed");
        test.log(LogStatus.PASS, "Testcase02 -> Verify that an existing user is not allowed to re-register on QKart : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase02")));
    }

    /*
     * Verify the functinality of the search text box
     */
    @Test(description="Verify the functionality of search text box",priority = 3,groups={"Sanity_Test"})
    @Parameters("TC3_ProductNameToSearchFor")
    public void TestCase03(String productName) throws InterruptedException, IOException {
        boolean status;

        // Visit the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Step 1: Search for the "yonex" product
        homePage.searchForProduct(productName);

        // Fetch the search results
        List<WebElement> searchResults = homePage.getSearchResults();

        // Verify the search results are available
        Assert.assertFalse(searchResults.size() == 0, "Test Case 03: No search results Found !");

        // Verify that all results contain the searched text
        for (WebElement webElement : searchResults) {
            // Create a SearchResult object from the parent element
            SearchResult resultElement = new SearchResult(webElement);

            // Verify that the title of each result contains the searched text
            String elementText = resultElement.getTitleofResult();
            Assert.assertTrue(elementText.toUpperCase().contains(productName.toUpperCase()),"Test Case 03: Search for product"+ productName + " Failed, unexpected Results !");
        }

        // Step 2: Search for product "Gesundheit"
        homePage.searchForProduct("Gesundheit");

        // Verify no search results are found
        searchResults = homePage.getSearchResults();
        Assert.assertTrue (searchResults.size ()==0," Test Case 03:  Results were found for invalid search !");
        test.log(LogStatus.PASS, "Testcase03 -> Verify the functinality of the search text box : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase03")));
    }

    /*
     * Verify the presence of size chart and check if the size chart content is as
     * expected
     */
    @Test(description = "Verify the existence of size chart for certain items and validate contents of size chart", priority = 4, groups = { "Regression_Test" })
    @Parameters("TC4_ProductNameToSearchFor")
    public void TestCase04(String productName) throws InterruptedException, IOException {
        Boolean status = false;

        // Visit home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Step 1: Search for a product and get card content element of search results
        homePage.searchForProduct(productName);
        List<WebElement> searchResults = homePage.getSearchResults();

        // Create expected values
        List<String> expectedTableHeaders = Arrays.asList("Size", "UK/INDIA", "EU", "HEEL TO TOE");
        List<List<String>> expectedTableBody = Arrays.asList(
                Arrays.asList("6", "6", "40", "9.8"),
                Arrays.asList("7", "7", "41", "10.2"),
                Arrays.asList("8", "8", "42", "10.6"),
                Arrays.asList("9", "9", "43", "11"),
                Arrays.asList("10", "10", "44", "11.5"),
                Arrays.asList("11", "11", "45", "12.2"),
                Arrays.asList("12", "12", "46", "12.6")
        );

        // Step 2: Verify size chart presence and content matching for each search result
        for (WebElement webElement : searchResults) {
            SearchResult result = new SearchResult(webElement);

            // Verify if the size chart exists for the search result
            status = result.verifySizeChartExists();
            Assert.assertTrue(status, "Test Case 04: Failed to find Size Chart !");

            // Verify if size dropdown exists
            status = result.verifyExistenceofSizeDropdown(driver);
            Assert.assertTrue(status, "Test Case 04: Failed to verify the existence of Size Dropdown !");

            // Step 3: Open the size chart
            status = result.openSizechart();
            Assert.assertTrue(status, "Step Failure: Failure to open Size Chart !");

            // Verify if the size chart contents match the expected values
            status = result.validateSizeChartContents(expectedTableHeaders, expectedTableBody, driver);
            Assert.assertTrue(status, "Step Failure: Failure while validating contents of Size Chart Link !");

            // Step 4: Close the size chart modal
            status = result.closeSizeChart(driver);
            Assert.assertTrue(status, "Step Failure: Failure to close Size Chart !");

        }
        test.log(LogStatus.PASS, "Testcase04 -> Verify the presence of size chart and check if the size chart content is as : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase04")));
    }

    /*
     * Verify the complete flow of checking out and placing order for products is
     * working correctly
     */

    @Test(description = "Verify that a new user can add multiple products in to the cart and Checkout", priority = 5, groups = { "Sanity_Test" })
    @Parameters({ "TC5_ProductNameToSearchFor", "TC5_ProductNameToSearchFor2", "TC5_AddressDetails" })
    public void TestCase05(String productOne, String productTwo, String address) throws InterruptedException, IOException {
        Boolean status;

        // Step 1: Go to the Register page
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();

        // Step 2: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        Assert.assertTrue(status, "Step Failure: User Registration Failed !");

        // Save the username of the newly registered user
        String lastGeneratedUserName = registration.lastGeneratedUsername;

        // Step 3: Go to the login page
        Login login = new Login(driver);
        login.navigateToLoginPage();

        // Step 4: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "Step Failure: User Login Failed !");

        // Step 5: Go to the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Step 6: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct(productOne);
        Assert.assertTrue(status, "Step Failure: Searching for product '"+productOne+"' Failed !");
        homePage.addProductToCart(productOne);

        status = homePage.searchForProduct(productTwo);
        Assert.assertTrue(status, "Step Failure: Searching for product '"+productTwo+"' Failed !");
        homePage.addProductToCart(productTwo);

        // Step 7: Click on the checkout button
        homePage.clickCheckout();

        // Step 8: Add a new address on the Checkout page and select it
        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress(address);
        checkoutPage.selectAddress(address);

        // Step 9: Place the order
        checkoutPage.placeOrder();

        // Step 10: Check if placing order redirected to the Thanks page
        status = driver.getCurrentUrl().endsWith("/thanks");
        Assert.assertTrue(status, "Step Failure: Placing Order and Redirecting to Thanks Page Failed !");

        // Step 11: Go to the home page
        homePage.navigateToHome();

        // Step 12: Log out the user
        status = homePage.PerformLogout();

        // The test case has passed if it has reached this point
        Assert.assertTrue(status, "Test Case 5: Happy Flow Test Completed !");
        test.log(LogStatus.PASS, "Testcase05 -> Verify the complete flow of checking out and placing order for products is working correctly : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase05")));
    }


    /*
     * Verify the quantity of items in cart can be updated
     */
    @Test(description = "Verify that the contents of the cart can be edited", priority = 6, groups = { "Regression_Test" })
    @Parameters({ "TC6_ProductNameToSearch1", "TC6_ProductNameToSearch2" })
    public void TestCase06(String productOne, String productTwo) throws InterruptedException, IOException {
        Boolean status;

        // Step 1: Navigate to the Register page
        Home homePage = new Home(driver);
        Register registration = new Register(driver);
        Login login = new Login(driver);

        registration.navigateToRegisterPage();

        // Step 2: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        Assert.assertTrue(status, "Step Failure: User Registration Failed !");

        String lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();

        // Step 3: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "Step Failure: User Login Failed !");

        homePage.navigateToHome();

        // Step 4: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct(productOne);
        Assert.assertTrue(status, "Step Failure: Searching for product '"+productOne+"' Failed !");
        homePage.addProductToCart(productOne);

        status = homePage.searchForProduct(productTwo);
        Assert.assertTrue(status, "Step Failure: Searching for product '"+productTwo+"' Failed !");
        homePage.addProductToCart(productTwo);

        // Step 5: Update watch quantity to 2
        status = homePage.changeProductQuantityinCart(productOne, 2);
        Assert.assertTrue(status, "Step Failure: Updating quantity of '"+productOne+"' to 2 Failed !");

        // Step 6: Update table lamp quantity to 0
        status = homePage.changeProductQuantityinCart(productTwo, 0);
        Assert.assertFalse(status, "Step Failure: Updating quantity of '"+productTwo+"' to 0 Failed !");

        // Step 7: Update watch quantity again to 1
        status = homePage.changeProductQuantityinCart(productOne, 1);
        Assert.assertTrue(status, "Step Failure: Updating quantity of '"+productOne+"' to 1 Failed !");

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 5");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 5");

        checkoutPage.placeOrder();

        // Step 8: Check if placing order redirected to the Thanks page
        status = driver.getCurrentUrl().endsWith("/thanks");
        Assert.assertTrue(status, "Step Failure: Placing Order and Redirecting to Thanks Page Failed !");

        homePage.navigateToHome();
        status = homePage.PerformLogout();

        // The test case has passed if it has reached this point
        Assert.assertTrue(status, "Test Case 6: Verify that cart can be edited !");
        test.log(LogStatus.PASS, "Testcase06 -> Verify the quantity of items in cart can be updated : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase06")));
    }

    /*
     * Verify that the cart contents are persisted after logout
     */

    @Test(description = "Verify that the contents made to the cart are saved against the user's login details", priority = 7, groups = { "Regression_Test" })
    @Parameters({ "TC7_ListOfProductsToAddToCart" })
    public void TestCase07(String products) throws InterruptedException, IOException {
        Boolean status = false;
        List<String> productsList = Arrays.asList(products.split (","));
        List<String> expectedResult = Arrays.asList(products.split (","));

        Register registration = new Register(driver);
        Login login = new Login(driver);
        Home homePage = new Home(driver);

        registration.navigateToRegisterPage();

        // Step 1: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        Assert.assertTrue(status, "Step Failure: User Registration Failed !");

        String lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();

        // Step 2: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "Step Failure: User Login Failed !");

        homePage.navigateToHome();

        // Step 3: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct(productsList.get (0));
        Assert.assertTrue(status, "Step Failure: Searching for product '"+productsList.get (0)+"' Failed !");
        homePage.addProductToCart(productsList.get (0));
        Assert.assertTrue(status, "Step Failure: Adding '"+productsList.get (0)+"' to the cart Failed !");

        status = homePage.searchForProduct(productsList.get (1));
        Assert.assertTrue(status, "Step Failure: Searching for product '"+productsList.get (1)+"' Failed !");
        homePage.addProductToCart(productsList.get (1));
        Assert.assertTrue(status, "Step Failure: Adding '"+productsList.get (1)+"' to the cart Failed !");

        homePage.PerformLogout();

        login.navigateToLoginPage();

        // Step 4: Login again with the same user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "Step Failure: User Login after Logout Failed !");

        // Step 5: Verify if the cart contents are persisted after logout
        status = homePage.verifyCartContents(expectedResult);
        Assert.assertTrue(status, "Step Failure: Cart Contents Not Persisted after Logout !");

        // The test case has passed if it has reached this point
        Assert.assertTrue(true, "Test Case 7: Verify that cart contents are persisted after logout !");
        test.log(LogStatus.PASS, "Testcase07 -> Verify that the cart contents are persisted after logout : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase07")));
    }

    @Test(description = "Verify that insufficient balance error is thrown when the wallet balance is not enough", priority = 8, groups = { "Sanity_Test" })
    @Parameters({ "TC8_ProductName", "TC8_Qty" })
    public void TestCase08(String productName, int quantity) throws InterruptedException, IOException {
        Boolean status;

        Register registration = new Register(driver);
        Login login = new Login(driver);
        Home homePage = new Home(driver);

        registration.navigateToRegisterPage();

        // Step 1: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        Assert.assertTrue(status, "Step Failure: User Registration Failed !");

        String lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();

        // Step 2: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "Step Failure: User Login Failed !");

        homePage.navigateToHome();

        // Step 3: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct(productName);
        Assert.assertTrue(status, "Step Failure: Searching for product '"+productName+"' Failed !");
        homePage.addProductToCart(productName);
        Assert.assertTrue(status, "Step Failure: Adding '"+productName+"' to the cart Failed !");

        homePage.changeProductQuantityinCart(productName, quantity);
        Assert.assertTrue(status, "Step Failure: Changing product quantity in the cart to "+quantity+" Failed !");

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 5");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 5");

        checkoutPage.placeOrder();

        // Step 4: Verify if the insufficient balance error message is displayed
        status = checkoutPage.verifyInsufficientBalanceMessage();
        Assert.assertTrue(status, "Step Failure: Insufficient balance error message is not displayed !");
        test.log(LogStatus.PASS, "Testcase08 -> Verify that insufficient balance error is thrown when the wallet balance is not enough : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase08")));
    }

    @Test(description = "Verify that a product added to a cart is available when a new tab is added", priority = 10, dependsOnMethods = { "TestCase10" }, groups = { "Regression_Test" })
    public void TestCase09() throws InterruptedException, IOException {
        Boolean status;

        Register registration = new Register(driver);
        Login login = new Login(driver);
        Home homePage = new Home(driver);

        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@125", true);
        Assert.assertTrue(status, "Step Failure: User Registration Failed !");

        String lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "Step Failure: User Login Failed !");

        homePage.navigateToHome();

        status = homePage.searchForProduct("YONEX");
        Assert.assertTrue(status, "Step Failure: Searching for product 'YONEX' Failed !");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");
        Assert.assertTrue(status, "Step Failure: Adding 'YONEX Smash Badminton Racquet' to the cart Failed !");

        String currentURL = driver.getCurrentUrl();

        driver.findElement(By.linkText("Privacy policy")).click();
        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]);
        homePage.navigateToHome();

        List<String> expectedResult = Arrays.asList("YONEX Smash Badminton Racquet");
        status = homePage.verifyCartContents(expectedResult);
        Assert.assertTrue(status, "Step Failure: Cart contents are not available in the new tab !");

        driver.close();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
        test.log(LogStatus.PASS, "Testcase09 -> Verify that a product added to a cart is available when a new tab is added : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase09")));
    }


    @Test(description = "Verify that privacy policy and about us links are working fine", priority = 9, groups = { "Regression_Test" })
    public void TestCase10() throws InterruptedException, IOException {
        Boolean status;

        Register registration = new Register(driver);
        Login login = new Login(driver);
        Home homePage = new Home(driver);

        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@125", true);
        Assert.assertTrue(status, "Step Failure: User Registration Failed !");

        String lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "Step Failure: User Login Failed !");

        homePage.navigateToHome();

        String basePageURL = driver.getCurrentUrl();

        driver.findElement(By.linkText("Privacy policy")).click();
        status = driver.getCurrentUrl().equals(basePageURL);
        Assert.assertTrue(status, "Step Failure: Verifying parent page URL didn't change on Privacy Policy link click !");

        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]);
        WebElement privacyPolicyHeading = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/h2"));
        status = privacyPolicyHeading.getText().equals("Privacy Policy");
        Assert.assertTrue(status, "Step Failure: Verifying new tab opened has Privacy Policy page heading !");

        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
        driver.findElement(By.linkText("Terms of Service")).click();

        handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[2]);
        WebElement tosHeading = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/h2"));
        status = tosHeading.getText().equals("Terms of Service");
        Assert.assertTrue(status, "Step Failure: Verifying new tab opened has Terms Of Service page heading !");

        driver.close();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]).close();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
        test.log(LogStatus.PASS, "Testcase10 -> Verify that privacy policy and about us links are working fine : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase10")));
    }

    @Test(description = "Verify that the contact us dialog works fine", priority = 11, groups = { "Regression_Test" })
    @Parameters({ "TC11_ContactusUserName", "TC11_ContactUsEmail", "TC11_QueryContent" })
    public void TestCase11(String contactName, String emailId, String msg) throws InterruptedException, IOException {
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        driver.findElement(By.xpath("//*[text()='Contact us']")).click();

        WebElement name = driver.findElement(By.xpath("//input[@placeholder='Name']"));
        name.sendKeys(contactName);

        WebElement email = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        email.sendKeys(emailId);

        WebElement message = driver.findElement(By.xpath("//input[@placeholder='Message']"));
        message.sendKeys(msg);

        WebElement contactUs = driver.findElement(By.xpath("*//button[contains(@class,\"btn-block\")]"));
        contactUs.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(contactUs));
        test.log(LogStatus.PASS, "Testcase11 -> Verify that the contact us dialog works fine : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase11")));
    }


    @Test(description = "Ensure that the Advertisement Links on the QKART page are clickable", priority = 12, groups = { "Sanity_Test" })
    @Parameters({ "TC12_ProductNameToSearch", "TC12_AddresstoAdd" })
    public void TestCase12(String productToSearch, String address) throws InterruptedException, IOException {
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean status = registration.registerUser("testUser", "abc@125", true);
        Assert.assertTrue(status, "User Registration Failed!");

        String lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        Assert.assertTrue(status, "User Login Failed!");

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        status = homePage.searchForProduct(productToSearch);
        Assert.assertTrue(status, "Product Search Failed!");

        homePage.addProductToCart(productToSearch);
        homePage.changeProductQuantityinCart(productToSearch, 1);
        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress(address);
        checkoutPage.selectAddress(address);
        checkoutPage.placeOrder();

        String currentURL = driver.getCurrentUrl();

        List<WebElement> advertisements = driver.findElements(By.xpath("//iframe"));
        Assert.assertEquals(advertisements.size(), 3, "Number of Advertisements is not as expected!");

        WebElement advertisement1 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/iframe[1]"));
        driver.switchTo().frame(advertisement1);
        driver.findElement(By.xpath("//button[text()='Buy Now']")).click();
        Assert.assertNotEquals(driver.getCurrentUrl(), currentURL, "Advertisement 1 is not clickable!");

        driver.navigate().back();
        driver.switchTo().parentFrame();

        WebElement advertisement2 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/iframe[2]"));
        driver.switchTo().frame(advertisement2);
        driver.findElement(By.xpath("//button[text()='Buy Now']")).click();
        Assert.assertNotEquals(driver.getCurrentUrl(), currentURL, "Advertisement 2 is not clickable!");

        driver.switchTo().parentFrame();
        driver.navigate().back();
        test.log(LogStatus.PASS, "Testcase12 -> Ensure that the Advertisement Links on the QKART page are clickable : Passed", test.addScreenCapture(takeScreenshot(driver, "PASSED", "Testcase12")));
    }
}

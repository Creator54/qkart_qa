package dev.creator54;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Main {

    public static String lastGeneratedUserName;

    public static RemoteWebDriver createDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions ();
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        return driver;
    }

    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s", String.valueOf(java.time.LocalDateTime.now()), type,
                message, status));
    }

    public static void takeScreenshot(WebDriver driver, String description, String testCaseName) {
        try {
            File theDir = new File("/screenshots");
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now());
            String fileName = String.format("%s_%s.png", testCaseName, description);
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File("screenshots/" + fileName);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Testcase01: Verify the functionality of Login button on the Home page
     */
    public static Boolean TestCase01(RemoteWebDriver driver) throws InterruptedException {
        takeScreenshot(driver, "StartTestCase", "TestCase01");
        logStatus("Start TestCase", "Test Case 1: Verify User Registration and Login", "DONE");

        // Step 1: User Registration
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean registrationStatus = registration.registerUser("testUser", "abc@125", true);

        if (!registrationStatus) {
            takeScreenshot(driver, "Registration_Failure", "TestCase01");
            logStatus("TestCase 1", "Test Case 1: User Registration Failed", "FAIL");
            return false;
        } else {
            takeScreenshot(driver, "Registration_Passed", "TestCase01");
            logStatus("TestCase 1", "Test Case 1: User Registration Passed", "PASS");
        }

        // Save the last generated username
        String lastGeneratedUserName = registration.lastGeneratedUsername;

        // Step 2: User Login
        Login login = new Login(driver);
        login.navigateToLoginPage();
        Boolean loginStatus = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Test Step", "Test Case 1: User Perform Login", loginStatus ? "PASS" : "FAIL");

        if (!loginStatus) {
            takeScreenshot(driver, "Login_Failure", "TestCase01");
            logStatus("End TestCase", "Test Case 1: Verify User Registration and Login", "FAIL");
            return false;
        }

        // Step 3: User Logout
        Home home = new Home(driver);
        Boolean logoutStatus = home.PerformLogout();
        takeScreenshot(driver, "Login_Passed", "TestCase01");
        logStatus("End TestCase", "Test Case 1: Verify User Registration and Login", logoutStatus ? "PASS" : "FAIL");

        return logoutStatus;
    }

    /*
     * Verify that an existing user is not allowed to re-register on QKart
     */
    public static Boolean TestCase02(RemoteWebDriver driver) throws InterruptedException {
        takeScreenshot(driver, "StartTestCase", "TestCase02");
        logStatus("Start TestCase", "Test Case 2: Verify User Registration with an existing username", "DONE");

        // Step 1: User Registration
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        Boolean registrationStatus = registration.registerUser("testUser", "abc@125", true);
        logStatus("Test Step", "Test Case 2: User Registration", registrationStatus ? "PASS" : "FAIL");

        if (!registrationStatus) {
            takeScreenshot(driver, "Registration_Failure", "TestCase02");
            logStatus("End TestCase", "Test Case 2: Verify User Registration with an existing username", "FAIL");
            return false;
        }

        // Save the last generated username
        String lastGeneratedUserName = registration.lastGeneratedUsername;

        // Step 2: User Reregistration
        registration.navigateToRegisterPage();
        Boolean reregistrationStatus = registration.registerUser(lastGeneratedUserName, "abc@125", false);

        // If reregistrationStatus is true, then reregistration succeeded, else it failed.
        // In this case, reregistration failure means Success (blocked).
        takeScreenshot(driver, "Reregistration_" + (reregistrationStatus ? "Allowed" : "Blocked"), "TestCase02");
        logStatus("End TestCase", "Test Case 2: Verify if user Reregistration using the same username is not allowed", reregistrationStatus ? "FAIL" : "PASS");

        return !reregistrationStatus;
    }

    /*
     * Verify the functinality of the search text box
     */
    public static Boolean TestCase03(RemoteWebDriver driver) throws InterruptedException {
        takeScreenshot(driver, "StartTestCase", "TestCase03");
        logStatus("Start TestCase", "Test Case 3: Verify functionality of the search box", "DONE");

        boolean status;

        // Visit the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Step 1: Search for the "yonex" product
        status = homePage.searchForProduct("YONEX");
        if (!status) {
            takeScreenshot(driver, "Search_Failure", "TestCase03");
            logStatus("Test Step", "Search for the \"YONEX\" product", "FAIL");
            logStatus("End TestCase", "Test Case 3: Verify functionality of the search box", "FAIL");
            return false;
        }

        // Fetch the search results
        List<WebElement> searchResults = homePage.getSearchResults();

        // Verify the search results are available
        if (searchResults.size() == 0) {
            takeScreenshot(driver, "No_Results_Found", "TestCase03");
            logStatus("Test Step", "Verify that search results are available", "FAIL");
            logStatus("End TestCase", "Test Case 3: Verify functionality of the search box", "FAIL");
            return false;
        }

        // Verify that all results contain the searched text
        for (WebElement webElement : searchResults) {
            // Create a SearchResult object from the parent element
            SearchResult resultElement = new SearchResult(webElement);

            // Verify that the title of each result contains the searched text
            String elementText = resultElement.getTitleofResult();
            if (!elementText.toUpperCase().contains("YONEX")) {
                takeScreenshot(driver, "Unexpected_Result", "TestCase03");
                logStatus("Test Step", "Verify that search results contain the expected text: YONEX", "FAIL");
                logStatus("End TestCase", "Test Case 3: Verify functionality of the search box", "FAIL");
                return false;
            }
        }

        logStatus("Test Step", "Successfully validated the search results", "PASS");

        // Step 2: Search for product "Gesundheit"
        status = homePage.searchForProduct("Gesundheit");

        // Verify no search results are found
        searchResults = homePage.getSearchResults();
        if (searchResults.size() == 0) {
            if (homePage.isNoResultFound()) {
                logStatus("Test Step", "Successfully validated that no products found message is displayed", "PASS");
            }
        } else {
            takeScreenshot(driver, "Results_Available", "TestCase03");
            logStatus("Test Step", "Verify that no search results are found for the given text", "FAIL");
            status = false;
        }

        logStatus("End TestCase", "Test Case 3: Verify functionality of the search box", status ? "PASS":"FAIL");
        return status;
    }

    /*
     * Verify the presence of size chart and check if the size chart content is as
     * expected
     */
    public static Boolean TestCase04(RemoteWebDriver driver) throws InterruptedException {
        takeScreenshot(driver, "StartTestCase", "TestCase04");
        logStatus("Start TestCase", "Test Case 4: Verify the presence of Size Chart", "DONE");
        boolean status = false;

        // Visit home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Step 1: Search for a product and get card content element of search results
        status = homePage.searchForProduct("Running Shoes");
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
            if (result.verifySizeChartExists()) {
                takeScreenshot(driver, "SizeChart_Presence", "TestCase04");
                logStatus("Step Success", "Successfully validated presence of Size Chart Link", "PASS");

                // Verify if size dropdown exists
                status = result.verifyExistenceofSizeDropdown(driver);
                logStatus("Step Success", "Validated presence of drop down", status ? "PASS" : "FAIL");

                // Step 3: Open the size chart
                if (result.openSizechart()) {
                    takeScreenshot(driver, "SizeChart_Opened", "TestCase04");
                    // Verify if the size chart contents match the expected values
                    if (result.validateSizeChartContents(expectedTableHeaders, expectedTableBody, driver)) {
                        logStatus("Step Success", "Successfully validated contents of Size Chart Link", "PASS");
                    } else {
                        logStatus("Step Failure", "Failure while validating contents of Size Chart Link", "FAIL");
                        status = false;
                        break;
                    }

                    // Step 4: Close the size chart modal
                    status = result.closeSizeChart(driver);
                    takeScreenshot(driver, "SizeChart_Closed", "TestCase04");
                } else {
                    logStatus("Step Failure", "Failure to open Size Chart", "FAIL");
                    takeScreenshot(driver, "SizeChart_Open_Failure", "TestCase04");
                    status=false;
                    break;
                }

            } else {
                logStatus("Test Step", "Size Chart Link does not exist for this search result", "FAIL");
                takeScreenshot(driver, "SizeChart_Existence_Failure", "TestCase04");
                status=false;
                break;
            }
        }

        logStatus("End TestCase", "Test Case 4: Verify the presence of Size Chart", status ? "PASS" : "FAIL");
        takeScreenshot(driver, "SizeChart_Validated", "TestCase04");
        return status;
    }

    /*
     * Verify the complete flow of checking out and placing order for products is
     * working correctly
     */

    public static Boolean TestCase05(RemoteWebDriver driver) throws InterruptedException {
        takeScreenshot(driver, "StartTestCase", "TestCase05");
        logStatus("Start TestCase", "Test Case 5: Verify Happy Flow of buying products", "DONE");
        Boolean status;

        // Step 1: Go to the Register page
        Register registration = new Register(driver);
        registration.navigateToRegisterPage();

        // Step 2: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        logStatus("Step Success", "User Registration: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase", "Test Case 5: Happy Flow Test Failed", "FAIL");
            takeScreenshot(driver, "Registration_Failure", "TestCase05");
            return false;
        }

        // Save the username of the newly registered user
        lastGeneratedUserName = registration.lastGeneratedUsername;

        // Step 3: Go to the login page
        Login login = new Login(driver);
        login.navigateToLoginPage();

        // Step 4: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Step Success", "User Perform Login: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase", "Test Case 5: Happy Flow Test Failed", status ? "PASS" : "FAIL");
            takeScreenshot(driver, "Login_Failure", "TestCase05");
            return false;
        }

        // Step 5: Go to the home page
        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Step 6: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct("YONEX");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");
        status = homePage.searchForProduct("Tan");
        homePage.addProductToCart("Tan Leatherette Weekender Duffle");

        // Step 7: Click on the checkout button
        homePage.clickCheckout();

        // Step 8: Add a new address on the Checkout page and select it
        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 5");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 5");

        // Step 9: Place the order
        checkoutPage.placeOrder();

        // Step 10: Check if placing order redirected to the Thanks page
        status = driver.getCurrentUrl().endsWith("/thanks");
        logStatus("Step Success", "Placing Order and Redirecting to Thanks Page: ", status ? "PASS" : "FAIL");

        // Step 11: Go to the home page
        homePage.navigateToHome();

        // Step 12: Log out the user
        homePage.PerformLogout();

        logStatus("End TestCase", "Test Case 5: Happy Flow Test Completed", status ? "PASS" : "FAIL");
        takeScreenshot(driver, "HappyFlow_Verified", "TestCase05");
        return status;
    }

    /*
     * Verify the quantity of items in cart can be updated
     */
    public static Boolean TestCase06(RemoteWebDriver driver) throws InterruptedException {
        takeScreenshot(driver, "StartTestCase", "TestCase06");
        logStatus("Start TestCase", "Test Case 6: Verify that cart can be edited", "DONE");
        Boolean status;

        // Step 1: Navigate to the Register page
        Home homePage = new Home(driver);
        Register registration = new Register(driver);
        Login login = new Login(driver);

        registration.navigateToRegisterPage();

        // Step 2: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        logStatus("Step Success", "User Registration: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase", "Test Case 6: Verify that cart can be edited", "FAIL");
            takeScreenshot(driver, "Registration_Failure", "TestCase06");
            return false;
        }
        lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();

        // Step 3: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Step Success", "User Perform Login: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase", "Test Case 6: Verify that cart can be edited", "FAIL");
            takeScreenshot(driver, "Login_Failure", "TestCase06");
            return false;
        }

        homePage.navigateToHome();

        // Step 4: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct("Xtend");
        homePage.addProductToCart("Xtend Smart Watch");

        status = homePage.searchForProduct("Yarine");
        homePage.addProductToCart("Yarine Floor Lamp");

        // Step 5: Update watch quantity to 2
        homePage.changeProductQuantityinCart("Xtend Smart Watch", 2);
        logStatus("Step Success", "Updated quantity of Xtend Smart Watch to 2", "PASS");

        // Step 6: Update table lamp quantity to 0
        homePage.changeProductQuantityinCart("Yarine Floor Lamp", 0);
        logStatus("Step Success", "Updated quantity of Yarine Floor Lamp to 0", "PASS");

        // Step 7: Update watch quantity again to 1
        homePage.changeProductQuantityinCart("Xtend Smart Watch", 1);
        logStatus("Step Success", "Updated quantity of Xtend Smart Watch to 1", "PASS");

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 5");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 5");

        checkoutPage.placeOrder();

        // Step 8: Check if placing order redirected to the Thanks page
        status = driver.getCurrentUrl().endsWith("/thanks");
        logStatus("Step Success", "Placing Order and Redirecting to Thanks Page: ", status ? "PASS" : "FAIL");

        homePage.navigateToHome();
        homePage.PerformLogout();

        logStatus("End TestCase", "Test Case 6: Verify that cart can be edited", status ? "PASS" : "FAIL");
        takeScreenshot(driver, "Cart_Edit_Verified", "TestCase06");
        return status;
    }

    /*
     * Verify that the cart contents are persisted after logout
     */
    public static Boolean TestCase07(RemoteWebDriver driver) throws InterruptedException {
        Boolean status = false;
        List<String> expectedResult = Arrays.asList("Stylecon 9 Seater RHS Sofa Set ", "Xtend Smart Watch");

        takeScreenshot(driver, "StartTestCase", "TestCase07");
        logStatus("Start TestCase", "Test Case 7: Verify that cart contents are persisted after logout", "DONE");

        Register registration = new Register(driver);
        Login login = new Login(driver);
        Home homePage = new Home(driver);

        registration.navigateToRegisterPage();

        // Step 1: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        logStatus("Step Success", "User Registration: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase", "Test Case 7: Verify that cart contents are persisted after logout", "FAIL");
            takeScreenshot(driver, "Registration_Failure", "TestCase07");
            return false;
        }
        lastGeneratedUserName = registration.lastGeneratedUsername;

        login.navigateToLoginPage();

        // Step 2: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Step Success", "User Perform Login: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase", "Test Case 7: Verify that cart contents are persisted after logout", "FAIL");
            takeScreenshot(driver, "Login_Failure", "TestCase07");
            return false;
        }

        homePage.navigateToHome();

        // Step 3: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct("Stylecon");
        homePage.addProductToCart("Stylecon 9 Seater RHS Sofa Set ");
        logStatus("Step Success", "Added 'Stylecon 9 Seater RHS Sofa Set' to the cart", status ? "PASS" : "FAIL");

        status = homePage.searchForProduct("Xtend");
        homePage.addProductToCart("Xtend Smart Watch");
        logStatus("Step Success", "Added 'Xtend Smart Watch' to the cart", status ? "PASS" : "FAIL");

        homePage.PerformLogout();

        login.navigateToLoginPage();

        // Step 4: Login again with the same user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Step Success", "User Perform Login after Logout: ", status ? "PASS" : "FAIL");

        // Step 5: Verify if the cart contents are persisted after logout
        status = homePage.verifyCartContents(expectedResult);
        logStatus("Step Success", "Cart Contents Persisted after Logout: ", status ? "PASS" : "FAIL");

        logStatus("End TestCase", "Test Case 7: Verify that cart contents are persisted after logout",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "Cart_Contents_Persisted", "TestCase07");

        homePage.PerformLogout();
        return status;
    }

    public static Boolean TestCase08(RemoteWebDriver driver) throws InterruptedException {
        Boolean status;

        logStatus("Start TestCase",
                "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough",
                "DONE");
        takeScreenshot(driver, "StartTestCase", "TestCase08");

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();

        // Step 1: Register a new user
        status = registration.registerUser("testUser", "abc@125", true);
        logStatus("Step Success", "User Registration: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase",
                    "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough",
                    "FAIL");
            takeScreenshot(driver, "Registration_Failure", "TestCase08");
            return false;
        }
        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();

        // Step 2: Login with the newly registered user's credentials
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Step Success", "User Perform Login: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase",
                    "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough",
                    "FAIL");
            takeScreenshot(driver, "Login_Failure", "TestCase08");
            return false;
        }

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        // Step 3: Find required products by searching and add them to the user's cart
        status = homePage.searchForProduct("Stylecon");
        homePage.addProductToCart("Stylecon 9 Seater RHS Sofa Set ");
        logStatus("Step Success", "Added 'Stylecon 9 Seater RHS Sofa Set' to the cart", status ? "PASS" : "FAIL");

        homePage.changeProductQuantityinCart("Stylecon 9 Seater RHS Sofa Set ", 10);
        logStatus("Step Success", "Changed product quantity in the cart to 10", status ? "PASS" : "FAIL");

        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1 addr Line 2 addr line 5");
        checkoutPage.selectAddress("Addr line 1 addr Line 2 addr line 5");

        checkoutPage.placeOrder();

        // Step 4: Verify if the insufficient balance error message is displayed
        status = checkoutPage.verifyInsufficientBalanceMessage();
        logStatus("Step Success", "Verified that the insufficient balance error message is displayed", status ? "PASS" : "FAIL");

        logStatus("End TestCase",
                "Test Case 8: Verify that insufficient balance error is thrown when the wallet balance is not enough",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "Insufficient_Balance_Error", "TestCase08");

        return status;
    }


    public static Boolean TestCase09(RemoteWebDriver driver) throws InterruptedException {
        Boolean status = false;

        logStatus("Start TestCase",
                "Test Case 9: Verify that product added to cart is available when a new tab is opened",
                "DONE");
        takeScreenshot(driver, "StartTestCase", "TestCase09");

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@125", true);
        logStatus("Step Success", "User Registration: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase",
                    "Test Case 9: Verify that product added to cart is available when a new tab is opened",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase09");
            return false;
        }
        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Step Success", "User Perform Login: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase",
                    "Test Case 9: Verify that product added to cart is available when a new tab is opened",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase09");
            return false;
        }

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        status = homePage.searchForProduct("YONEX");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");
        logStatus("Step Success", "Added 'YONEX Smash Badminton Racquet' to the cart", status ? "PASS" : "FAIL");

        String currentURL = driver.getCurrentUrl();

        driver.findElement(By.linkText("Privacy policy")).click();
        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]);
        homePage.navigateToHome();

        List<String> expectedResult = Arrays.asList("YONEX Smash Badminton Racquet");
        status = homePage.verifyCartContents(expectedResult);
        logStatus("Step Success", "Verified that the cart contents are available in the new tab", status ? "PASS" : "FAIL");

        driver.close();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);

        logStatus("End TestCase",
                "Test Case 9: Verify that product added to cart is available when a new tab is opened",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "EndTestCase", "TestCase09");

        return status;
    }

    public static Boolean TestCase10(RemoteWebDriver driver) throws InterruptedException {
        Boolean status = false;

        logStatus("Start TestCase",
                "Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly",
                "DONE");
        takeScreenshot(driver, "StartTestCase", "TestCase10");

        Register registration = new Register(driver);
        registration.navigateToRegisterPage();
        status = registration.registerUser("testUser", "abc@125", true);
        logStatus("Step Success", "User Registration: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase",
                    "Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase10");
            return false;
        }
        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        logStatus("Step Success", "User Perform Login: ", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase",
                    "Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase10");
            return false;
        }

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        String basePageURL = driver.getCurrentUrl();

        driver.findElement(By.linkText("Privacy policy")).click();
        status = driver.getCurrentUrl().equals(basePageURL);
        logStatus("Step Success", "Verifying parent page URL didn't change on Privacy Policy link click", status ? "PASS" : "FAIL");

        if (!status) {
            logStatus("End TestCase",
                    "Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase10");
            return false;
        }

        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]);
        WebElement privacyPolicyHeading = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/h2"));
        status = privacyPolicyHeading.getText().equals("Privacy Policy");
        logStatus("Step Success", "Verifying new tab opened has Privacy Policy page heading", status ? "PASS" : "FAIL");

        if (!status) {
            driver.close();
            driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
            logStatus("End TestCase",
                    "Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase10");
            return false;
        }

        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
        driver.findElement(By.linkText("Terms of Service")).click();

        handles = driver.getWindowHandles();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[2]);
        WebElement tosHeading = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/h2"));
        status = tosHeading.getText().equals("Terms of Service");
        logStatus("Step Success", "Verifying new tab opened has Terms Of Service page heading", status ? "PASS" : "FAIL");

        if (!status) {
            driver.close();
            driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
            logStatus("End TestCase",
                    "Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase10");
            return false;
        }

        driver.close();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[1]).close();
        driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);

        logStatus("End TestCase",
                "Test Case 10: Verify that the Privacy Policy, About Us are displayed correctly",
                "PASS");
        takeScreenshot(driver, "EndTestCase", "TestCase10");

        return true;
    }

    public static Boolean TestCase11(RemoteWebDriver driver) throws InterruptedException {
        logStatus("Start TestCase",
                "Test Case 11: Verify that contact us option is working correctly ",
                "DONE");
        takeScreenshot(driver, "StartTestCase", "TestCase11");

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        driver.findElement(By.xpath("//*[text()='Contact us']")).click();

        WebElement name = driver.findElement(By.xpath("//input[@placeholder='Name']"));
        name.sendKeys("crio user");
        WebElement email = driver.findElement(By.xpath("//input[@placeholder='Email']"));
        email.sendKeys("criouser@gmail.com");
        WebElement message = driver.findElement(By.xpath("//input[@placeholder='Message']"));
        message.sendKeys("Testing the contact us page");

        WebElement contactUs = driver.findElement(By.xpath("*//button[contains(@class,\"btn-block\")]"));

        contactUs.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(contactUs));

        logStatus("End TestCase",
                "Test Case 11: Verify that contact us option is working correctly ",
                "PASS");

        takeScreenshot(driver, "EndTestCase", "TestCase11");

        return true;
    }

    public static Boolean TestCase12(RemoteWebDriver driver) throws InterruptedException {
        logStatus("Start TestCase",
                "Test Case 12: Ensure that the links on the QKART advertisement are clickable",
                "DONE");
        takeScreenshot(driver, "StartTestCase", "TestCase12");

        Register registration = new Register (driver);
        registration.navigateToRegisterPage();
        Boolean status = registration.registerUser("testUser", "abc@125", true);
        if (!status) {
            logStatus("TestCase 12",
                    "Test Case Failure. Ensure that the links on the QKART advertisement are clickable",
                    "FAIL");
            takeScreenshot(driver, "Failure", "TestCase12");
            return false;
        }
        lastGeneratedUserName = registration.lastGeneratedUsername;

        Login login = new Login(driver);
        login.navigateToLoginPage();
        status = login.PerformLogin(lastGeneratedUserName, "abc@125");
        if (!status) {
            logStatus("Step Failure", "User Perform Login Failed", status ? "PASS" : "FAIL");
            takeScreenshot(driver, "Failure", "TestCase12");
            logStatus("End TestCase",
                    "Test Case 12:  Ensure that the links on the QKART advertisement are clickable",
                    status ? "PASS" : "FAIL");
            return false;
        }

        Home homePage = new Home(driver);
        homePage.navigateToHome();

        status = homePage.searchForProduct("YONEX Smash Badminton Racquet");
        homePage.addProductToCart("YONEX Smash Badminton Racquet");
        homePage.changeProductQuantityinCart("YONEX Smash Badminton Racquet", 1);
        homePage.clickCheckout();

        Checkout checkoutPage = new Checkout(driver);
        checkoutPage.addNewAddress("Addr line 1  addr Line 2  addr line 5");
        checkoutPage.selectAddress("Addr line 1  addr Line 2  addr line 5");
        checkoutPage.placeOrder();

        String currentURL = driver.getCurrentUrl();

        List<WebElement> advertisements = driver.findElements(By.xpath("//iframe"));

        status = advertisements.size() == 3;
        logStatus("Step ", "Verify that 3 Advertisements are available", status ? "PASS" : "FAIL");

        WebElement advertisement1 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/iframe[1]"));
        driver.switchTo().frame(advertisement1);
        driver.findElement(By.xpath("//button[text()='Buy Now']")).click();

        status = !driver.getCurrentUrl().equals(currentURL);
        logStatus("Step ", "Verify that Advertisement 1 is clickable ", status ? "PASS" : "FAIL");

        driver.navigate().back();
        driver.switchTo().parentFrame();
        WebElement advertisement2 = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/iframe[2]"));
        driver.switchTo().frame(advertisement2);
        driver.findElement(By.xpath("//button[text()='Buy Now']")).click();

        status = !driver.getCurrentUrl().equals(currentURL);
        driver.switchTo().parentFrame();
        driver.navigate().back();

        logStatus("Step ", "Verify that Advertisement 2 is clickable ", status ? "PASS" : "FAIL");

        logStatus("End TestCase",
                "Test Case 12:  Ensure that the links on the QKART advertisement are clickable",
                status ? "PASS" : "FAIL");
        takeScreenshot(driver, "EndTestCase", "TestCase12");

        return status;
    }

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        int totalTests = 0;
        int passedTests = 0;
        Boolean status;
        RemoteWebDriver driver = createDriver();
        // Maximize and Implicit Wait for things to initailize
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        try {
            // Execute Test Case 1
            totalTests += 1;
            status = TestCase01(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 2
            totalTests += 1;
            status = TestCase02(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 3
            totalTests += 1;
            status = TestCase03(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 4
            totalTests += 1;
            status = TestCase04(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 5
            totalTests += 1;
            status = TestCase05(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 6
            totalTests += 1;
            status = TestCase06(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 7
            totalTests += 1;
            status = TestCase07(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 8
            totalTests += 1;
            status = TestCase08(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 9
            totalTests += 1;
            status = TestCase09(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 10
            totalTests += 1;
            status = TestCase10(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 11
            totalTests += 1;
            status = TestCase11(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");

            // Execute Test Case 12
            totalTests += 1;
            status = TestCase12(driver);
            if (status) {
                passedTests += 1;
            }

            System.out.println("");
        } catch (Exception e) {
            throw e;
        } finally {
            // quit Chrome Driver
            driver.quit();

            System.out.println(String.format("%s out of %s test cases Passed ", passedTests,
                    totalTests));
        }

    }
}

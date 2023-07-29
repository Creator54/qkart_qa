package dev.creator54.QKART_TESTNG;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverSingleton {
    static DriverSingleton driverSingleton=null;
    static RemoteWebDriver driver = null;

    public DriverSingleton () throws MalformedURLException {
        ChromeOptions options = new ChromeOptions ();
        driver = new RemoteWebDriver(new URL ("http://localhost:4444/wd/hub"), options);
    }

    public static DriverSingleton getInstance () throws MalformedURLException {
        if(driverSingleton ==null){
            driverSingleton = new DriverSingleton ();
        }
        return driverSingleton;
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }

    public static void quitDriver () {
        driver.quit();
        driver = null;
    }
}
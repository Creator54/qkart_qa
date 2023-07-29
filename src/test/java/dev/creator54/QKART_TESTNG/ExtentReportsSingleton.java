package dev.creator54.QKART_TESTNG;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

public class ExtentReportsSingleton {
    private static ExtentReportsSingleton instance = null;
    private static ExtentReports extentReports = null;
    private static ExtentTest  extentTest = null;

    ExtentReportsSingleton () {
        extentReports = new ExtentReports(System.getProperty("user.dir") + "/ExtentReport.html", true);
        extentReports.loadConfig(new File (System.getProperty("user.dir") + "/extent-config.xml"));

        extentTest = extentReports.startTest("QKART_TESTNG Test");
    }

    public static ExtentReportsSingleton getInstance() {
        if (instance == null) {
            instance = new ExtentReportsSingleton();
        }
        return instance;
    }


    public ExtentTest getExtentTest() {
        return extentTest;
    }
    public ExtentReports  getExtentReports() {
        return extentReports;
    }

    public void endReport() {
        if(extentReports !=null) {
            extentReports.endTest (extentTest);
            extentReports.flush ();
            extentReports.close ();
            instance = null;
        }
    }
}

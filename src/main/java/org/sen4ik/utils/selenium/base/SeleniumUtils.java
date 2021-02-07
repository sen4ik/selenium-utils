package org.sen4ik.utils.selenium.base;

import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

public class SeleniumUtils {

    private static Supplier supplier;

    public static int defaultTimeoutInSeconds = 60;
    public static int defaultPollingPeriodInMilliseconds = 500;

    public static void setSupplier(Supplier supplier){
        SeleniumUtils.supplier = supplier;
    }

    public static WebDriver getDriver(){
        return (WebDriver) supplier.get();
    }

    public static int getDefaultTimeoutInSeconds() {
        return defaultTimeoutInSeconds;
    }

    public static void setDefaultTimeoutInSeconds(int defaultTimeoutInSeconds) {
        SeleniumUtils.defaultTimeoutInSeconds = defaultTimeoutInSeconds;
    }

    public static int getDefaultPollingPeriodInMilliseconds() {
        return defaultPollingPeriodInMilliseconds;
    }

    public static void setDefaultPollingPeriodInMilliseconds(int defaultPollingPeriodInMilliseconds) {
        SeleniumUtils.defaultPollingPeriodInMilliseconds = defaultPollingPeriodInMilliseconds;
    }

}
